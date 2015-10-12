package no.arktekk.siren;

import net.hamnaberg.json.Json;
import net.hamnaberg.json.io.JacksonStreamingParser;

import java.io.InputStream;

public final class Siren {
    public static Entity fromJson(InputStream is, JsonParser<Json.JObject> converter) {
        JacksonStreamingParser streamParser = new JacksonStreamingParser();
        Json.JValue parse = streamParser.parse(is);
        return converter.fromJson(parse.asJsonObjectOrEmpty());
    }

    public static Entity fromJson(String json, JsonParser<Json.JObject> converter) {
        JacksonStreamingParser streamParser = new JacksonStreamingParser();
        Json.JValue parse = streamParser.parse(json);
        return converter.fromJson(parse.asJsonObjectOrEmpty());
    }

    public static Json.JValue toJson(Entity entity, JsonSerializer<Json.JValue> serializer) {
        return serializer.serialize(entity);
    }

}
