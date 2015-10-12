package no.arktekk.siren;

import com.fasterxml.jackson.core.JsonFactory;
import net.hamnaberg.json.Json;
import no.arktekk.siren.SubEntity.EmbeddedLink;
import no.arktekk.siren.SubEntity.EmbeddedRepresentation;
import no.arktekk.siren.util.StreamUtils;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface JsonSerializer<T> {
    T serialize(EmbeddedRepresentation embeddedRepresentation);

    T serialize(EmbeddedLink embeddedLink);

    T serialize(Entity entity);

    final class ImmutableJsonSerializer implements JsonSerializer<Json.JValue> {

        private static Function<Iterable<String>, Json.JArray> FromIterableString =
                strings -> Json.jArray((StreamUtils.stream(strings).map(Json::jString).collect(Collectors.toList())));

        private Json.JObject sirenBuilder(Entity entity) {
            Map<String, Json.JValue> map = new LinkedHashMap<>();
            entity.classes.ifPresent(cs -> map.put("class", FromIterableString.apply(cs)));
            entity.properties.ifPresent(ps -> map.put("properties", ps));
            entity.entities.ifPresent(es -> map.put("entities", Json.jArray(es.stream().map(e -> e.toJson(this)).collect(Collectors.toList()))));
            entity.links.ifPresent(ls -> map.put("links", Json.jArray(ls.stream().map(l -> {
                return Json.jObject(entry("rel", FromIterableString.apply(l.rel)),
                        entry("href", Json.jString(l.href.toString())));
            }).collect(Collectors.toList()))));
            entity.actions.ifPresent(as -> map.put("actions", Json.jArray(as.stream().map(a -> {
                Map<String, Json.JValue> action = new LinkedHashMap<>();
                action.put("name", Json.jString(a.name));
                a.classes.ifPresent(cs -> action.put("class", FromIterableString.apply(cs)));
                a.method.ifPresent(m -> action.put("method", Json.jString(m.name())));
                action.put("href", Json.jString(a.href.toString()));
                a.title.ifPresent(t -> action.put("title", Json.jString(t)));
                a.type.ifPresent(t -> action.put("type", Json.jString(t.format())));
                a.fields.ifPresent(fs ->
                        action.put("fields", Json.jArray(fs.stream().map(f -> {
                            Map<String, Json.JValue> field = new LinkedHashMap<>();
                            field.put("name", Json.jString(f.name));
                            f.classes.ifPresent(cs -> field.put("class", FromIterableString.apply(cs)));
                            field.put("type", Json.jString(f.type.value));
                            f.value.ifPresent(v -> field.put("value", v));
                            f.title.ifPresent(t -> field.put("title", Json.jString(t)));
                            return Json.jObject(field);
                        }).collect(Collectors.toList()))));
                return Json.jObject(action);
            }).collect(Collectors.toList()))));
            entity.title.ifPresent(t -> map.put("title", Json.jString(t)));
            return Json.jObject(map);
        }

        private Map.Entry<String, Json.JValue> entry(String name, Json.JValue value) {
            return new AbstractMap.SimpleImmutableEntry<>(name, value);
        }

        public Json.JValue serialize(Entity entity) {
            return sirenBuilder(entity);
        }

        public Json.JValue serialize(EmbeddedRepresentation embeddedRepresentation) {
            Json.JObject object = sirenBuilder(embeddedRepresentation.entity);
            return object.put("rel", FromIterableString.apply(embeddedRepresentation.rel));
        }

        public Json.JValue serialize(EmbeddedLink embeddedLink) {
            Map<String, Json.JValue> object = new LinkedHashMap<>();
            embeddedLink.classes.ifPresent(cs -> object.put("class", FromIterableString.apply(cs)));
            object.put("rel", FromIterableString.apply(embeddedLink.rel));
            object.put("href", Json.jString(embeddedLink.href.toString()));
            embeddedLink.title.ifPresent(t -> object.put("title", Json.jString(t)));
            embeddedLink.type.ifPresent(t -> object.put("type", Json.jString(t.format())));
            return Json.jObject(object);
        }
    }
}
