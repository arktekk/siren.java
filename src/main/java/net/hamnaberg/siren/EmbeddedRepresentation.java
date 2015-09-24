package net.hamnaberg.siren;

public final class EmbeddedRepresentation implements Entity {

    public final Relations rels;
    public final Siren siren;

    public EmbeddedRepresentation(Relations rels, Siren siren) {
        this.rels = rels;
        this.siren = siren;
    }

    public <T> T toJson(JsonSerializer<T> serializer) {
        return serializer.serialize(this);
    }
}
