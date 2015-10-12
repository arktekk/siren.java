package no.arktekk.siren;

import net.hamnaberg.json.Json;
import no.arktekk.siren.Field.Type;
import org.junit.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;

public class FieldTest {

    @Test
    public void construct() {
        Field field = Field.of("foo").with(Json.jString("bar"));
        assertEquals(field.name, "foo");
        assertEquals(field.value, Optional.of(Json.jString("bar")));
        assertEquals(field.classes, empty());
        assertEquals(field.title, empty());
        assertEquals(field.type, Type.TEXT);
    }
}
