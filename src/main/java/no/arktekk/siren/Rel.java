package no.arktekk.siren;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import no.arktekk.siren.util.StreamableIterable;

public final class Rel implements StreamableIterable<String> {

    private final List<String> rels;

    private Rel(List<String> rels) {
        this.rels = rels;
    }

    public Rel(Iterable<String> rels) {
        this(List.ofAll(rels));
    }

    public static Rel of(String rel, String... rels) {
        return new Rel(List.of(rels).prepend(rel));
    }

    public boolean includes(Rel rel) {
        List<String> other = rel.rels;
        List<String> us = this.rels;
        return other.size() <= us.size() && us.zip(other).forAll(t -> t._1.equals(t._2));
    }

    public boolean includes(String rel) {
        return includes(Rel.of(rel));
    }

    public Rel add(String rel) {
        return new Rel(this.rels.append(rel));
    }

    public Rel addAll(Rel rels) {
        return new Rel(rels.rels.prependAll(this.rels));
    }

    public Rel remove(String rel) {
        return new Rel(this.rels.remove(rel));
    }

    public Iterator<String> iterator() {
        return rels.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rel strings = (Rel) o;

        return rels.equals(strings.rels);
    }

    @Override
    public int hashCode() {
        return rels.hashCode();
    }

    @Override
    public String toString() {
        return rels.toString();
    }
}
