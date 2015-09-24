package net.hamnaberg.siren;

import java.net.URI;
import java.util.Optional;

public final class EmbeddedLink implements Entity {

    public final Classes classes;
    public final Relations rels;
    public final Optional<URI> href;
    public final Optional<MIMEType> type;
    public final Optional<String> title;

    public EmbeddedLink(Classes classes, Relations rels, Optional<URI> href, Optional<MIMEType> type, Optional<String> title) {
        this.classes = classes;
        this.rels = rels;
        this.href = href;
        this.type = type;
        this.title = title;
    }

    public <T> T toJson(JsonSerializer<T> serializer) {
        return serializer.serialize(this);
    }
}
