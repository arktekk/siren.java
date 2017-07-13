package no.arktekk.siren;

import io.vavr.control.Option;
import net.hamnaberg.json.Json;

import static io.vavr.control.Option.none;

public final class Entity implements JsonSerializable {
    public final Option<Classes> classes;
    public final Option<Json.JObject> properties;
    public final Option<Entities> entities;
    public final Option<Actions> actions;
    public final Option<Links> links;
    public final Option<String> title;

    public Entity(Option<Classes> classes, Option<Json.JObject> properties, Option<Entities> entities, Option<Actions> actions, Option<Links> links, Option<String> title) {
        this.classes = classes;
        this.properties = properties;
        this.entities = entities;
        this.actions = actions;
        this.links = links;
        this.title = title;
    }

    public static Entity of() {
        return new Entity(none(), none(), none(), none(), none(), none());
    }

    public Entity with(Classes classes) {
        return new Entity(Option.of(classes), properties, entities, actions, links, title);
    }

    public Entity with(Json.JObject properties) {
        return new Entity(classes, Option.of(properties), entities, actions, links, title);
    }

    public Entity with(Entities entities) {
        return new Entity(classes, properties, Option.of(entities), actions, links, title);
    }

    public Entity with(Actions actions) {
        return new Entity(classes, properties, entities, Option.of(actions), links, title);
    }

    public Entity with(Links links) {
        return new Entity(classes, properties, entities, actions, Option.of(links), title);
    }

    public Entity with(String title) {
        return new Entity(classes, properties, entities, actions, links, Option.of(title));
    }

    public SubEntity.EmbeddedRepresentation toEmbedded(Rel rel) {
        return new SubEntity.EmbeddedRepresentation(rel, this);
    }

    public Option<Link> getLinkByRel(Rel rel) {
        return links.flatMap(links -> links.getLinkByRel(rel));
    }

    public Option<Link> getLinkByRelIncludes(Rel rel) {
        return links.flatMap(links -> links.getLinkByRelIncludes(rel));
    }

    public Option<Action> getActionByName(String name) {
        return actions.flatMap(actions -> actions.getActionByName(name));
    }

    public Option<Action> getActionByClasses(Classes classes) {
        return actions.flatMap(actions -> actions.getActionByClasses(classes));
    }

    public Option<Action> getActionByClassesIncludes(Classes classes) {
        return actions.flatMap(actions -> actions.getActionByClassesIncludes(classes));
    }

    public Option<SubEntity> getEntityByRel(Rel rel) {
        return entities.flatMap(entities -> entities.getEntityByRel(rel));
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

    public Option<Classes> getClasses() {
        return classes;
    }

    public Option<Json.JObject> getProperties() {
        return properties;
    }

    public Json.JObject getPropertiesOrEmpty() {
        return properties.getOrElse(Json.jEmptyObject());
    }

    public Option<Entities> getEntities() {
        return entities;
    }

    public Entities getEntitiesOrEmpty() {
        return entities.getOrElse(Entities.empty());
    }

    public Option<Actions> getActions() {
        return actions;
    }

    public Actions getActionsOrEmpty() {
        return actions.getOrElse(Actions.empty());
    }

    public Option<Links> getLinks() {
        return links;
    }

    public Links getLinksOrEmpty() {
        return links.getOrElse(Links.empty());
    }

    public Option<String> getTitle() {
        return title;
    }

    public <T> T toJson(JsonSerializer<T> serializer) {
        return serializer.serialize(this);
    }
}
