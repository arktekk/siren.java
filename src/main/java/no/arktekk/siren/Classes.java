package no.arktekk.siren;

import io.vavr.collection.List;
import no.arktekk.siren.util.StreamableIterable;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Function;


public final class Classes implements StreamableIterable<String> {

    private final List<String> classes;

    private Classes(List<String> classes) {
        this.classes = classes;
    }

    public Classes(Iterable<String> classes) {
        this(List.ofAll(classes));
    }

    public static Classes of(String clazz, String... classes) {
        return new Classes(List.of(classes).prepend(clazz));
    }

    public static Classes empty() {
        return new Classes(Collections.emptyList());
    }

    public boolean includes(Classes rel) {
        List<String> other = rel.classes;
        List<String> us = this.classes;
        return other.size() <= us.size() && us.zip(other).forAll(t -> t._1.equals(t._2));
    }

    public Classes add(String clazz) {
        return new Classes(classes.append(clazz));
    }

    public Classes addAll(Classes classes) {
        return new Classes(classes.classes.prependAll(this.classes));
    }

    public Classes remove(String clazz) {
        return new Classes(classes.remove(clazz));
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
