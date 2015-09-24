package net.hamnaberg.siren;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonValue;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class JavaxJsonSerializerTest {

    @Test
    public void serializeAndParse() {
        Entity entity = new Entity(
                new Classes(asList("foo-class", "bar-class")),
                Optional.of(Json.createObjectBuilder().add("name", "Foo").add("dog", false).add("cat", true).build()),
                asList(new EmbeddedLink(
                                new Classes(asList("foo-class", "bar-class")),
                                new Relations(asList("rel1")),
                                URI.create("http://www.vg.no"),
                                Optional.of(MIMEType.JSON),
                                Optional.of("Supertittel"))),
                Collections.emptyList(),
                Collections.emptyList(),
                Optional.of("Megatittel"));

        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }
}
