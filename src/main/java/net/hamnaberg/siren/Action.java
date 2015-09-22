package net.hamnaberg.siren;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public final class Action {
    public final String name;
    public final URI href;
    public final Optional<String> title;
    public final Optional<Method> method;
    public final Optional<MIMEType> type;
    public final List<Field> fields;

    public Action(String name, URI href, Optional<String> title, Optional<Method> method, Optional<MIMEType> type, List<Field> fields) {
        this.name = name;
        this.title = title;
        this.method = method;
        this.href = href;
        this.type = type;
        this.fields = fields;
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
