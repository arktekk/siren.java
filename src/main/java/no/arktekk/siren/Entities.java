package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public final class Entities implements StreamableIterable<SubEntity> {

    private List<SubEntity> subEntities;

    public Entities(Iterable<SubEntity> entities) {
        this.subEntities = unmodifiableList(StreamUtils.stream(entities).collect(Collectors.toList()));
    }

    public static Entities of(SubEntity entity, SubEntity... entities) {
        return new Entities(new ArrayList<SubEntity>() {{
            add(entity);
            addAll(asList(entities));
        }});
    }

    public Iterator<SubEntity> iterator() {
        return subEntities.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entities that = (Entities) o;

        return subEntities.equals(that.subEntities);

    }

    @Override
    public int hashCode() {
        return subEntities.hashCode();
    }
}
