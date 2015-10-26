package no.arktekk.siren;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class RelTest {

    @Test
    public void includesShouldIncludeLesser() {
        Rel of = Rel.of("collection", "thing");
        Rel of2 = Rel.of("collection");

        assertThat("of should include of2", of.includes(of2), CoreMatchers.is(true));
        assertThat("of2 should not include of", of2.includes(of), CoreMatchers.is(false));
    }
}
