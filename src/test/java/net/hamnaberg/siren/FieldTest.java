package net.hamnaberg.siren;

import org.glassfish.json.JsonFactory;
import org.junit.Test;

import javax.json.JsonValue;
import java.util.Optional;

import static org.junit.Assert.*;

public class FieldTest {
    @Test
    public void construct() {
        Field field = Field.of("foo", "bar");
        assertEquals(field.name, "foo");
        assertEquals(field.value, Optional.of(JsonFactory.jsonString("bar")));
        assertEquals(field.classes, Classes.empty());
        assertEquals(field.title, Optional.empty());
        assertEquals(field.type, Field.Type.TEXT);
    }

    @Test
    public void replaceValue() {
        Field field = Field.of("foo", "bar");
        Field falseField = field.withValue(JsonValue.FALSE);
        assertNotSame(field, falseField);
        assertNotEquals(field, falseField);
        assertEquals(falseField, Field.of("foo", JsonValue.FALSE));
    }

    @Test
    public void replaceWithNoValue() {
        Field field = Field.of("foo", "bar");
        Field noValue = field.noValue();
        assertNotSame(field, noValue);
        assertNotEquals(field, noValue);
        assertEquals(noValue, Field.of("foo"));
    }

    @Test
    public void addTitle() {
        Field field = Field.of("foo", "bar");
        Field titled = field.withTitle("title");
        assertNotSame(field, titled);
        assertNotEquals(field, titled);
        assertEquals(titled, new Field("foo", Classes.empty(), Field.Type.TEXT, Optional.of(JsonFactory.jsonString("bar")), Optional.of("title")));
        assertEquals(titled.noTitle(), new Field("foo", Classes.empty(), Field.Type.TEXT, Optional.of(JsonFactory.jsonString("bar")), Optional.empty()));
    }
}
