package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class Links implements StreamableIterable<Link> {

    private List<Link> links;

    public Links(Iterable<Link> links) {
        this.links = unmodifiableList(StreamUtils.stream(links).collect(Collectors.toList()));
    }

    public static Links of(Link link, Link... links) {
        return new Links(new ArrayList<Link>() {{
            add(link);
            addAll(asList(links));
        }});
    }

    public Iterator<Link> iterator() {
        return links.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Links links1 = (Links) o;

        return links.equals(links1.links);

    }

    @Override
    public int hashCode() {
        return links.hashCode();
    }
}
