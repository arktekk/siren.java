package no.arktekk.siren;

import no.arktekk.siren.util.CollectionUtils;
import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public final class Classes implements StreamableIterable<String> {

    private final List<String> classes;

    private Classes(List<String> classes) {
        this.classes = unmodifiableList(classes);
    }

    public Classes(Iterable<String> classes) {
        this(StreamUtils.stream(classes).collect(Collectors.toList()));
    }

    public static Classes of(String clazz, String... classes) {
        return new Classes(CollectionUtils.asList(clazz, classes));
    }

    public static Classes empty() {
        return new Classes(Collections.emptyList());
    }

    public boolean includes(Classes rel) {
        List<String> other = rel.classes;
        List<String> us = this.classes;
        return other.size() <= us.size() && StreamUtils.zip(us.stream(), other.stream(), String::equals).allMatch(t -> t);
    }

    public Classes add(String clazz) {
        List<String> list = new ArrayList<>(classes);
        list.add(clazz);
        return new Classes(list);
    }

    public Classes addAll(Classes classes) {
        List<String> list = new ArrayList<>(this.classes);
        list.addAll(classes.classes);
        return new Classes(list);
    }

    public Classes remove(String clazz) {
        List<String> list = new ArrayList<>(classes);
        list.remove(clazz);
        return new Classes(list);
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
