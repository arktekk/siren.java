package no.arktekk.siren.field;

import no.arktekk.siren.Field;
import no.arktekk.siren.Fields;
import no.arktekk.siren.MIMEType;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;
import java.util.stream.Collectors;

public final class WWWUrlEncodedFieldSerializer implements FieldSerializer {
    public static final WWWUrlEncodedFieldSerializer INSTANCE = new WWWUrlEncodedFieldSerializer();

    private WWWUrlEncodedFieldSerializer() {
    }

    public Optional<String> serialize(MIMEType mimeType, Optional<Fields> fields) {
        return fields.filter(f -> MIMEType.URLEncoded.includedBy(mimeType)).map(fs -> fs.stream().map(this::format).collect(Collectors.joining("&")));
    }

    private String format(Field f) {
        return String.format("%s=%s", encode(f.name), encode(f.value.map(this::toString).orElse("")));
    }

    private String toString(JsonValue jv) {
        switch (jv.getValueType()) {
            case ARRAY:
                throw new IllegalArgumentException("We do not support json arrays");
            case OBJECT:
                throw new IllegalArgumentException("We do not support json objects");
            case STRING:
                return ((JsonString)jv).getString();
            case NUMBER:
                return ((JsonNumber)jv).bigDecimalValue().toString();
            case TRUE:
                return "true";
            case FALSE:
                return "false";
            default:
                return "";
        }
    }

    private String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedCharsetException("UTF-8");
        }
    }
}
