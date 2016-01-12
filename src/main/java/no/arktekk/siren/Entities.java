package no.arktekk.siren;

import no.arktekk.siren.util.CollectionUtils;
import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public final class Entities implements StreamableIterable<SubEntity> {

    private final List<SubEntity> subEntities;

    public Entities(List<SubEntity> entities) {
        this.subEntities = unmodifiableList(entities);
    }

    public Entities(Iterable<SubEntity> entities) {
        this(StreamUtils.stream(entities).collect(Collectors.toList()));
    }

    public static Entities of(SubEntity entity, SubEntity... entities) {
        return new Entities(CollectionUtils.asList(entity, entities));
    }

    public Entities add(SubEntity entity) {
        List<SubEntity> entities = new ArrayList<>(this.subEntities);
        entities.add(entity);
        return new Entities(entities);
    }

    public Entities remove(Rel rel) {
        return new Entities(stream().filter(e -> !e.getRel().equals(rel)).collect(Collectors.toList()));
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
