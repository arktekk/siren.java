package no.arktekk.siren;

import java.net.URI;
import java.util.Optional;

import static java.util.Optional.empty;

public final class Link {

    public final Optional<Classes> classes;
    public final Relations rel;
    public final URI href;
    public final Optional<MIMEType> type;
    public final Optional<String> title;

    public Link(Optional<Classes> classes, Relations rel, URI href, Optional<MIMEType> type, Optional<String> title) {
        this.classes = classes;
        this.rel = rel;
        this.href = href;
        this.type = type;
        this.title = title;
    }

    public static Link of(Relations rel, URI href) {
        return new Link(empty(), rel, href, empty(), empty());
    }

    public Link classes(Classes classes) {
        return new Link(Optional.of(classes), rel, href, type, title);
    }

    public Link type(MIMEType type) {
        return new Link(classes, rel, href, Optional.of(type), title);
    }

    public Link title(String title) {
        return new Link(classes, rel, href, type, Optional.of(title));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!classes.equals(link.classes)) return false;
        if (!rel.equals(link.rel)) return false;
        if (!href.equals(link.href)) return false;
        if (!type.equals(link.type)) return false;
        return title.equals(link.title);

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
