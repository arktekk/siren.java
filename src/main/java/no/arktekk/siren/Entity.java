package no.arktekk.siren;

import net.hamnaberg.json.Json;

import java.util.Optional;

import static java.util.Optional.empty;

public final class Entity implements JsonSerializable {
    public final Optional<Classes> classes;
    public final Optional<Json.JObject> properties;
    public final Optional<Entities> entities;
    public final Optional<Actions> actions;
    public final Optional<Links> links;
    public final Optional<String> title;

    public Entity(Optional<Classes> classes, Optional<Json.JObject> properties, Optional<Entities> entities, Optional<Actions> actions, Optional<Links> links, Optional<String> title) {
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

    public Entity with(Json.JObject properties) {
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

    public SubEntity.EmbeddedRepresentation toEmbedded(Rel rel) {
        return new SubEntity.EmbeddedRepresentation(rel, this);
    }

    public Optional<Link> getLinkByRel(Rel rel) {
        return links.flatMap(links -> links.stream().filter(l -> l.rel.equals(rel)).findFirst());
    }

    public Optional<Link> getLinkByRelIncludes(Rel rel) {
        return links.flatMap(links -> links.stream().filter(l -> l.rel.includes(rel)).findFirst());
    }

    public Optional<Action> getActionByName(String name) {
        return actions.flatMap(actions -> actions.stream().filter(l -> l.name.equals(name)).findFirst());
    }

    public Optional<Action> getActionByClasses(Classes classes) {
        return actions.flatMap(actions -> actions.stream().filter(l -> l.classes.filter(classes::equals).isPresent()).findFirst());
    }
    public Optional<Action> getActionByClassesIncludes(Classes classes) {
        return actions.flatMap(actions -> actions.stream().filter(l -> l.classes.filter(classes::includes).isPresent()).findFirst());
    }

    public Optional<SubEntity> getEntityByRel(Rel rel) {
        return entities.flatMap(entities ->
                        entities.stream().
                                filter(l -> l.fold(e -> e.rel.equals(rel), e -> e.rel.equals(rel))).
                                findFirst()
        );
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
