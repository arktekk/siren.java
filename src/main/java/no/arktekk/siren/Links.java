package no.arktekk.siren;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import no.arktekk.siren.util.StreamableIterable;

import java.util.function.Function;

public final class Links implements StreamableIterable<Link> {

    private final List<Link> links;

    private Links(List<Link> links) {
        this.links = links;
    }

    public Links(Iterable<Link> links) {
        this(List.ofAll(links));
    }

    public static Links empty() {
        return new Links(List.empty());
    }

    public static Links of(Link link, Link... links) {
        return new Links(List.of(links).prepend(link));
    }

    public Links add(Link link) {
        return new Links(this.links.append(link));
    }

    public Links remove(Rel rel) {
        return new Links(links.filter(e -> !e.getRel().equals(rel)));
    }

    public Option<Link> getLinkByRel(Rel rel) {
        return links.find(l -> l.rel.equals(rel));
    }

    public Option<Link> getLinkByRelIncludes(Rel rel) {
        return links.find(l -> l.rel.includes(rel));
    }

    public <U> List<U> map(Function<? super Link, ? extends U> mapper) {
        return links.map(mapper);
    }

    public <U> List<U> flatMap(Function<? super Link, ? extends Iterable<? extends U>> mapper) {
        return links.flatMap(mapper);
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
