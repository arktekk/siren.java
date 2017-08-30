package no.arktekk.siren;

import io.vavr.control.Option;
import net.hamnaberg.json.Json;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.vavr.control.Option.none;


public abstract class Field {
    public final String name;
    public final Option<Classes> classes;
    public final Option<Json.JValue> value;
    public final Option<String> title;

    public abstract <X> X fold(Function<SimpleField, X> simpleField, Function<NestedField, X> nestedField);
    public abstract void consume(Consumer<SimpleField> simpleField, Consumer<NestedField> nestedField);

    private Field(String name, Option<Classes> classes, Option<Json.JValue> value, Option<String> title) {
        this.name = name;
        this.classes = classes;
        this.value = value;
        this.title = title;
    }

    public static final class SimpleField extends Field {
        public final Type type;

        public SimpleField(String name, Type type, Option<Classes> classes, Option<Json.JValue> value, Option<String> title) {
            super(name, classes, value, title);
            this.type = type;
        }

        public SimpleField with(Classes classes) {
            return new SimpleField(name, type, Option.of(classes), value, title);
        }

        public SimpleField with(Json.JValue value) { // TODO: Få vekk JsonValue
            return new SimpleField(name, type, classes, Option.of(value), title);
        }

        public SimpleField with(String title) {
            return new SimpleField(name, type, classes, value, Option.of(title));
        }

        @Override
        public <X> X fold(Function<SimpleField, X> simpleField, Function<NestedField, X> nestedField) {
            return simpleField.apply(this);
        }

        @Override
        public void consume(Consumer<SimpleField> simpleField, Consumer<NestedField> nestedField) {
            simpleField.accept(this);
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            SimpleField aSimpleField = (SimpleField) o;

            return type == aSimpleField.type;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }
    }

    public static final class NestedField extends Field {

        public final Fields fields;

        public NestedField(String name, Fields fields, Option<Classes> classes, Option<Json.JValue> value, Option<String> title) {
            super(name, classes, value, title);
            this.fields = fields;
        }

        @Override
        public <X> X fold(Function<SimpleField, X> simpleField, Function<NestedField, X> nestedField) {
            return nestedField.apply(this);
        }

        @Override
        public void consume(Consumer<SimpleField> simpleField, Consumer<NestedField> nestedField) {
            nestedField.accept(this);
        }

        public Fields getFields() {
            return fields;
        }

        public NestedField with(Classes classes) {
            return new NestedField(name, fields, Option.of(classes), value, title);
        }

        public NestedField with(Json.JValue value) { // TODO: Få vekk JsonValue
            return new NestedField(name, fields, classes, Option.of(value), title);
        }

        public NestedField with(String title) {
            return new NestedField(name, fields, classes, value, Option.of(title));
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            NestedField nestedField1 = (NestedField) o;

            return fields.equals(nestedField1.fields);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + fields.hashCode();
            return result;
        }
    }

    public static SimpleField of(String name) {
        return new SimpleField(name, Type.TEXT, none(), none(), none());
    }

    public static SimpleField of(String name, Type type) {
        return new SimpleField(name, type, none(), none(), none());
    }

    public static NestedField nested(String name, Fields fields) {
        return new NestedField(name, fields, none(), none(), none());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Field)) return false;

        Field field = (Field) o;

        if (!name.equals(field.name)) return false;
        if (!classes.equals(field.classes)) return false;
        if (!value.equals(field.value)) return false;
        return title.equals(field.title);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + classes.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }


    public String getName() {
        return name;
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
