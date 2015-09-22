package net.hamnaberg.siren;

import org.glassfish.json.JsonFactory;

import javax.json.JsonValue;
import java.util.Optional;

public final class Field {
    public final String name;
    public final Classes classes;
    public final Type type;
    public final Optional<JsonValue> value;
    public final Optional<String> title;

    public static Field of(String name) {
        return new Field(name, Classes.empty(), Type.TEXT, Optional.empty(), Optional.empty());
    }

    public static Field of(String name, String value) {
        return new Field(name, Classes.empty(), Type.TEXT, Optional.of(value).map(JsonFactory::jsonString), Optional.empty());
    }
    public static Field of(String name, JsonValue value) {
        return new Field(name, Classes.empty(), Type.TEXT, Optional.of(value), Optional.empty());
    }

    public static Field of(String name, Type type, JsonValue value) {
        return new Field(name, Classes.empty(), type, Optional.of(value), Optional.empty());
    }

    public static Field of(String name, Type type) {
        return new Field (name, Classes.empty(), type, Optional.empty(), Optional.empty());
    }

    public Field(String name, Classes classes, Type type, Optional<JsonValue> value, Optional<String> title) {
        this.name = name;
        this.classes = classes;
        this.type = type;
        this.value = value;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public Classes getClasses() {
        return classes;
    }

    public Type getType() {
        return type;
    }

    public Optional<JsonValue> getValue() {
        return value;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Field withClasses(Classes classes) {
        return new Field(name, classes, type, value, title);
    }

    public Field withValue(JsonValue value) {
        return new Field(name, classes, type, Optional.of(value), title);
    }

    public Field noValue() {
        return new Field(name, classes, type, Optional.empty(), title);
    }

    public Field withTitle(String title) {
        return new Field(name, classes, type, value, Optional.of(title));
    }

    public Field noTitle() {
        return new Field(name, classes, type, value, Optional.empty());
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

    enum Type {
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
