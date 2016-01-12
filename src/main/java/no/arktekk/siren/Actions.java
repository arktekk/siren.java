package no.arktekk.siren;

import java.util.*;
import java.util.stream.Collectors;

import no.arktekk.siren.util.CollectionUtils;
import no.arktekk.siren.util.StreamableIterable;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSortedMap;

/**
 * Backed by Map to enforce unique action name invariant.
 */
public final class Actions implements StreamableIterable<Action> {

    private final SortedMap<String, Action> actions;

    private Actions(SortedMap<String, Action> actions) {
        this.actions = unmodifiableSortedMap(actions);
    }

    public Actions(Iterable<Action> actions) {
        this(new TreeMap<String, Action>(String::compareTo) {{
            for (Action action : actions) {
                put(action.name, action);
            }
        }});
    }

    public static Actions of(Action action, Action... actions) {
        return new Actions(CollectionUtils.asList(action, actions));
    }

    public Actions add(Action action) {
        SortedMap<String, Action> actions = new TreeMap<>(this.actions);
        actions.put(action.name, action);
        return new Actions(actions);
    }

    public Actions remove(String name) {
        SortedMap<String, Action> actions = new TreeMap<>(this.actions);
        actions.remove(name);
        return new Actions(actions);
    }

    public Iterator<Action> iterator() {
        return actions.values().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actions actions1 = (Actions) o;

        return actions.equals(actions1.actions);

    }

    @Override
    public int hashCode() {
        return actions.hashCode();
    }
}
