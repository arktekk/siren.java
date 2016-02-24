package no.arktekk.siren;

import no.arktekk.siren.util.CollectionUtils;
import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public final class Fields implements StreamableIterable<Field> {

    private final List<Field> fields;

    private Fields(List<Field> fields) {
        this.fields = unmodifiableList(fields);
    }

    public Fields(Iterable<Field> fields) {
        this(StreamUtils.stream(fields).collect(Collectors.toList()));
    }

    public static Fields of(Field field, Field ... fields) {
        return new Fields(CollectionUtils.asList(field, fields));
    }

    public Fields replace(Field field) {
        return remove(field.name).add(field);
    }

    public Fields add(Field field) {
        List<Field> fields = new ArrayList<>(this.fields);
        fields.add(field);
        return new Fields(fields);
    }

    public Fields remove(String name) {
        return new Fields(stream().filter(f -> !f.name.equals(name)).collect(Collectors.toList()));
    }

    public Iterator<Field> iterator() {
        return fields.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fields fields1 = (Fields) o;

        return fields.equals(fields1.fields);

    }

    @Override
    public int hashCode() {
        return fields.hashCode();
    }
}
