package no.arktekk.siren;

import javax.json.JsonObject;
import java.util.Optional;

import static java.util.Optional.empty;

public final class Entity implements JsonSerializable {
    public final Optional<Classes> classes;
    public final Optional<JsonObject> properties;
    public final Optional<Entities> entities;
    public final Optional<Actions> actions;
    public final Optional<Links> links;
    public final Optional<String> title;

    public Entity(Optional<Classes> classes, Optional<JsonObject> properties, Optional<Entities> entities, Optional<Actions> actions, Optional<Links> links, Optional<String> title) {
        this.classes = classes;
        this.properties = properties;
        this.entities = entities;
        this.actions = actions;
        this.links = links;
        this.title = title;
    }

    public static Entity of() {
        return new Entity(empty(), empty(), empty(), empty(), empty(), empty());
    }

    public Entity with(Classes classes) {
        return new Entity(Optional.of(classes), properties, entities, actions, links, title);
    }

    public Entity with(JsonObject properties) { // TODO Få vekk JsonObject
        return new Entity(classes, Optional.of(properties), entities, actions, links, title);
    }

    public Entity with(Entities entities) {
        return new Entity(classes, properties, Optional.of(entities), actions, links, title);
    }

    public Entity with(Actions actions) {
        return new Entity(classes, properties, entities, Optional.of(actions), links, title);
    }

    public Entity with(Links links) {
        return new Entity(classes, properties, entities, actions, Optional.of(links), title);
    }

    public Entity with(String title) {
        return new Entity(classes, properties, entities, actions, links, Optional.of(title));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!classes.equals(entity.classes)) return false;
        if (!properties.equals(entity.properties)) return false;
        if (!entities.equals(entity.entities)) return false;
        if (!actions.equals(entity.actions)) return false;
        if (!links.equals(entity.links)) return false;
        return title.equals(entity.title);

    }

    @Override
    public int hashCode() {
        int result = classes.hashCode();
        result = 31 * result + properties.hashCode();
        result = 31 * result + entities.hashCode();
        result = 31 * result + actions.hashCode();
        result = 31 * result + links.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    public <T> T toJson(JsonSerializer<T> serializer) {
        return serializer.serialize(this);
    }
}
