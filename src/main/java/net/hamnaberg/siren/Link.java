package net.hamnaberg.siren;

import java.net.URI;
import java.util.Optional;

public final class Link {
    public final URI href;
    public final Optional<Relations> relations;

    public Link(URI href, Optional<Relations> relations) {
        this.href = href;
        this.relations = relations;
    }
}
