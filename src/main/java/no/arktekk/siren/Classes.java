package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public final class Classes implements StreamableIterable<String> {

    private final List<String> classes;

    public Classes(Iterable<String> classes) {
        this.classes = unmodifiableList(StreamUtils.stream(classes).collect(Collectors.toList()));
    }

    public static Classes of(String clazz, String... classes) {
        return new Classes(new ArrayList<String>() {{
            add(clazz);
            addAll(asList(classes));
        }});
    }

    public Iterator<String> iterator() {
        return classes.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classes strings = (Classes) o;

        return classes.equals(strings.classes);

    }

    @Override
    public int hashCode() {
        return classes.hashCode();
    }
}
