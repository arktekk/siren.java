package no.arktekk.siren;

import javaslang.control.Option;
import static javaslang.control.Option.none;

import no.arktekk.siren.field.FieldSerializer;
import no.arktekk.siren.field.WWWUrlEncodedFieldSerializer;

import java.net.URI;
import java.util.Objects;

public final class Action {
    public final String name;
    public final URI href;
    public final Option<Classes> classes;
    public final Option<String> title;
    public final Option<Method> method;
    public final Option<MIMEType> type;
    public final Option<Fields> fields;

    public Action(String name, URI href, Option<Classes> classes, Option<String> title, Option<Method> method, Option<MIMEType> type, Option<Fields> fields) {
        this.name = Objects.requireNonNull(name, "name may not be null");
        this.href = Objects.requireNonNull(href, "href may not be null");
        this.classes = classes;
        this.title = title;
        this.method = method;
        this.type = type;
        this.fields = fields;
    }

    public static Action of(String name, URI href) {
        return new Action(name, href, none(), none(), none(), none(), none());
    }

    public Action with(Classes classes) {
        return new Action(name, href, Option.of(classes), title, method, type, fields);
    }

    public Action with(String title) {
        return new Action(name, href, classes, Option.of(title), method, type, fields);
    }

    public Action with(Method method) {
        return new Action(name, href, classes, title, Option.of(method), type, fields);
    }

    public Action with(MIMEType type) {
        return new Action(name, href, classes, title, method, Option.of(type), fields);
    }

    public Action with(Fields fields) {
        return new Action(name, href, classes, title, method, type, Option.of(fields));
    }

    public Option<String> format(FieldSerializer serializer) {
        return serializer.serialize(type.getOrElse(MIMEType.URLEncoded), fields);
    }

    public Option<String> format() {
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

    public Option<Classes> getClasses() {
        return classes;
    }

    public Option<String> getTitle() {
        return title;
    }

    public Option<Method> getMethod() {
        return method;
    }

    public Option<MIMEType> getType() {
        return type;
    }

    public Option<Fields> getFields() {
        return fields;
    }
}
