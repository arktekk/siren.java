package no.arktekk.siren;

import net.hamnaberg.json.Json;
import net.hamnaberg.json.io.JacksonStreamingSerializer;
import no.arktekk.siren.SubEntity.EmbeddedLink;
import no.arktekk.siren.SubEntity.EmbeddedRepresentation;
import org.junit.Test;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static no.arktekk.siren.Field.Type.NUMBER;
import static no.arktekk.siren.MIMEType.URLEncoded;
import static no.arktekk.siren.Method.POST;
import static org.junit.Assert.assertEquals;

public class ImmutableJsonSerializerParserTest {
    @Test
    public void serializeAndParse() {
        Entity entity = new Entity(
                Optional.of(Classes.of("foo-class", "bar-class")),
                Optional.of(Json.jObject(entry("name", Json.jString("Foo")), entry("dog", Json.jBoolean(false)), entry("cat", Json.jBoolean(true)))),
                Optional.of(Entities.of(EmbeddedLink.of(new Rel(singletonList("rel1")), URI.create("http://www.vg.no"))
                        .with(Classes.of("foo-class", "bar-class"))
                        .with(MIMEType.JSON)
                        .with("Supertittel"))),
                empty(),
                empty(),
                Optional.of("Megatittel"));

        String s = Siren.toString(entity);
        Entity parsed = Siren.parse(s);
        assertEquals(entity, parsed);
    }

    @Test
    public void recreateNullPointerExceptionInParser() {
        Entity entity = Entity.of().with(Classes.of("test-classes")).with(
                Entities.of(new EmbeddedRepresentation(
                        Rel.of("related"),
                        new Entity(
                                Optional.of(Classes.of("test-class")),
                                Optional.of(Json.jObject(entry("test1-key", "test1-value"), entry("test2-key", "test2-value"))),
                                empty(),
                                empty(),
                                Optional.of(Links.of(Link.of(Rel.of("test-link"), URI.create("http://www.github.com")))),
                                empty()
                        )
                ))
        );
        String json = Siren.toString(entity);
        Entity data = Siren.parse(json);
        assertEquals(entity, data);
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
        String json = Siren.toString(entity);
        Entity data = Siren.parse(json);
        assertEquals(entity, data);
    }


    private Map.Entry<String, Json.JValue> entry(String name, Json.JValue value) {
        return new AbstractMap.SimpleImmutableEntry<>(name, value);
    }

    private Map.Entry<String, Json.JValue> entry(String name, String value) {
        return entry(name, Json.jString(value));
    }
}
