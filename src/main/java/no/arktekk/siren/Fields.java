package no.arktekk.siren;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import no.arktekk.siren.util.StreamableIterable;

import java.util.function.Function;


public final class Fields implements StreamableIterable<Field> {

    private final List<Field> fields;

    private Fields(List<Field> fields) {
        this.fields = fields;
    }

    public Fields(Iterable<Field> fields) {
        this(List.ofAll(fields));
    }

    public static Fields of(Field field, Field ... fields) {
        return new Fields(List.of(fields).prepend(field));
    }

    public Fields replace(Field field) {
        return remove(field.name).add(field);
    }

    public Fields add(Field field) {
        return new Fields(fields.append(field));
    }

    public Fields remove(String name) {
        return new Fields(fields.filter(f -> !f.name.equals(name)));
    }

    public <U> List<U> map(Function<? super Field, ? extends U> mapper) {
        return fields.map(mapper);
    }

    public <U> List<U> flatMap(Function<? super Field, ? extends Iterable<? extends U>> mapper) {
        return fields.flatMap(mapper);
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
