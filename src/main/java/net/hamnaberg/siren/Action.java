package net.hamnaberg.siren;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public final class Action {
    public final String name;
    public final Optional<String> title;
    public final Optional<Method> method;
    public final Optional<URI> href;
    public final Optional<MIMEType> type;
    public final List<Field> fields;

    public Action(String name, Optional<String> title, Optional<Method> method, Optional<URI> href, Optional<MIMEType> type, List<Field> fields) {
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

    public Optional<Field> getFieldByName(String name) {
        return fields.stream().filter(f -> f.name.equalsIgnoreCase(name)).findFirst();
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<Method> getMethod() {
        return method;
    }

    public Optional<URI> getHref() {
        return href;
    }

    public Optional<MIMEType> getType() {
        return type;
    }

    public List<Field> getFields() {
        return fields;
    }
}
