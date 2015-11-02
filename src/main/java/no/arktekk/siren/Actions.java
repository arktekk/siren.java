package no.arktekk.siren;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import no.arktekk.siren.util.StreamableIterable;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;

/**
 * Backed by Map to enforce unique action name invariant.
 */
public class Actions implements StreamableIterable<Action> {

    private final Map<String, Action> actions;

    public Actions(Iterable<Action> actions) {
        this.actions = unmodifiableMap(new TreeMap<String, Action>(String::compareTo) {{
            for (Action action : actions) {
                put(action.name, action);
            }
        }});
    }

    public static Actions of(Action action, Action... actions) {
        return new Actions(new ArrayList<Action>() {{
            add(action);
            addAll(asList(actions));
        }});
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
