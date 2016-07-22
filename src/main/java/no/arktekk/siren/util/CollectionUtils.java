package no.arktekk.siren.util;

import java.util.function.BiFunction;

public class CollectionUtils {

    public static <A, C> C foldLeft(Iterable<A> iterable, C empty, BiFunction<C, A, C> accFunction) {
        C agg = empty;
        for (A a : iterable) {
            agg = accFunction.apply(agg, a);
        }
        return agg;
    }
}
