package net.hamnaberg.siren.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CollectionUtils {
    private CollectionUtils() {
    }

    public static <A> List<A> asList(A first, A... rest) {
        ArrayList<A> list = new ArrayList<>(rest.length + 1);
        list.add(first);
        list.addAll(Arrays.asList(rest));
        return list;
    }
}
