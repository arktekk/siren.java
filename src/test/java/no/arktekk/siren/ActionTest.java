package no.arktekk.siren;

import org.junit.Test;

import java.net.URI;

import static io.vavr.control.Option.none;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class ActionTest {
    URI uri = URI.create("http://example.com/dostuff");

    @Test
    public void construct() {
        Action action = Action.of("æksjen", uri);
        assertEquals("æksjen", action.name);
        assertEquals(uri, action.href);
        assertEquals(none(), action.title);
        assertEquals(none(), action.method);
        assertEquals(none(), action.type);
        assertEquals(none(), action.fields);

        Action action1 = new Action("æksjen", uri, none(), none(), none(), none(), none());
        assertEquals(action1, action);
    }

    @Test
    public void withFields() {
        Action action = Action.of("name", uri);
        Action actionFields = action.with(Fields.of(Field.of("hello"), Field.of("world")));
        Action actionFields2 = action.with(Fields.of(Field.of("hello"), Field.of("world")));
        Action actionFields3 = action.with(Fields.of(Field.of("hello"), Field.of("world")));
        Action actionFields4 = action.with(Fields.of(Field.of("hello"), Field.of("world")));

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
}
