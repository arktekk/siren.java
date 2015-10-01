package no.arktekk.siren;

import java.net.URI;
import java.util.Optional;

import static java.util.Optional.empty;

public final class EmbeddedLink implements SubEntity {

    public final Optional<Classes> classes;
    public final Relations rel;
    public final URI href;
    public final Optional<MIMEType> type;
    public final Optional<String> title;

    public EmbeddedLink(Optional<Classes> classes, Relations rel, URI href, Optional<MIMEType> type, Optional<String> title) {
        this.classes = classes;
        this.rel = rel;
        this.href = href;
        this.type = type;
        this.title = title;
    }

    public static EmbeddedLink of(Relations rel, URI href) {
        return new EmbeddedLink(empty(), rel, href, empty(), empty());
    }



    public EmbeddedLink classes(Classes classes) {
        return new EmbeddedLink(Optional.of(classes), rel, href, type, title);
    }

    public EmbeddedLink type(MIMEType type) {
        return new EmbeddedLink(classes, rel, href, Optional.of(type), title);
    }

    public EmbeddedLink title(String title) {
        return new EmbeddedLink(classes, rel, href, type, Optional.of(title));
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
