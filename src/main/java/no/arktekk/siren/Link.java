package no.arktekk.siren;

import java.net.URI;
import java.util.Optional;

public final class Link {

    public final Classes classes;
    public final Relations rel;
    public final URI href;
    public final Optional<MIMEType> type;
    public final Optional<String> title;

    public Link(Classes classes, Relations rel, URI href, Optional<MIMEType> type, Optional<String> title) {
        this.classes = classes;
        this.rel = rel;
        this.href = href;
        this.type = type;
        this.title = title;
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
