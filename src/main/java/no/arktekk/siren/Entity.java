package no.arktekk.siren;

import javax.json.JsonObject;
import java.util.List;
import java.util.Optional;

public final class Entity implements JsonSerializable {
    public final Classes classes;
    public final Optional<JsonObject> properties;
    public final List<SubEntity> entities;
    public final List<Action> actions;
    public final List<Link> links;
    public final Optional<String> title;

    public Entity(Classes classes, Optional<JsonObject> properties, List<SubEntity> entities, List<Action> actions, List<Link> links, Optional<String> title) {
        this.classes = classes;
        this.properties = properties;
        this.entities = entities;
        this.actions = actions;
        this.links = links;
        this.title = title;
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
