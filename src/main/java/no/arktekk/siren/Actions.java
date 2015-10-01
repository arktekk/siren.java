package no.arktekk.siren;

import no.arktekk.siren.util.StreamUtils;
import no.arktekk.siren.util.StreamableIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class Actions implements StreamableIterable<Action> {

    private final List<Action> actions;

    public Actions(Iterable<Action> actions) {
        this.actions = unmodifiableList(StreamUtils.stream(actions).collect(Collectors.toList()));
    }

    public static Actions of(Action action, Action... actions) {
        return new Actions(new ArrayList<Action>() {{
            add(action);
            addAll(asList(actions));
        }});
    }

    public Iterator<Action> iterator() {
        return actions.iterator();
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
