package no.arktekk.siren;

import javaslang.control.Option;
import no.arktekk.siren.util.CollectionUtils;
import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public final class Links implements StreamableIterable<Link> {

    private final List<Link> links;

    private Links(List<Link> links) {
        this.links = unmodifiableList(links);
    }

    public Links(Iterable<Link> links) {
        this(StreamUtils.stream(links).collect(Collectors.toList()));
    }

    public static Links empty() {
        return new Links(Collections.emptyList());
    }

    public static Links of(Link link, Link... links) {
        return new Links(CollectionUtils.asList(link, links));
    }

    public Links add(Link link) {
        List<Link> entities = new ArrayList<>(this.links);
        entities.add(link);
        return new Links(entities);
    }

    public Links remove(Rel rel) {
        return new Links(stream().filter(e -> !e.getRel().equals(rel)).collect(Collectors.toList()));
    }

    public Option<Link> getLinkByRel(Rel rel) {
        return Option.ofOptional(links.stream().filter(l -> l.rel.equals(rel)).findFirst());
    }

    public Option<Link> getLinkByRelIncludes(Rel rel) {
        return Option.ofOptional(links.stream().filter(l -> l.rel.includes(rel)).findFirst());
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
