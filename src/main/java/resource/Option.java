package main.java.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Option {
    ADD_DEVICE(1),
    EDIT_DEVICE(2),
    DELETE_DEVICE(3),
    CHECK_CONSUMPTION(4),
    CONSUMPTION_GUIDE(5),
    EDIT_PROFILE(6),
    EXIT(0);

    final int index;

    Option(int index) {
        this.index = index;
    }

    public static ArrayList<Integer> indexValues() {
        return Option.reverseLookup.values().stream().map(c -> c.index).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Option fromInt(final int id) {
        return reverseLookup.getOrDefault(id, null);
    }

    private int getIndex() {
        return this.index;
    }

    private static final Map<Integer, Option> reverseLookup =
            Arrays.stream(Option.values()).collect(Collectors.toMap(Option::getIndex, Function.identity()));
}