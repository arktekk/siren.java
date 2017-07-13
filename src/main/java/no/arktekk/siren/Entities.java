package no.arktekk.siren;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import no.arktekk.siren.util.StreamableIterable;

import java.util.Collections;
import java.util.function.Function;

public final class Entities implements StreamableIterable<SubEntity> {

    private final List<SubEntity> subEntities;

    public Entities(List<SubEntity> entities) {
        this.subEntities = entities;
    }

    public Entities(Iterable<SubEntity> entities) {
        this(List.ofAll(entities));
    }

    public static Entities empty() {
        return new Entities(Collections.emptyList());
    }

    public static Entities of(SubEntity entity, SubEntity... entities) {
        return new Entities(List.of(entities).prepend(entity));
    }

    public Entities add(SubEntity entity) {
        return new Entities(this.subEntities.append(entity));
    }

    public Entities remove(Rel rel) {
        return new Entities(this.subEntities.filter(e -> !e.getRel().equals(rel)));
    }

    public Option<SubEntity> getEntityByRel(Rel rel) {
        return this.subEntities.find(e -> e.getRel().equals(rel));
    }

    public <U> List<U> map(Function<? super SubEntity, ? extends U> mapper) {
        return subEntities.map(mapper);
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
