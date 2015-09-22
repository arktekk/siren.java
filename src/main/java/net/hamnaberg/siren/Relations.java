package net.hamnaberg.siren;

import net.hamnaberg.siren.util.StreamUtils;
import net.hamnaberg.siren.util.StreamableIterable;

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

    public Relations(List<String> rels) {
        this.rels = Collections.unmodifiableList(rels);
    }

    public Optional<String> headOption() {
        return stream().findFirst();
    }

    public boolean isEmpty() {
        return rels.isEmpty();
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
