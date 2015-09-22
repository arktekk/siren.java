package org.glassfish.json;

import javax.json.*;
import java.math.BigDecimal;
import java.util.Map;

//Factory class to avoid suckiness in underlying library.
public abstract class JsonFactory {
    private JsonFactory(){}

    public static JsonString jsonString(String s) {
        return new JsonStringImpl(s);
    }

    public static JsonValue jsonNull() {
        return JsonValue.NULL;
    }

    public static JsonValue jsonBoolean(boolean b) {
        return b ? JsonValue.TRUE : JsonValue.FALSE;
    }

    public static JsonNumber jsonNumber(double s) {
        return JsonNumberImpl.getJsonNumber(s);
    }

    public static JsonNumber jsonNumber(long s) {
        return JsonNumberImpl.getJsonNumber(s);
    }

    public static JsonNumber jsonNumber(BigDecimal s) {
        return JsonNumberImpl.getJsonNumber(s);
    }

    public static JsonNumber jsonNumber(Number s) {
        return JsonNumberImpl.getJsonNumber(new BigDecimal(s.toString()));
    }

    public static JsonArray arrayOf(Iterable<JsonValue> values) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        values.forEach(builder::add);
        return builder.build();
    }

    public static JsonObject objectOf(Map<String, JsonValue> values) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        values.forEach(builder::add);
        return builder.build();
    }
}
