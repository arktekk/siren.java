package no.arktekk.siren;

import no.arktekk.siren.SubEntity.EmbeddedLink;
import no.arktekk.siren.SubEntity.EmbeddedRepresentation;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonValue;
import java.net.URI;
import java.util.Optional;

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
                Optional.of(Entities.of(EmbeddedLink.of(new Rel(singletonList("rel1")), URI.create("http://www.vg.no"))
                        .with(Classes.of("foo-class", "bar-class"))
                        .with(MIMEType.JSON)
                        .with("Supertittel"))),
                empty(),
                empty(),
                Optional.of("Megatittel"));

        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }

    @Test
    public void recreateNullPointerExceptionInParser() {
        Entity entity = Entity.of().with(Classes.of("test-classes")).with(
                Entities.of(new EmbeddedRepresentation(
                        Rel.of("related"),
                        new Entity(
                                Optional.of(Classes.of("test-class")),
                                Optional.of(Json.createObjectBuilder().add("test1-key", "test1-value").add("test2-key", "test2-value").build()),
                                empty(),
                                empty(),
                                Optional.of(Links.of(Link.of(Rel.of("test-link"), URI.create("http://www.github.com")))),
                                empty()
                        )
                ))
        );
        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }

    @Test
    public void withAction() {
        Entity entity = Entity.of().with(Classes.of("test-classes")).with(
                Actions.of(new Action(
                                "do-it",
                                URI.create("http://www.github.com"),
                                Optional.of(Classes.of("do-it-class")),
                                Optional.of("Do It"),
                                Optional.of(POST),
                                Optional.of(URLEncoded),
                                Optional.of(Fields.of(Field.of("a"), Field.of("b", NUMBER))))
                ));
        JsonValue json = Siren.toJson(entity, new JsonSerializer.JavaxJsonSerializer());
        assertEquals(entity, Siren.fromJson(json.toString(), new JsonParser.JavaxJsonParser()));
    }
}
