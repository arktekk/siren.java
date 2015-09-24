package net.hamnaberg.siren;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface WithEntities {

/*
    default List<Entity> getEntitiesByRel(Relations rel) {
        return getEntities().stream().filter(l -> l.getRelations().equals(rel)).collect(Collectors.toList());
    }

    default List<Entity> getEntitiesByRel(String rel) {
        return getEntities().stream().filter(l -> l.getRelations().matches(rel)).collect(Collectors.toList());
    }

    default Optional<Entity> getEntityByRel(Relations rel) {
        return getEntities().stream().filter(l -> l.getRelations().equals(rel)).findFirst();
    }

    default Optional<Entity> getEntityByRel(String rel) {
        return getEntities().stream().filter(l -> l.getRelations().matches(rel)).findFirst();
    }
*/

    List<Entity> getEntities();
}
