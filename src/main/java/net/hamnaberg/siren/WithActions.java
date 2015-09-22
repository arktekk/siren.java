package net.hamnaberg.siren;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface WithActions {
    default Optional<Action> getActionByName(String name) {
        return getActions().stream().filter(a -> a.name.equalsIgnoreCase(name)).findFirst();
    }

    default List<Action> getActionsByName(String name) {
        return getActions().stream().filter(a -> a.name.equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    List<Action> getActions();
}
