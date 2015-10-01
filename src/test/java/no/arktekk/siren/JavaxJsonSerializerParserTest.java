package no.arktekk.siren;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonValue;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static no.arktekk.siren.Field.Type.NUMBER;
import static no.arktekk.siren.MIMEType.URLEncoded;
import static no.arktekk.siren.Method.POST;
import static org.junit.Assert.assertEquals;

public class JavaxJsonSerializerParserTest {

    @Test
    public void serializeAndParse() {
        Entity entity = new Entity(
                Optional.of(Classes.of("foo-class", "bar-class")),
                Optional.of(Json.createObjectBuilder().add("name", "Foo").add("dog", false).add("cat", true).build()),
                singletonList(EmbeddedLink.of(new Relations(singletonList("rel1")), URI.create("http://www.vg.no"))
                        .classes(Classes.of("foo-class", "bar-class"))
                        .type(MIMEType.JSON)
                        .title("Supertittel")),
                Collections.emptyList(),
                Collections.emptyList(),
                Optional.of("Megatittel"));

        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }

    @Test
    public void recreateNullPointerExceptionInParser() {
        Entity entity = Entity.of().classes(Classes.of("test-classes")).entities(
                singletonList(new EmbeddedRepresentation(
                        Relations.of("related"),
                        new Entity(
                                Optional.of(Classes.of("test-class")),
                                Optional.of(Json.createObjectBuilder().add("test1-key", "test1-value").add("test2-key", "test2-value").build()),
                                emptyList(),
                                emptyList(),
                                singletonList(Link.of(Relations.of("test-link"), URI.create("http://www.github.com"))),
                                empty()
                        )
                ))
        );
        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }

    @Test
    public void withAction() {
        Entity entity = Entity.of().classes(Classes.of("test-classes")).actions(
                singletonList(new Action(
                        "do-it",
                        Optional.of(Classes.of("do-it-class")),
                        URI.create("http://www.github.com"),
                        Optional.of("Do It"),
                        Optional.of(POST),
                        Optional.of(URLEncoded),
                        Optional.of(Fields.of(Field.of("a"), Field.of("b", NUMBER))))
        ));
        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }
}
