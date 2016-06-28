package no.arktekk.siren;


import javaslang.collection.List;
import javaslang.control.Option;
import net.hamnaberg.json.Json;
import no.arktekk.siren.SubEntity.EmbeddedLink;
import no.arktekk.siren.SubEntity.EmbeddedRepresentation;

import java.net.URI;
import java.util.function.Function;

public interface JsonParser<T> {

    Entity fromJson(T json);

    enum ImmutableJsonParser implements JsonParser<Json.JObject> {
        INSTANCE;

        public Entity fromJson(Json.JObject json) {
            return parseEntity(json);
        }

        private Entity parseEntity(Json.JObject object) {
            Option<Classes> classes = object.getAsArray("class").map(l -> new Classes(l.getListAsStrings()));
            Option<Json.JObject> properties = object.getAsObject("properties");
            Option<Entities> entities = object.getAsArray("entities").map(es -> new Entities(mapObjectList(es, this::parseSubEntity)));
            Option<Actions> actions = object.getAsArray("actions").map(as -> new Actions(mapObjectList(as, this::parseAction)));
            Option<Links> links = object.getAsArray("links").map(ls -> new Links(mapObjectList(ls, this::parseLink)));
            return new Entity(classes, properties, entities, actions, links, parseTitle(object));
        }


        private Option<String> parseTitle(Json.JObject object) {
            return object.getAsString("title");
        }

        private Link parseLink(Json.JObject obj) {
            URI href = getHref(obj, "Link");
            Rel rels = new Rel(obj.getAsArrayOrEmpty("rel").getListAsStrings());
            if (rels.isEmpty()) {
                throw new SirenParseException(String.format("Empty 'rel' in Link '%s'", obj));
            }
            Option<MIMEType> type = obj.getAsString("type").flatMap(MIMEType::parse);
            Option<Classes> classes = obj.getAsArray("class").map(cs -> new Classes(cs.getListAsStrings()));
            return new Link(rels, href, classes, type, parseTitle(obj));
        }

        private Action parseAction(Json.JObject action) {
            Option<String> name = action.getAsString("name");
            if (!name.isDefined()) {
                throw new SirenParseException(String.format("Missing required 'name' field in Action '%s", action));
            }
            URI href = getHref(action, "Action");
            Option<Method> method = action.getAsString("method").map(Method::valueOf);
            Option<MIMEType> type = action.getAsString("type").flatMap(MIMEType::parse);
            Option<Fields> fields = action.getAsArray("fields").map(fs -> new Fields(mapObjectList(fs, this::parseField)));
            Option<Classes> classes = action.getAsArray("class").map(cs -> new Classes(cs.getListAsStrings()));
            return new Action(name.get(), href, classes, parseTitle(action), method, type, fields);
        }

        private SubEntity parseSubEntity(Json.JObject entity) {
            if (entity.containsKey("href"))
                return parseEmbeddedLink(entity);
            else
                return parseEmbeddedRepresentation(entity);
        }

        private EmbeddedLink parseEmbeddedLink(Json.JObject embeddedLink) {
            Link link = parseLink(embeddedLink);
            return new EmbeddedLink(link.rel, link.href, link.classes, link.type, link.title);
        }

        private EmbeddedRepresentation parseEmbeddedRepresentation(Json.JObject embeddedRepresentation) {
            Entity entity = parseEntity(embeddedRepresentation);
            Rel rel = new Rel(embeddedRepresentation.getAsArrayOrEmpty("rel").getListAsStrings());
            return new EmbeddedRepresentation(rel, entity);
        }

        private Field parseField(Json.JObject field) {
            Option<String> name = field.getAsString("name");
            if (!name.isDefined()) {
                throw new SirenParseException(String.format("Missing required 'name' field in Field '%s", field));
            }
            Option<Classes> classes = field.getAsArray("class").map(cs -> new Classes(cs.getListAsStrings()));
            Field.Type type = Field.Type.fromString(field.getAsString("type").getOrElse("text"));

            return new Field(name.get(), type, classes, field.get("value"), parseTitle(field));
        }

        private URI getHref(Json.JObject obj, String name) {
            Option<String> hrefString = obj.getAsString("href");
            if (!hrefString.isDefined()) {
                throw new SirenParseException(String.format("Missing required 'href' field in %s '%s'", name, obj));
            }
            return URI.create(hrefString.get());
        }

        private <A> List<A> mapObjectList(Json.JArray list, Function<Json.JObject, A> f) {
            return list.getListAsObjects().map(f);
        }
    }
}
