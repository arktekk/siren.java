package no.arktekk.siren;

import net.hamnaberg.json.Json;
import net.hamnaberg.json.io.JacksonStreamingParser;
import net.hamnaberg.json.io.JacksonStreamingSerializer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

public final class Siren {
    private static final JacksonStreamingParser streamParser = new JacksonStreamingParser();
    private static final JacksonStreamingSerializer serializer = new JacksonStreamingSerializer();

    public static Entity parse(InputStream is) {
        Json.JValue parse = streamParser.parse(is);
        return JsonParser.ImmutableJsonParser.INSTANCE.fromJson(parse.asJsonObjectOrEmpty());
    }

    public static Entity parse(String json) {
        Json.JValue parse = streamParser.parse(json);
        return JsonParser.ImmutableJsonParser.INSTANCE.fromJson(parse.asJsonObjectOrEmpty());
    }

    public static Entity parse(Json.JObject json) {
        return JsonParser.ImmutableJsonParser.INSTANCE.fromJson(json);
    }

    public static Json.JValue toJson(Entity entity) {
        return JsonSerializer.ImmutableJsonSerializer.INSTANCE.serialize(entity);
    }

    public static void write(Entity entity, OutputStream stream) {
        Json.JValue json = toJson(entity);
        serializer.write(json, stream);
    }
    public static void write(Entity entity, Writer stream) {
        Json.JValue json = toJson(entity);
        serializer.write(json, stream);
    }

    public static String toString(Entity entity) {
        Json.JValue json = toJson(entity);
        return serializer.writeToString(json);
    }

}
