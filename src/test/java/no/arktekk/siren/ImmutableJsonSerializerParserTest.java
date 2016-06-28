package no.arktekk.siren;

import javaslang.control.Option;
import net.hamnaberg.json.Json;
import no.arktekk.siren.SubEntity.EmbeddedLink;
import no.arktekk.siren.SubEntity.EmbeddedRepresentation;
import org.junit.Test;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static javaslang.control.Option.none;
import static no.arktekk.siren.Field.Type.NUMBER;
import static no.arktekk.siren.MIMEType.URLEncoded;
import static no.arktekk.siren.Method.POST;
import static org.junit.Assert.assertEquals;

public class ImmutableJsonSerializerParserTest {
    @Test
    public void serializeAndParse() {
        Entity entity = new Entity(
                Option.of(Classes.of("foo-class", "bar-class")),
                Option.of(Json.jObject(entry("name", Json.jString("Foo")), entry("dog", Json.jBoolean(false)), entry("cat", Json.jBoolean(true)))),
                Option.of(Entities.of(EmbeddedLink.of(new Rel(singletonList("rel1")), URI.create("http://www.vg.no"))
                        .with(Classes.of("foo-class", "bar-class"))
                        .with(MIMEType.JSON)
                        .with("Supertittel"))),
                none(),
                none(),
                Option.of("Megatittel"));

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
                                Option.of(Classes.of("test-class")),
                                Option.of(Json.jObject(entry("test1-key", "test1-value"), entry("test2-key", "test2-value"))),
                                none(),
                                none(),
                                Option.of(Links.of(Link.of(Rel.of("test-link"), URI.create("http://www.github.com")))),
                                none()
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
                                Option.of(Classes.of("do-it-class")),
                                Option.of("Do It"),
                                Option.of(POST),
                                Option.of(URLEncoded),
                                Option.of(Fields.of(Field.of("a"), Field.of("b", NUMBER))))
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
