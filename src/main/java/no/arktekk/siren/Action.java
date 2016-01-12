package no.arktekk.siren;

import no.arktekk.siren.field.FieldSerializer;
import no.arktekk.siren.field.WWWUrlEncodedFieldSerializer;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;

public final class Action {
    public final String name;
    public final URI href;
    public final Optional<Classes> classes;
    public final Optional<String> title;
    public final Optional<Method> method;
    public final Optional<MIMEType> type;
    public final Optional<Fields> fields;

    public Action(String name, URI href, Optional<Classes> classes, Optional<String> title, Optional<Method> method, Optional<MIMEType> type, Optional<Fields> fields) {
        this.name = Objects.requireNonNull(name, "name may not be null");
        this.href = Objects.requireNonNull(href, "href may not be null");
        this.classes = classes;
        this.title = title;
        this.method = method;
        this.type = type;
        this.fields = fields;
    }

    public static Action of(String name, URI href) {
        return new Action(name, href, empty(), empty(), empty(), empty(), empty());
    }

    public Action with(Classes classes) {
        return new Action(name, href, Optional.of(classes), title, method, type, fields);
    }

    public Action with(String title) {
        return new Action(name, href, classes, Optional.of(title), method, type, fields);
    }

    public Action with(Method method) {
        return new Action(name, href, classes, title, Optional.of(method), type, fields);
    }

    public Action with(MIMEType type) {
        return new Action(name, href, classes, title, method, Optional.of(type), fields);
    }

    public Action with(Fields fields) {
        return new Action(name, href, classes, title, method, type, Optional.of(fields));
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
        if (!classes.equals(action.classes)) return false;
        if (!href.equals(action.href)) return false;
        if (!title.equals(action.title)) return false;
        if (!method.equals(action.method)) return false;
        if (!type.equals(action.type)) return false;
        return fields.equals(action.fields);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + classes.hashCode();
        result = 31 * result + href.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + fields.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public URI getHref() {
        return href;
    }

    public Optional<Classes> getClasses() {
        return classes;
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

    public Optional<Fields> getFields() {
        return fields;
    }
}
