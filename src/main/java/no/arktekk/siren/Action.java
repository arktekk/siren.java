package no.arktekk.siren;

import no.arktekk.siren.field.FieldSerializer;
import no.arktekk.siren.field.WWWUrlEncodedFieldSerializer;
import no.arktekk.siren.util.CollectionUtils;
import no.arktekk.siren.util.StreamUtils;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Action {
    public final String name;
    public final Classes classes;
    public final URI href;
    public final Optional<String> title;
    public final Optional<Method> method;
    public final Optional<MIMEType> type;
    public final List<Field> fields;

    public static Action of(String name, URI href) {
        return new Action(name, Classes.empty(), href, Optional.empty(), Optional.empty(), Optional.empty(), Collections.emptyList());
    }

    public static Action of(String name, URI href, List<Field> fields) {
        return new Action(name, Classes.empty(), href, Optional.empty(), Optional.empty(), Optional.empty(), fields);
    }

    public Action(String name, Classes classes, URI href, Optional<String> title, Optional<Method> method, Optional<MIMEType> type, List<Field> fields) {
        this.name = Objects.requireNonNull(name, "name may not be null");
        this.classes = classes;
        this.href = Objects.requireNonNull(href, "href may not be null");
        this.title = title;
        this.method = method;
        this.type = type;
        this.fields = Collections.unmodifiableList(Objects.requireNonNull(fields, "Fields may not be null"));
    }

    public String getName() {
        return name;
    }

    public URI getHref() {
        return href;
    }

    public Optional<Field> getFieldByName(String name) {
        return fields.stream().filter(f -> f.name.equalsIgnoreCase(name)).findFirst();
    }

    public Action withFields(List<Field> fields) {
        return new Action(name, classes, href, title, method, type, fields);
    }

    public Action withFields(Field field, Field... fields) {
        return withFields(CollectionUtils.asList(field, fields));
    }

    public Action addFields(Field field, Field... fields) {
        return addFields(CollectionUtils.asList(field, fields));
    }

    public Action addFields(List<Field> fields) {
        List<Field> allFields = Stream.concat(this.fields.stream(), fields.stream()).collect(Collectors.toList());
        return new Action(name, classes, href, title, method, type, allFields);
    }

    public Action replace(Iterable<Field> replacement) {
        if (!StreamUtils.stream(replacement).findFirst().isPresent()) {
            return this;
        }
        Map<String, Field> map = StreamUtils.stream(replacement).
                collect(Collectors.toMap(Field::getName, Function.identity()));

        List<Field> fields = getFields().stream().
                map(f -> map.getOrDefault(f.getName(), f)).
                collect(Collectors.toList());

        return withFields(fields);
    }

    public Action noFields() {
        return new Action(name, classes, href, title, method, type, Collections.emptyList());
    }

    public Action withTitle(String title) {
        return new Action(name, classes, href, Optional.of(title), method, type, Collections.emptyList());
    }

    public Action noTitle() {
        return new Action(name, classes, href, Optional.empty(), method, type, Collections.emptyList());
    }

    public Optional<String> format(FieldSerializer serializer) {
        return serializer.serialize(type.orElse(MIMEType.URLEncoded), fields);
    }

    public Optional<String> format() {
        return format(WWWUrlEncodedFieldSerializer.INSTANCE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (!name.equals(action.name)) return false;
        if (!href.equals(action.href)) return false;
        if (!title.equals(action.title)) return false;
        if (!method.equals(action.method)) return false;
        if (!type.equals(action.type)) return false;
        return fields.equals(action.fields);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + href.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + fields.hashCode();
        return result;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<Method> getMethod() {
        return method;
    }

    public Optional<MIMEType> getType() {
        return type;
    }

    public List<Field> getFields() {
        return fields;
    }
}
