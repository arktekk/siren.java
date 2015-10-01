package no.arktekk.siren;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.empty;

public abstract class SubEntity implements JsonSerializable {

    private SubEntity() {
    }

    public <X> X fold(Function<EmbeddedRepresentation, X> representation, Function<EmbeddedLink, X> link) {
        if (this instanceof EmbeddedRepresentation)
            return representation.apply(((EmbeddedRepresentation) this));
        else if (this instanceof EmbeddedLink)
            return link.apply((EmbeddedLink) this);
        else
            throw new RuntimeException("Unsupported SubEntity type");
    }

    public <T> T toJson(JsonSerializer<T> serializer) {
        return fold(serializer::serialize, serializer::serialize);
    }

    public static final class EmbeddedRepresentation extends SubEntity {

        public final Rel rel;
        public final Entity entity;

        public EmbeddedRepresentation(Rel rel, Entity entity) {
            this.rel = rel;
            this.entity = entity;
        }

        public static EmbeddedRepresentation of(Rel rel, Entity entity) {
            return new EmbeddedRepresentation(rel, entity);
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

    public static final class EmbeddedLink extends SubEntity {

        public final Rel rel;
        public final URI href;
        public final Optional<Classes> classes;
        public final Optional<MIMEType> type;
        public final Optional<String> title;

        public EmbeddedLink(Rel rel, URI href, Optional<Classes> classes, Optional<MIMEType> type, Optional<String> title) {
            this.rel = rel;
            this.href = href;
            this.classes = classes;
            this.type = type;
            this.title = title;
        }

        public static EmbeddedLink of(Rel rel, URI href) {
            return new EmbeddedLink(rel, href, empty(), empty(), empty());
        }

        public EmbeddedLink with(Classes classes) {
            return new EmbeddedLink(rel, href, Optional.of(classes), type, title);
        }

        public EmbeddedLink with(MIMEType type) {
            return new EmbeddedLink(rel, href, classes, Optional.of(type), title);
        }

        public EmbeddedLink with(String title) {
            return new EmbeddedLink(rel, href, classes, type, Optional.of(title));
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
}
