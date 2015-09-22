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
}
