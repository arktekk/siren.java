package net.hamnaberg.siren;

import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ActionTest {
    URI uri = URI.create("http://example.com/dostuff");

    @Test
    public void construct() {
        Action action = Action.of("balle", uri);
        assertEquals("balle", action.name);
        assertEquals(uri, action.href);
        assertEquals(Optional.empty(), action.title);
        assertEquals(Optional.empty(), action.method);
        assertEquals(Optional.empty(), action.type);
        assertEquals(Collections.emptyList(), action.fields);

        Action action1 = new Action("balle", uri, Optional.empty(), Optional.empty(), Optional.empty(), Collections.emptyList());
        assertEquals(action1, action);
    }

    @Test
    public void withFields() {
        Action action = Action.of("name", uri);
        Action actionFields = action.withFields(Field.of("hello"), Field.of("world"));
        Action actionFields2 = action.withFields(Arrays.asList(Field.of("hello"), Field.of("world")));
        Action actionFields3 = action.addFields(Arrays.asList(Field.of("hello"), Field.of("world")));
        Action actionFields4 = action.addFields(Field.of("hello"), Field.of("world"));

        assertNotSame(action, actionFields);
        assertNotSame(action, actionFields2);
        assertNotSame(action, actionFields3);
        assertNotSame(action, actionFields4);
        assertEquals(actionFields, actionFields2);
        assertEquals(actionFields, actionFields3);
        assertEquals(actionFields, actionFields4);
        assertEquals(actionFields2, actionFields3);
        assertEquals(actionFields2, actionFields4);
        assertEquals(actionFields3, actionFields4);
    }

    @Test
    public void replaceFields() {
        Action actionFields = Action.of("name", uri, Arrays.asList(Field.of("hello"), Field.of("world")));
        List<Field> replacement = Arrays.asList(Field.of("hello", "world"), Field.of("world", "of-hurt"));
        Action replaced = actionFields.replace(replacement);
        assertEquals(actionFields.fields.size(), replaced.fields.size());
        assertEquals(Action.of("name", uri, replacement), replaced);
    }

}
