package net.hamnaberg.siren.util;

import java.util.Optional;
import java.util.stream.Stream;

public interface StreamableIterable<T> extends Iterable<T> {

    default Stream<T> stream() {
        return StreamUtils.stream(this);
    }

    default Optional<T> headOption() {
        return stream().findFirst();
    }
}
