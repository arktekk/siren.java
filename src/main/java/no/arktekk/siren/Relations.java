package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.*;
import java.util.stream.Collectors;

public final class Relations implements StreamableIterable<String> {
    private final List<String> rels;

    public static Relations of(String rel) {
        return new Relations(Collections.singletonList(rel));
    }

    public static Relations of(String... rels) {
        return new Relations(Arrays.asList(rels));
    }

    public static Relations of(Iterable<String> rels) {
        return new Relations(StreamUtils.stream(rels).collect(Collectors.toList()));
    }

    Relations(List<String> rels) {
        this.rels = Collections.unmodifiableList(rels);
    }

    public boolean matches(String... rels) {
        List<String> list = Arrays.asList(rels);
        return stream().allMatch(list::contains);
    }

    @Override
    public String toString() {
        return rels.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return rels.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relations strings = (Relations) o;

        return rels.equals(strings.rels);
    }

    @Override
    public int hashCode() {
        return rels.hashCode();
    }
}
