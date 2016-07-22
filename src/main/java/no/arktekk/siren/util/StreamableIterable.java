package no.arktekk.siren.util;

import javaslang.control.Option;

import java.util.stream.Stream;

public interface StreamableIterable<T> extends Iterable<T> {

    default Stream<T> stream() {
        return StreamUtils.stream(this);
    }

    default Stream<T> parallellStream() {
        return StreamUtils.parallellStream(this);
    }

    default Option<T> headOption() {
        return Option.ofOptional(stream().findFirst());
    }

    default boolean isEmpty() {
        return !headOption().isDefined();
    }
}
