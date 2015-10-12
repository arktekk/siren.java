package no.arktekk.siren;

import net.hamnaberg.json.Json;

import java.util.Optional;

import static java.util.Optional.empty;

public final class Field {
    public final String name;
    public final Type type;
    public final Optional<Classes> classes;
    public final Optional<Json.JValue> value;
    public final Optional<String> title;

    public Field(String name, Type type, Optional<Classes> classes, Optional<Json.JValue> value, Optional<String> title) {
        this.name = name;
        this.type = type;
        this.classes = classes;
        this.value = value;
        this.title = title;
    }

    public static Field of(String name) {
        return new Field(name, Type.TEXT, empty(), empty(), empty());
    }

    public static Field of(String name, Type type) {
        return new Field(name, type, empty(), empty(), empty());
    }

    public Field with(Classes classes) {
        return new Field(name, type, Optional.of(classes), value, title);
    }

    public Field with(Json.JValue value) { // TODO: Få vekk JsonValue
        return new Field(name, type, classes, Optional.of(value), title);
    }

    public Field with(String title) {
        return new Field(name, type, classes, value, Optional.of(title));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (!name.equals(field.name)) return false;
        if (!classes.equals(field.classes)) return false;
        if (type != field.type) return false;
        if (!value.equals(field.value)) return false;
        return title.equals(field.title);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + classes.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    public enum Type {
        HIDDEN("hidden"),
        TEXT("text"),
        SEARCH("search"),
        TEL("tel"),
        URL("url"),
        EMAIL("email"),
        PASSWORD("password"),
        DATETIME("datetime"),
        DATE("date"),
        MONTH("month"),
        WEEK("week"),
        TIME("time"),
        DATETIME_LOCAL("datetime-local"),
        NUMBER("number"),
        RANGE("range"),
        COLOR("color"),
        CHECKBOX("checkbox"),
        RADIO("radio"),
        FILE("file");

        public String value;

        Type(String s) {
            this.value = s;
        }

        public static Type fromString(String s) {
            for (Type v : values()) {
                if (v.value.equals(s)) {
                    return v;
                }
            }
            return Type.TEXT;
        }
    }
}
