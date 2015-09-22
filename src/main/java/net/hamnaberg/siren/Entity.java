package net.hamnaberg.siren;

import javax.json.JsonObject;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Entity implements WithLinks, WithEntities, WithActions {
    public final Optional<URI> href;
    public final Relations rels;
    public final Classes classes;
    public final Optional<JsonObject> properties;
    public final List<Entity> entities;
    public final List<Action> actions;
    public final List<Link> links;

    public static Entity of(URI href, Relations rels) {
        return new Entity(Optional.of(href), rels, Classes.empty(), Optional.<JsonObject>empty(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public Entity(Optional<URI> href, Relations rels, Classes classes, Optional<JsonObject> properties, List<Entity> entities, List<Action> actions, List<Link> links) {
        this.href = href;
        this.rels = rels;
        this.classes = classes;
        this.properties = properties;
        this.entities = entities;
        this.actions = actions;
        this.links = links;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!href.equals(entity.href)) return false;
        if (!rels.equals(entity.rels)) return false;
        if (!classes.equals(entity.classes)) return false;
        if (!properties.equals(entity.properties)) return false;
        if (!entities.equals(entity.entities)) return false;
        if (!actions.equals(entity.actions)) return false;
        return links.equals(entity.links);

    }

    @Override
    public int hashCode() {
        int result = href.hashCode();
        result = 31 * result + rels.hashCode();
        result = 31 * result + classes.hashCode();
        result = 31 * result + properties.hashCode();
        result = 31 * result + entities.hashCode();
        result = 31 * result + actions.hashCode();
        result = 31 * result + links.hashCode();
        return result;
    }

    public Optional<URI> getHref() {
        return href;
    }

    public Relations getRelations() {
        return rels;
    }

    public Siren toSiren() {
        return new Siren(classes, properties, Collections.emptyList(), actions, links);
    }

    public Classes getClasses() {
        return classes;
    }

    public Optional<JsonObject> getProperties() {
        return properties;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Action> getActions() {
        return actions;
    }

    public List<Link> getLinks() {
        return links;
    }
}
