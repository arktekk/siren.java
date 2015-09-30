package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import org.glassfish.json.JsonFactory;

import javax.json.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface JsonSerializer<T> {
    T serialize(EmbeddedRepresentation embeddedRepresentation);

    T serialize(EmbeddedLink embeddedLink);

    T serialize(Entity entity);

    final class JavaxJsonSerializer implements JsonSerializer<JsonValue> {

        private static Function<Iterable<String>, JsonArray> FromIterableString =
                strings -> JsonFactory.arrayOf(StreamUtils.stream(strings).<JsonValue>map(JsonFactory::jsonString).collect(Collectors.toList()));

        private JsonObjectBuilder sirenBuilder(Entity entity) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("class", FromIterableString.apply(entity.classes));
            entity.properties.ifPresent(ps -> builder.add("properties", ps));
            if (!entity.entities.isEmpty())
                builder.add("entities", JsonFactory.arrayOf(entity.entities.stream().map(e -> e.toJson(this)).collect(Collectors.toList())));
            if (!entity.links.isEmpty())
                builder.add("links", JsonFactory.arrayOf(entity.links.stream().map(l -> {
                    JsonObjectBuilder link = Json.createObjectBuilder();
                    link.add("rel", FromIterableString.apply(l.rel));
                    link.add("href", l.href.toString()); // TODO: denne skal sikker encodes
                    return link.build();
                }).collect(Collectors.toList())));
            if (!entity.actions.isEmpty())
                builder.add("actions", JsonFactory.arrayOf(entity.actions.stream().map(a -> {
                    JsonObjectBuilder action = Json.createObjectBuilder();
                    action.add("name", a.name);
                    action.add("class", FromIterableString.apply(a.classes));
                    a.method.ifPresent(m -> action.add("method", m.name()));
                    action.add("href", a.href.toString());  // TODO: denne skal sikker encodes
                    a.title.ifPresent(t -> action.add("title", t));
                    a.type.ifPresent(t -> action.add("type", t.format()));
                    if (!a.fields.isEmpty())
                        action.add("fields", JsonFactory.arrayOf(a.fields.stream().map(f -> {
                            JsonObjectBuilder field = Json.createObjectBuilder();
                            field.add("name", f.name);
                            if (!f.classes.isEmpty())
                                field.add("class", FromIterableString.apply(f.classes));
                            field.add("type", f.type.value);
                            f.value.ifPresent(v -> field.add("value", v));
                            f.title.ifPresent(t -> field.add("title", t));
                            return field.build();
                        }).collect(Collectors.toList())));
                    return action.build();
                }).collect(Collectors.toList())));
            entity.title.ifPresent(t -> builder.add("title", t));
            return builder;
        }

        public JsonValue serialize(Entity entity) {
            return sirenBuilder(entity).build();
        }

        public JsonValue serialize(EmbeddedRepresentation embeddedRepresentation) {
            return sirenBuilder(embeddedRepresentation.entity).add("rel", FromIterableString.apply(embeddedRepresentation.rel)).build();
        }

        public JsonValue serialize(EmbeddedLink embeddedLink) {
            JsonObjectBuilder object = Json.createObjectBuilder();
            object.add("class", FromIterableString.apply(embeddedLink.classes));
            object.add("rel", FromIterableString.apply(embeddedLink.rel));
            object.add("href", embeddedLink.href.toString()); // TODO: denne skal sikkert encodes
            embeddedLink.title.ifPresent(t -> object.add("title", t));
            embeddedLink.type.ifPresent(t -> object.add("type", t.format()));
            return object.build();
        }
    }
}
