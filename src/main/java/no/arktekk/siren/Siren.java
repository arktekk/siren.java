package no.arktekk.siren;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.InputStream;
import java.io.StringReader;

public final class Siren {

    public static Entity fromJson(InputStream is, JsonParser<JsonObject> parser) {
        try (JsonReader reader = Json.createReader(is)) {
            return parser.fromJson(reader.readObject());
        }
    }

    public static Entity fromJson(String json, JsonParser<JsonObject> parser) {
        try (StringReader sReader = new StringReader(json); JsonReader reader = Json.createReader(sReader)) {
            return parser.fromJson(reader.readObject());
        }
    }

    public static JsonValue toJson(Entity entity, JsonSerializer<JsonValue> serializer) {
        return serializer.serialize(entity);
    }

}
