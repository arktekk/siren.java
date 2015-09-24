package no.arktekk.siren.util;

import java.util.Optional;
import java.util.stream.Stream;

public interface StreamableIterable<T> extends Iterable<T> {

    default Stream<T> stream() {
        return StreamUtils.stream(this);
    }

    default Stream<T> parallellStream() {
        return StreamUtils.parallellStream(this);
    }

    default Optional<T> headOption() {
        return stream().findFirst();
    }

    default boolean isEmpty() {
        return !headOption().isPresent();
    }
}
