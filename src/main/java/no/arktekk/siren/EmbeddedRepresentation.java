package no.arktekk.siren;

public final class EmbeddedRepresentation implements SubEntity {

    public final Relations rel;
    public final Entity entity;

    public EmbeddedRepresentation(Relations rel, Entity entity) {
        this.rel = rel;
        this.entity = entity;
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

        EmbeddedRepresentation that = (EmbeddedRepresentation) o;

        if (!rel.equals(that.rel)) return false;
        return entity.equals(that.entity);

    }

    @Override
    public int hashCode() {
        int result = rel.hashCode();
        result = 31 * result + entity.hashCode();
        return result;
    }
}
