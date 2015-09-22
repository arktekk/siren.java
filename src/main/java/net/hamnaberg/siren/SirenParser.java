package net.hamnaberg.siren;

import javax.json.*;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class SirenParser {
    public Optional<Siren> parse(InputStream is) {
        JsonReader parser = Json.createReader(is);
        return parseSiren(parser);
    }

    public Optional<Siren> parse(Reader reader) {
        JsonReader parser = Json.createReader(reader);
        return parseSiren(parser);
    }

    public Optional<Siren> parse(String input) {
        return parse(new StringReader(input));
    }

    private Optional<Siren> parseSiren(JsonReader parser) {
        JsonStructure struct = parser.read();
        if (struct instanceof JsonObject) {
            return Optional.of(parseSiren((JsonObject) struct));
        }
        return Optional.empty();
    }

    private Siren parseSiren(JsonObject object) {
        Classes clazz = new Classes(arrayToStringList(arrayOrEmpty(object, "class")));
        Optional<JsonObject> properties = Optional.ofNullable(object.getJsonObject("properties"));
        List<Entity> entities = parseList(arrayOrEmpty(object, "entities"), this::parseEntity);
        List<Action> actions = parseList(arrayOrEmpty(object, "actions"), this::parseAction);
        List<Link> links = parseList(arrayOrEmpty(object, "links"), this::parseLink);
        return new Siren(clazz, properties, entities, actions, links);
    }

    private Link parseLink(JsonObject obj) {
        URI href = getHref(obj, "Link");
        Relations rels = new Relations(arrayToStringList(obj.getJsonArray("rel")));
        if (rels.isEmpty()) {
            throw new SirenParseException(String.format("Empty 'rel' in Link '%s'", obj));
        }
        Optional<MIMEType> type = Optional.ofNullable(obj.getString("type")).flatMap(MIMEType::parse);
        return new Link(href, rels, Optional.ofNullable(obj.getString("title")), type);
    }

    private Action parseAction(JsonObject action) {
        String name = action.getString("name");
        if (name == null) {
            throw new SirenParseException(String.format("Missing required 'name' field in Action '%s", action));
        }
        URI href = getHref(action, "Action");
        Optional<Method> method = Optional.ofNullable(action.getString("method")).map(Method::valueOf);
        Optional<MIMEType> type = Optional.ofNullable(action.getString("type")).flatMap(MIMEType::parse);

        List<Field> fields = parseList(arrayOrEmpty(action, "fields"), this::parseField);

        return new Action(name, href, Optional.ofNullable(action.getString("title")), method, type, fields);
    }

    private Entity parseEntity(JsonObject entity) {
        Siren siren = parseSiren(entity);
        Relations rels = new Relations(arrayToStringList(entity.getJsonArray("rel")));
        Optional<URI> href = Optional.ofNullable(entity.getString("href")).map(URI::create);
        return siren.toEntity(href, rels);
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


    private <A> List<A> parseList(JsonArray list, Function<JsonObject, A> f) {
        List<JsonObject> linkObjects = arrayToObjectList(list);
        return linkObjects.stream().map(f).collect(Collectors.toList());
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

    private List<JsonObject> arrayToObjectList(JsonArray classes) {
        return classes.stream().
                filter(j -> j.getValueType() == JsonValue.ValueType.OBJECT).
                map(j -> ((JsonObject) j)).
                collect(Collectors.toList());
    }
}
