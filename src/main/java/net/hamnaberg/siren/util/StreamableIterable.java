package net.hamnaberg.siren.util;

import java.util.stream.Stream;

public interface StreamableIterable<T> extends Iterable<T> {

    default Stream<T> stream() {
        return StreamUtils.stream(this);
    }
}
