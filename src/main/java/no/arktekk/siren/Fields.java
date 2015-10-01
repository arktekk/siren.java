package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public final class Fields implements StreamableIterable<Field> {

    private final List<Field> fields;

    public Fields(Iterable<Field> fields) {
        this.fields = unmodifiableList(StreamUtils.stream(fields).collect(Collectors.toList()));
    }

    public static Fields of(Field field, Field ... fields) {
        return new Fields(new ArrayList<Field>() {{
            add(field);
            addAll(asList(fields));
        }});
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
