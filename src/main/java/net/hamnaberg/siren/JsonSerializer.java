package net.hamnaberg.siren;

import net.hamnaberg.siren.util.StreamUtils;
import org.glassfish.json.JsonFactory;

import javax.json.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface JsonSerializer<T> {
    T serialize(EmbeddedRepresentation embeddedRepresentation);

    T serialize(EmbeddedLink embeddedLink);

    T serialize(Siren siren);

    final class JavaxJsonSerializer implements JsonSerializer<JsonValue> {

        private static Function<Iterable<String>, JsonArray> FromIterableString =
                strings -> JsonFactory.arrayOf(StreamUtils.stream(strings).<JsonValue>map(JsonFactory::jsonString).collect(Collectors.toList()));

        private JsonObjectBuilder sirenBuilder(Siren siren) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("class", FromIterableString.apply(siren.classes));
            siren.properties.ifPresent(ps -> builder.add("properties", ps));
            if (!siren.entities.isEmpty())
                builder.add("entities", JsonFactory.arrayOf(siren.entities.stream().map(e -> e.toJson(this)).collect(Collectors.toList())));
            if (!siren.links.isEmpty())
                builder.add("links", JsonFactory.arrayOf(siren.links.stream().map(l -> {
                    JsonObjectBuilder link = Json.createObjectBuilder();
                    l.relations.ifPresent(rel -> link.add("rel", FromIterableString.apply(rel)));
                    link.add("href", l.href.toString()); // TODO: denne skal sikker encodes
                    return link.build();
                }).collect(Collectors.toList())));
            if (!siren.actions.isEmpty())
                builder.add("action", JsonFactory.arrayOf(siren.actions.stream().map(a -> {
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
                            // TODO: få inn field
                            return field.build();
                        }).collect(Collectors.toList())));
                    return action.build();
                }).collect(Collectors.toList())));
            siren.title.ifPresent(t -> builder.add("title", t));
            return builder;
        }

        public JsonValue serialize(Siren siren) {
            return sirenBuilder(siren).build();
        }

        public JsonValue serialize(EmbeddedRepresentation embeddedRepresentation) {
            return sirenBuilder(embeddedRepresentation.siren).add("rel", FromIterableString.apply(embeddedRepresentation.rels)).build();
        }

        public JsonValue serialize(EmbeddedLink embeddedLink) {
            JsonObjectBuilder object = Json.createObjectBuilder();
            object.add("class", FromIterableString.apply(embeddedLink.classes));
            object.add("rel", FromIterableString.apply(embeddedLink.rels));
            embeddedLink.href.ifPresent(h -> object.add("href", h.toString())); // TODO: denne skal sikker encodes
            embeddedLink.title.ifPresent(t -> object.add("title", t));
            embeddedLink.type.ifPresent(t -> object.add("type", t.format())); // TODO: riktig?
            return object.build();
        }
    }
}
