package net.hamnaberg.siren;

import javax.json.JsonObject;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Siren implements WithLinks, WithEntities, WithActions {
    public final Classes classes;
    public final Optional<JsonObject> properties;
    public final List<Entity> entities;
    public final List<Action> actions;
    public final List<Link> links;

    public static Siren empty() {
        return new Siren(Classes.empty(), Optional.<JsonObject>empty(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public Siren(Classes classes, Optional<JsonObject> properties, List<Entity> entities, List<Action> actions, List<Link> links) {
        this.classes = classes;
        this.properties = properties;
        this.entities = entities;
        this.actions = actions;
        this.links = links;
    }

    public Entity toEntity(Optional<URI> href, Relations rels) {
        return new Entity(href, rels, classes, properties, Collections.emptyList(), actions, links);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Siren siren = (Siren) o;

        if (!classes.equals(siren.classes)) return false;
        if (!properties.equals(siren.properties)) return false;
        if (!entities.equals(siren.entities)) return false;
        if (!actions.equals(siren.actions)) return false;
        return links.equals(siren.links);

    }

    @Override
    public int hashCode() {
        int result = classes.hashCode();
        result = 31 * result + properties.hashCode();
        result = 31 * result + entities.hashCode();
        result = 31 * result + actions.hashCode();
        result = 31 * result + links.hashCode();
        return result;
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
