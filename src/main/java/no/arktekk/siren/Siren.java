package no.arktekk.siren;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import net.hamnaberg.json.Json;
import net.hamnaberg.json.jackson.JacksonStreamingParser;

public final class Siren {
    private static final JacksonStreamingParser streamParser = new JacksonStreamingParser();

    public static Entity parse(InputStream is) {
        Json.JValue parse = streamParser.parseUnsafe(is);
        return JsonParser.ImmutableJsonParser.INSTANCE.fromJson(parse.asJsonObjectOrEmpty());
    }

    public static Entity parse(String json) {
        Json.JValue parse = streamParser.parseUnsafe(json);
        return JsonParser.ImmutableJsonParser.INSTANCE.fromJson(parse.asJsonObjectOrEmpty());
    }

    public static Entity parse(Json.JObject json) {
        return JsonParser.ImmutableJsonParser.INSTANCE.fromJson(json);
    }

    public static Json.JValue toJson(Entity entity) {
        return JsonSerializer.ImmutableJsonSerializer.INSTANCE.serialize(entity);
    }

    public static void write(Entity entity, OutputStream stream) throws IOException {
        Json.JValue json = toJson(entity);
        stream.write(json.nospaces().getBytes(StandardCharsets.UTF_8));
    }

    public static void write(Entity entity, Writer stream) throws IOException {
        stream.write(toJson(entity).nospaces());
    }

    public static String toString(Entity entity) {
        return toJson(entity).nospaces();
    }

}
