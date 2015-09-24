package net.hamnaberg.siren;

import java.net.URI;
import java.util.Optional;

public final class EmbeddedLink implements SubEntity {

    public final Classes classes;
    public final Relations rel;
    public final URI href;
    public final Optional<MIMEType> type;
    public final Optional<String> title;

    public EmbeddedLink(Classes classes, Relations rel, URI href, Optional<MIMEType> type, Optional<String> title) {
        this.classes = classes;
        this.rel = rel;
        this.href = href;
        this.type = type;
        this.title = title;
    }

    public EmbeddedLink(Link link) {
        this(link.classes, link.rel, link.href, link.type, link.title);
    }

    public Relations getRel() {
        return rel;
    }

    public <T> T toJson(JsonSerializer<T> serializer) {
        return serializer.serialize(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmbeddedLink that = (EmbeddedLink) o;

        if (!classes.equals(that.classes)) return false;
        if (!rel.equals(that.rel)) return false;
        if (!href.equals(that.href)) return false;
        if (!type.equals(that.type)) return false;
        return title.equals(that.title);

    }

    @Override
    public int hashCode() {
        int result = classes.hashCode();
        result = 31 * result + rel.hashCode();
        result = 31 * result + href.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }
}
