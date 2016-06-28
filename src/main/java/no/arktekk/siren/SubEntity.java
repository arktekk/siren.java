package no.arktekk.siren;

import javaslang.control.Option;

import java.net.URI;
import java.util.function.Function;

import static javaslang.control.Option.none;


public abstract class SubEntity implements JsonSerializable {

    private SubEntity() {
    }

    public abstract <X> X fold(Function<EmbeddedRepresentation, X> representation, Function<EmbeddedLink, X> link);

    public <T> T toJson(JsonSerializer<T> serializer) {
        return fold(serializer::serialize, serializer::serialize);
    }

    public static SubEntity representation(Rel rel, Entity entity) {
        return new EmbeddedRepresentation(rel, entity);
    }

    public static SubEntity link(Rel rel, URI href) {
        return link(rel, href, none(), none(), none());
    }

    public static SubEntity link(Link link) {
        return new EmbeddedLink(link.rel, link.href, link.classes, link.type, link.title);
    }

    public static SubEntity link(Rel rel, URI href, Option<Classes> classes, Option<MIMEType> type, Option<String> title) {
        return new EmbeddedLink(rel, href, classes, type, title);
    }

    public abstract Rel getRel();

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

        @Override
        public <X> X fold(Function<EmbeddedRepresentation, X> representation, Function<EmbeddedLink, X> link) {
            return representation.apply(this);
        }

        public Rel getRel() {
            return rel;
        }

        public Entity getEntity() {
            return entity;
        }
    }

    public static final class EmbeddedLink extends SubEntity {

        public final Rel rel;
        public final URI href;
        public final Option<Classes> classes;
        public final Option<MIMEType> type;
        public final Option<String> title;

        public EmbeddedLink(Rel rel, URI href, Option<Classes> classes, Option<MIMEType> type, Option<String> title) {
            this.rel = rel;
            this.href = href;
            this.classes = classes;
            this.type = type;
            this.title = title;
        }

        public static EmbeddedLink of(Rel rel, URI href) {
            return new EmbeddedLink(rel, href, none(), none(), none());
        }

        public EmbeddedLink with(Classes classes) {
            return new EmbeddedLink(rel, href, Option.of(classes), type, title);
        }

        public EmbeddedLink with(MIMEType type) {
            return new EmbeddedLink(rel, href, classes, Option.of(type), title);
        }

        public EmbeddedLink with(String title) {
            return new EmbeddedLink(rel, href, classes, type, Option.of(title));
        }

        public Link toLink() {
            return new Link(rel, href, classes, type, title);
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

        @Override
        public <X> X fold(Function<EmbeddedRepresentation, X> representation, Function<EmbeddedLink, X> link) {
            return link.apply(this);
        }

        public Rel getRel() {
            return rel;
        }

        public URI getHref() {
            return href;
        }

        public Option<Classes> getClasses() {
            return classes;
        }

        public Option<MIMEType> getType() {
            return type;
        }

        public Option<String> getTitle() {
            return title;
        }
    }
}
