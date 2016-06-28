package no.arktekk.siren;

import javaslang.control.Option;
import net.hamnaberg.json.Json;

import static javaslang.control.Option.none;


public final class Field {
    public final String name;
    public final Type type;
    public final Option<Classes> classes;
    public final Option<Json.JValue> value;
    public final Option<String> title;

    public Field(String name, Type type, Option<Classes> classes, Option<Json.JValue> value, Option<String> title) {
        this.name = name;
        this.type = type;
        this.classes = classes;
        this.value = value;
        this.title = title;
    }

    public static Field of(String name) {
        return new Field(name, Type.TEXT, none(), none(), none());
    }

    public static Field of(String name, Type type) {
        return new Field(name, type, none(), none(), none());
    }

    public Field with(Classes classes) {
        return new Field(name, type, Option.of(classes), value, title);
    }

    public Field with(Json.JValue value) { // TODO: Få vekk JsonValue
        return new Field(name, type, classes, Option.of(value), title);
    }

    public Field with(String title) {
        return new Field(name, type, classes, value, Option.of(title));
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


    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Option<Classes> getClasses() {
        return classes;
    }

    public Option<Json.JValue> getValue() {
        return value;
    }

    public Option<String> getTitle() {
        return title;
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
