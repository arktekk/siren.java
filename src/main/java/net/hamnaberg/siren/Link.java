package net.hamnaberg.siren;

import java.net.URI;
import java.util.Optional;

public final class Link {
    public final URI href;
    public final Relations relations;
    public final Optional<String> title;
    public final Optional<MIMEType> type;

    public Link(URI href, Relations relations, Optional<String> title, Optional<MIMEType> type) {
        this.href = href;
        this.relations = relations;
        this.title = title;
        this.type = type;
    }

    public URI getHref() {
        return href;
    }

    public Relations getRelations() {
        return relations;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<MIMEType> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!href.equals(link.href)) return false;
        if (!relations.equals(link.relations)) return false;
        if (!title.equals(link.title)) return false;
        return type.equals(link.type);

    }

    @Override
    public int hashCode() {
        int result = href.hashCode();
        result = 31 * result + relations.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
