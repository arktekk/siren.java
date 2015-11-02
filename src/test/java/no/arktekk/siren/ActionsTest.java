package no.arktekk.siren;

import java.net.URI;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ActionsTest {

    @Test
    public void uniqueName() {
        Actions actions = Actions.of(
            Action.of("unique", URI.create("www.arktekk.no/1")),
            Action.of("unique", URI.create("www.arktekk.no/2")));
        assertTrue(1 == actions.stream().count());
    }
}
