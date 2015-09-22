package net.hamnaberg.siren;

import javax.json.JsonObject;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Entity {
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

    public Optional<Action> getActionByName(String name) {
        return actions.stream().filter(a -> a.name.equalsIgnoreCase(name)).findFirst();
    }

    public Optional<URI> getHref() {
        return href;
    }

    public Relations getRels() {
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
