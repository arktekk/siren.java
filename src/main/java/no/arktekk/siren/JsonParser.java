package no.arktekk.siren;


import net.hamnaberg.json.Json;
import no.arktekk.siren.SubEntity.EmbeddedLink;
import no.arktekk.siren.SubEntity.EmbeddedRepresentation;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface JsonParser<T> {

    Entity fromJson(T json);

    final class ImmutableJsonParser implements JsonParser<Json.JObject> {

        public Entity fromJson(Json.JObject json) {
            return parseEntity(json);
        }

        private Entity parseEntity(Json.JObject object) {
            Optional<Classes> classes = getAsJsonArray(object, "class").map(l -> new Classes(l.getListAsStrings()));
            Optional<Json.JObject> properties = object.getAs("properties", Json.JValue::asJsonObject);
            Optional<Entities> entities = getAsJsonArray(object, "entities").map(es -> new Entities(mapObjectList(es, this::parseSubEntity)));
            Optional<Actions> actions = getAsJsonArray(object, "actions").map(as -> new Actions(mapObjectList(as, this::parseAction)));
            Optional<Links> links = getAsJsonArray(object, "links").map(ls -> new Links(mapObjectList(ls, this::parseLink)));
            return new Entity(classes, properties, entities, actions, links, parseTitle(object));
        }

        private Optional<Json.JArray> getAsJsonArray(Json.JObject object, String actions) {
            return object.getAs(actions, Json.JValue::asJsonArray);
        }

        private Optional<String> parseTitle(Json.JObject object) {
            return object.getAsString("title");
        }

        private Link parseLink(Json.JObject obj) {
            URI href = getHref(obj, "Link");
            Rel rels = new Rel(getAsJsonArray(obj, "rel").orElse(Json.jEmptyArray()).getListAsStrings());
            if (rels.isEmpty()) {
                throw new SirenParseException(String.format("Empty 'rel' in Link '%s'", obj));
            }
            Optional<MIMEType> type = obj.getAsString("type").flatMap(MIMEType::parse);
            Optional<Classes> classes = getAsJsonArray(obj, "class").map(cs -> new Classes(cs.getListAsStrings()));
            return new Link(rels, href, classes, type, parseTitle(obj));
        }

        private Action parseAction(Json.JObject action) {
            Optional<String> name = action.getAsString("name");
            if (!name.isPresent()) {
                throw new SirenParseException(String.format("Missing required 'name' field in Action '%s", action));
            }
            URI href = getHref(action, "Action");
            Optional<Method> method = action.getAsString("method").map(Method::valueOf);
            Optional<MIMEType> type = action.getAsString("type").flatMap(MIMEType::parse);
            Optional<Fields> fields = getAsJsonArray(action, "fields").map(fs -> new Fields(mapObjectList(fs, this::parseField)));
            Optional<Classes> classes = getAsJsonArray(action, "class").map(cs -> new Classes(cs.getListAsStrings()));
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
            Rel rel = new Rel(getAsJsonArray(embeddedRepresentation, "rel").orElse(Json.jEmptyArray()).getListAsStrings());
            return new EmbeddedRepresentation(rel, entity);
        }

        private Field parseField(Json.JObject field) {
            Optional<String> name = field.getAsString("name");
            if (!name.isPresent()) {
                throw new SirenParseException(String.format("Missing required 'name' field in Field '%s", field));
            }
            Optional<Classes> classes = getAsJsonArray(field, "class").map(cs -> new Classes(cs.getListAsStrings()));
            Field.Type type = Field.Type.fromString(field.getAsString("type").orElse("text"));

            return new Field(name.get(), type, classes, field.get("value"), parseTitle(field));
        }

        private URI getHref(Json.JObject obj, String name) {
            Optional<String> hrefString = obj.getAsString("href");
            if (!hrefString.isPresent()) {
                throw new SirenParseException(String.format("Missing required 'href' field in %s '%s'", name, obj));
            }
            return URI.create(hrefString.get());
        }

        private <A> List<A> mapObjectList(Json.JArray list, Function<Json.JObject, A> f) {
            return list.getListAsObjects().stream().map(f).collect(Collectors.toList());
        }
    }
}
