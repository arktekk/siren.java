package no.arktekk.siren;

import no.arktekk.siren.Field.Type;
import org.glassfish.json.JsonFactory;
import org.junit.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;

public class FieldTest {

    @Test
    public void construct() {
        Field field = Field.of("foo").value(JsonFactory.jsonString("bar"));
        assertEquals(field.name, "foo");
        assertEquals(field.value, Optional.of(JsonFactory.jsonString("bar")));
        assertEquals(field.classes, empty());
        assertEquals(field.title, empty());
        assertEquals(field.type, Type.TEXT);
    }
}
