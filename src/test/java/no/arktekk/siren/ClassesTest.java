package no.arktekk.siren;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class ClassesTest {

    @Test
    public void includesShouldIncludeLesser() {
        Classes of = Classes.of("collection", "thing");
        Classes of2 = Classes.of("collection");

        assertThat("of should include of2", of.includes(of2), CoreMatchers.is(true));
        assertThat("of2 should not include of", of2.includes(of), CoreMatchers.is(false));
    }
}
