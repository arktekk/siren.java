package net.hamnaberg.siren;


import javax.json.*;
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
            Classes clazz = new Classes(arrayToStringList(arrayOrEmpty(object, "class")));
            Optional<JsonObject> properties = Optional.ofNullable(object.getJsonObject("properties"));
            List<SubEntity> entities = mapObjectList(arrayOrEmpty(object, "entities"), this::parseSubEntity);
            List<Action> actions = mapObjectList(arrayOrEmpty(object, "actions"), this::parseAction);
            List<Link> links = mapObjectList(arrayOrEmpty(object, "links"), this::parseLink);
            return new Entity(clazz, properties, entities, actions, links, parseTitle(object));
        }

        private Optional<String> parseTitle(JsonObject object) {
            return object.containsKey("title") ? Optional.of(object.getString("title")) : Optional.empty();
        }

        private Link parseLink(JsonObject obj) {
            URI href = getHref(obj, "Link");
            Relations rels = new Relations(arrayToStringList(obj.getJsonArray("rel")));
            if (rels.isEmpty()) {
                throw new SirenParseException(String.format("Empty 'rel' in Link '%s'", obj));
            }
            Optional<MIMEType> type = Optional.ofNullable(obj.getString("type")).flatMap(MIMEType::parse);
            Classes clazz = new Classes(arrayToStringList(arrayOrEmpty(obj, "class")));
            return new Link(clazz, rels, href, type, parseTitle(obj));
        }

        private Action parseAction(JsonObject action) {
            String name = action.getString("name");
            if (name == null) {
                throw new SirenParseException(String.format("Missing required 'name' field in Action '%s", action));
            }
            URI href = getHref(action, "Action");
            Optional<Method> method = Optional.ofNullable(action.getString("method")).map(Method::valueOf);
            Optional<MIMEType> type = Optional.ofNullable(action.getString("type")).flatMap(MIMEType::parse);

            List<Field> fields = mapObjectList(arrayOrEmpty(action, "fields"), this::parseField);

            Classes clazz = new Classes(arrayToStringList(arrayOrEmpty(action, "class")));
            return new Action(name, clazz, href, Optional.ofNullable(action.getString("title")), method, type, fields);
        }

        private SubEntity parseSubEntity(JsonObject entity) {
            if (entity.containsKey("href"))
                return parseEmbeddedLink(entity);
            else
                return parseEmbeddedRepresentation(entity);
        }

        private EmbeddedLink parseEmbeddedLink(JsonObject embeddedLink) {
            return new EmbeddedLink(parseLink(embeddedLink));
        }

        private EmbeddedRepresentation parseEmbeddedRepresentation(JsonObject embeddedRepresentation) {
            Entity entity = parseEntity(embeddedRepresentation);
            Relations rel = new Relations(arrayToStringList(embeddedRepresentation.getJsonArray("rel")));
            return new EmbeddedRepresentation(rel, entity);
        }

        private Field parseField(JsonObject field) {
            String name = field.getString("name");
            if (name == null) {
                throw new SirenParseException(String.format("Missing required 'name' field in Field '%s", field));
            }
            Classes clazz = new Classes(arrayToStringList(arrayOrEmpty(field, "class")));
            Field.Type type = Field.Type.fromString(field.getString("type", "text"));

            return new Field(name, clazz, type, Optional.ofNullable(field.get("value")), Optional.ofNullable(field.getString("title")));
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

        private JsonArray arrayOrEmpty(JsonObject object, String name) {
            return Optional.ofNullable(object.getJsonArray(name)).orElse(Json.createArrayBuilder().build());
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
