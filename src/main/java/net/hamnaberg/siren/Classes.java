package net.hamnaberg.siren;

import net.hamnaberg.siren.util.StreamUtils;
import net.hamnaberg.siren.util.StreamableIterable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Classes implements StreamableIterable<String> {
    private final List<String> classes;

    public static Classes empty() {
        return new Classes(Collections.emptyList());
    }

    public Classes(Iterable<String> classes) {
        this(StreamUtils.stream(classes).collect(Collectors.toList()));
    }

    Classes(List<String> classes) {
        this.classes = Collections.unmodifiableList(classes);
    }

    public Classes add(String... clazz) {
        return add(Arrays.asList(clazz));
    }

    public Classes add(Iterable<String> clazz) {
        return new Classes(
                Stream.concat(classes.stream(), StreamUtils.stream(clazz))
                        .collect(Collectors.toList())
        );
    }

    @Override
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
