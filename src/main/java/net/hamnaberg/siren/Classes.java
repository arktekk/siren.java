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

    private Classes(List<String> classes) {
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

    public Optional<String> headOption() {
        return stream().findFirst();
    }

    @Override
    public Iterator<String> iterator() {
        return classes.iterator();
    }
}
