package net.hamnaberg.siren;

import org.junit.Test;

import javax.json.Json;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import static java.util.Arrays.asList;

public class JavaxJsonSerializerTest {

    @Test
    public void serialize() {
        Siren s = new Siren(
                new Classes(asList("foo-class", "bar-class")),
                Optional.of(Json.createObjectBuilder().add("name", "Foo").add("dog", false).add("cat", true).build()),
                asList(new EmbeddedLink(
                                new Classes(asList("foo-class", "bar-class")),
                                new Relations(asList("rel1")),
                                Optional.of(URI.create("http://www.vg.no")),
                                Optional.of(MIMEType.JSON),
                                Optional.of("Supertittel"))),
                Collections.emptyList(),
                Collections.emptyList(),
                Optional.of("Megatittel"));
        System.out.println(new JsonSerializer.JavaxJsonSerializer().serialize(s).toString());
    }
}
