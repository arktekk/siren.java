package no.arktekk.siren;


import no.arktekk.siren.SubEntity.EmbeddedLink;
import no.arktekk.siren.SubEntity.EmbeddedRepresentation;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface JsonParser<T> {

    Entity fromJson(T json);

    final class JavaxJsonParser implements JsonParser<JsonObject> {

        public Entity fromJson(JsonObject json) {
            return parseEntity(json);
        }

        private Entity parseEntity(JsonObject object) {
            Optional<Classes> classes = orEmpty(object, "class").map(cs -> new Classes(arrayToStringList(cs)));
            Optional<JsonObject> properties = Optional.ofNullable(object.getJsonObject("properties"));
            Optional<Entities> entities = orEmpty(object, "entities").map(es -> new Entities(mapObjectList(es, this::parseSubEntity)));
            Optional<Actions> actions = orEmpty(object, "actions").map(as -> new Actions(mapObjectList(as, this::parseAction)));
            Optional<Links> links = orEmpty(object, "links").map(ls -> new Links(mapObjectList(ls, this::parseLink)));
            return new Entity(classes, properties, entities, actions, links, parseTitle(object));
        }

        private Optional<String> parseTitle(JsonObject object) {
            return object.containsKey("title") ? Optional.of(object.getString("title")) : Optional.empty();
        }

        private Optional<String> get(JsonObject object, String key) {
            if (object.containsKey(key))
                return Optional.ofNullable(object.getString(key));
            else
                return Optional.empty();
        }

        private Link parseLink(JsonObject obj) {
            URI href = getHref(obj, "Link");
            Rel rels = new Rel(arrayToStringList(obj.getJsonArray("rel")));
            if (rels.isEmpty()) {
                throw new SirenParseException(String.format("Empty 'rel' in Link '%s'", obj));
            }
            Optional<MIMEType> type = get(obj, "type").flatMap(MIMEType::parse);
            Optional<Classes> classes = orEmpty(obj, "class").map(cs -> new Classes(arrayToStringList(cs)));
            return new Link(rels, href, classes, type, parseTitle(obj));
        }

        private Action parseAction(JsonObject action) {
            String name = action.getString("name");
            if (name == null) {
                throw new SirenParseException(String.format("Missing required 'name' field in Action '%s", action));
            }
            URI href = getHref(action, "Action");
            Optional<Method> method = get(action, "method").map(Method::valueOf);
            Optional<MIMEType> type = get(action, "type").flatMap(MIMEType::parse);
            Optional<Fields> fields = orEmpty(action, "fields").map(fs -> new Fields(mapObjectList(fs, this::parseField)));
            Optional<Classes> classes = orEmpty(action, "class").map(cs -> new Classes(arrayToStringList(cs)));
            return new Action(name, href, classes, get(action, "title"), method, type, fields);
        }

        private SubEntity parseSubEntity(JsonObject entity) {
            if (entity.containsKey("href"))
                return parseEmbeddedLink(entity);
            else
                return parseEmbeddedRepresentation(entity);
        }

        private EmbeddedLink parseEmbeddedLink(JsonObject embeddedLink) {
            Link link = parseLink(embeddedLink);
            return new EmbeddedLink(link.rel, link.href, link.classes, link.type, link.title);
        }

        private EmbeddedRepresentation parseEmbeddedRepresentation(JsonObject embeddedRepresentation) {
            Entity entity = parseEntity(embeddedRepresentation);
            Rel rel = new Rel(arrayToStringList(embeddedRepresentation.getJsonArray("rel")));
            return new EmbeddedRepresentation(rel, entity);
        }

        private Field parseField(JsonObject field) {
            String name = field.getString("name");
            if (name == null) {
                throw new SirenParseException(String.format("Missing required 'name' field in Field '%s", field));
            }
            Optional<Classes> classes = orEmpty(field, "class").map(cs -> new Classes(arrayToStringList(cs)));
            Field.Type type = Field.Type.fromString(field.getString("type", "text"));

            return new Field(name, type, classes, Optional.ofNullable(field.get("value")), get(field, "title"));
        }

        private URI getHref(JsonObject obj, String name) {
            String hrefString = obj.getString("href");
            if (hrefString == null) {
                throw new SirenParseException(String.format("Missing required 'href' field in %s '%s'", name, obj));
            }
            return URI.create(hrefString);
        }

        private <A> List<A> mapObjectList(JsonArray list, Function<JsonObject, A> f) {
            return arrayToObjectStream(list).map(f).collect(Collectors.toList());
        }

        private Optional<JsonArray> orEmpty(JsonObject object, String name) {
            return Optional.ofNullable(object.getJsonArray(name));
        }

        private List<String> arrayToStringList(JsonArray classes) {
            return classes.stream().
                    filter(j -> j.getValueType() == JsonValue.ValueType.STRING).
                    map(j -> ((JsonString) j).getString()).
                    collect(Collectors.toList());
        }

        private Stream<JsonObject> arrayToObjectStream(JsonArray classes) {
            return classes.stream().
                    filter(j -> j.getValueType() == JsonValue.ValueType.OBJECT).
                    map(j -> ((JsonObject) j));
        }
    }
}
