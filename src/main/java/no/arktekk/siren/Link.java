package no.arktekk.siren;

import java.net.URI;
import java.util.Optional;

import static java.util.Optional.empty;

public final class Link {

    public final Rel rel;
    public final URI href;
    public final Optional<Classes> classes;
    public final Optional<MIMEType> type;
    public final Optional<String> title;

    public Link(Rel rel, URI href, Optional<Classes> classes, Optional<MIMEType> type, Optional<String> title) {
        this.rel = rel;
        this.href = href;
        this.classes = classes;
        this.type = type;
        this.title = title;
    }

    public static Link of(Rel rel, URI href) {
        return new Link(rel, href, empty(), empty(), empty());
    }

    public Link with(Classes classes) {
        return new Link(rel, href, Optional.of(classes), type, title);
    }

    public Link with(MIMEType type) {
        return new Link(rel, href, classes, Optional.of(type), title);
    }

    public Link with(String title) {
        return new Link(rel, href, classes, type, Optional.of(title));
    }

    public SubEntity.EmbeddedLink toEmbedded() {
        return new SubEntity.EmbeddedLink(rel, href, classes, type, title);
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
