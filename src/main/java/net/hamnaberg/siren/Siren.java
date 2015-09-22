package net.hamnaberg.siren;

import javax.json.JsonObject;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Siren {
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

    public Entity toEntity(URI uri, Relations rels) {
        return new Entity(Optional.of(uri), rels, classes, properties, Collections.emptyList(), actions, links);
    }

    public Optional<Action> getActionByName(String name) {
        return actions.stream().filter(a -> a.name.equalsIgnoreCase(name)).findFirst();
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
