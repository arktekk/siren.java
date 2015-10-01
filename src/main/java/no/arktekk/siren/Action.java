package no.arktekk.siren;

import no.arktekk.siren.field.FieldSerializer;
import no.arktekk.siren.field.WWWUrlEncodedFieldSerializer;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;

public final class Action {
    public final String name;
    public final Optional<Classes> classes;
    public final URI href;
    public final Optional<String> title;
    public final Optional<Method> method;
    public final Optional<MIMEType> type;
    public final Optional<Fields> fields;

    public Action(String name, Optional<Classes> classes, URI href, Optional<String> title, Optional<Method> method, Optional<MIMEType> type, Optional<Fields> fields) {
        this.name = Objects.requireNonNull(name, "name may not be null");
        this.classes = classes;
        this.href = Objects.requireNonNull(href, "href may not be null");
        this.title = title;
        this.method = method;
        this.type = type;
        this.fields = fields;
    }

    public static Action of(String name, URI href) {
        return new Action(name, empty(), href, empty(), empty(), empty(), empty());
    }

    public Action classes(Classes classes) {
        return new Action(name, Optional.of(classes), href, title, method, type, fields);
    }

    public Action title(String title) {
        return new Action(name, classes, href, Optional.of(title), method, type, fields);
    }

    public Action method(Method method) {
        return new Action(name, classes, href, title, Optional.of(method), type, fields);
    }

    public Action type(MIMEType type) {
        return new Action(name, classes, href, title, method, Optional.of(type), fields);
    }

    public Action fields(Fields fields) { // TODO: Ha denne lik Fields.of? Da slipper man å eksplisitt jobbe med Fields. Kanskje forvirrende
        return new Action(name, classes, href, title, method, type, Optional.of(fields));
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
}
