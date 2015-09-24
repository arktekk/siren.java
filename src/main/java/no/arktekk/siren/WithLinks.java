package no.arktekk.siren;

import java.util.List;

public interface WithLinks {

/*
    default List<Link> getLinksByRel(Relations rel) {
        return getLinks().stream().filter(l -> l.getRelations().equals(rel)).collect(Collectors.toList());
    }

    default List<Link> getLinksByRel(String rel) {
        return getLinks().stream().filter(l -> l.getRelations().matches(rel)).collect(Collectors.toList());
    }

    default Optional<Link> getLinkByRel(Relations rel) {
        return getLinks().stream().filter(l -> l.getRelations().equals(rel)).findFirst();
    }

    default Optional<Link> getLinkByRel(String rel) {
        return getLinks().stream().filter(l -> l.getRelations().matches(rel)).findFirst();
    }
*/

    List<Link> getLinks();
}
