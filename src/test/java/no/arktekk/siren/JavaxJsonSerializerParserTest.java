package no.arktekk.siren;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonValue;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static no.arktekk.siren.Field.Type.NUMBER;
import static no.arktekk.siren.Field.Type.TEXT;
import static no.arktekk.siren.MIMEType.URLEncoded;
import static no.arktekk.siren.Method.POST;
import static org.junit.Assert.assertEquals;

public class JavaxJsonSerializerParserTest {

    @Test
    public void serializeAndParse() {
        Entity entity = new Entity(
                new Classes(asList("foo-class", "bar-class")),
                Optional.of(Json.createObjectBuilder().add("name", "Foo").add("dog", false).add("cat", true).build()),
                singletonList(new EmbeddedLink(
                        new Classes(asList("foo-class", "bar-class")),
                        new Relations(singletonList("rel1")),
                        URI.create("http://www.vg.no"),
                        Optional.of(MIMEType.JSON),
                        Optional.of("Supertittel"))),
                Collections.emptyList(),
                Collections.emptyList(),
                Optional.of("Megatittel"));

        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }

    @Test
    public void recreateNullPointerExceptionInParser() {
        Entity entity = new Entity(
                new Classes(singletonList("test-classes")),
                empty(),
                singletonList(new EmbeddedRepresentation(
                        Relations.of("related"),
                        new Entity(
                                new Classes(singletonList("test-class")),
                                Optional.of(Json.createObjectBuilder().add("test1-key", "test1-value").add("test2-key", "test2-value").build()),
                                emptyList(),
                                emptyList(),
                                singletonList(new Link(Classes.empty(), Relations.of("test-link"), URI.create("http://www.github.com"), empty(), empty())),
                                empty()
                        )
                )),
                emptyList(),
                emptyList(),
                empty()
        );
        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }

    @Test
    public void withAction() {
        Entity entity = new Entity(
                new Classes(singletonList("test-classes")),
                empty(),
                emptyList(),
                singletonList(new Action(
                        "do-it",
                        new Classes(singletonList("do-it-class")),
                        URI.create("http://www.github.com"),
                        Optional.of("Do It"),
                        Optional.of(POST),
                        Optional.of(URLEncoded),
                        asList(
                                new Field("a", Classes.empty(), TEXT, empty(), Optional.of("A")),
                                new Field("b", Classes.empty(), NUMBER, empty(), Optional.of("B"))
                        ))),
                emptyList(),
                empty()
        );
        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }
}
