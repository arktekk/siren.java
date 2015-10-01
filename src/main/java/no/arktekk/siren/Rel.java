package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public final class Rel implements StreamableIterable<String> {

    private final List<String> rels;

    public Rel(Iterable<String> rels) {
        this.rels = unmodifiableList(StreamUtils.stream(rels).collect(Collectors.toList()));
    }

    public static Rel of(String rel, String... rels) {
        return new Rel(new ArrayList<String>() {{
            add(rel);
            addAll(asList(rels));
        }});
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
