package no.arktekk.siren;

import io.vavr.control.Option;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class MIMETypeTest {

    @Test
    public void parseLotsOfExamples() {
        Map<String, MIMEType> map = new HashMap<>();
        map.put("application/json", MIMEType.application("json"));
        map.put("application/atom+xml", MIMEType.application("atom+xml"));
        map.put("application/x-www-form-urlencoded", MIMEType.application("x-www-form-urlencoded"));
        map.put("application/xml; charset=utf-8", MIMEType.application("xml", Collections.singletonMap("charset", "utf-8")));
        map.put("application/vnd.collection+json; charset=utf-8; profile=http://example.com/balle&heioghopp=meh",
                MIMEType.application("vnd.collection+json", new HashMap<String, String>() {{
                    put("charset", "utf-8");
                    put("profile", "http://example.com/balle&heioghopp=meh");
                }})
        );
        AtomicInteger count = new AtomicInteger();
        map.forEach((input, ex) -> {
            Option<MIMEType> parsed = MIMEType.parse(input);
            assertTrue(parsed.isDefined());
            assertEquals(parsed.get(), ex);
            count.incrementAndGet();
        });
        assertEquals(map.size(), count.get());
    }

    @Test
    public void formatExamples() {
        Map<String, MIMEType> map = new HashMap<>();
        map.put("application/json", MIMEType.application("json"));
        map.put("application/atom+xml", MIMEType.application("atom+xml"));
        map.put("application/xml; charset=utf-8", MIMEType.application("xml", Collections.singletonMap("charset", "utf-8")));
        map.put("application/vnd.collection+json; charset=utf-8; profile=http://example.com/balle&heioghopp=meh",
                MIMEType.application("vnd.collection+json", new HashMap<String, String>(){{
                    put("charset", "utf-8");
                    put("profile", "http://example.com/balle&heioghopp=meh");
                }})
        );
        AtomicInteger count = new AtomicInteger();

        map.forEach((ex, input) -> {
            assertEquals(ex, input.format());
            count.incrementAndGet();
        });

        assertEquals(map.size(), count.get());
    }

    @Test
    public void equalsTest() {
        MIMEType exp = MIMEType.application("json");
        assertThat("json was equal to ALL", exp, CoreMatchers.not(CoreMatchers.equalTo(MIMEType.All)));
    }

    @Test
    public void parse() {
        Option<MIMEType> type = MIMEType.parse("application/x-www-form-urlencoded");
        assertTrue(type.map(MIMEType.application("x-www-form-urlencoded")::equals).getOrElse(false));
    }
}
