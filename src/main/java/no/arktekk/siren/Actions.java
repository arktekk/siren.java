package no.arktekk.siren;

import java.util.SortedMap;
import java.util.TreeMap;

import javaslang.collection.*;
import javaslang.control.Option;
import no.arktekk.siren.util.StreamableIterable;

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

    public static Actions empty() {
        return new Actions(new TreeMap<>());
    }

    public static Actions of(Action action, Action... actions) {
        return new Actions(List.of(actions).prepend(action));
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
        return List.ofAll(actions.values()).iterator();
    }

    public Option<Action> getActionByName(String name) {
        return Option.of(actions.get(name));
    }

    public Option<Action> getActionByClasses(Classes classes) {
        return Option.ofOptional(stream().filter(l -> l.classes.filter(classes::equals).isDefined()).findFirst());
    }

    public Option<Action> getActionByClassesIncludes(Classes classes) {
        return Option.ofOptional(stream().filter(l -> l.classes.filter(classes::includes).isDefined()).findFirst());
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
