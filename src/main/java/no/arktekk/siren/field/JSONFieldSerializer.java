package no.arktekk.siren.field;

import javaslang.control.Option;
import net.hamnaberg.json.Json;
import net.hamnaberg.json.io.JacksonStreamingSerializer;
import no.arktekk.siren.Fields;
import no.arktekk.siren.MIMEType;
import no.arktekk.siren.util.CollectionUtils;

public enum JSONFieldSerializer implements FieldSerializer {
    INSTANCE;

    @Override
    public Option<String> serialize(MIMEType mimeType, Option<Fields> fields) {
        return MIMEType.JSON.includedBy(mimeType) ? fields.map(this::jsonFields) : Option.none();
    }

    private String jsonFields(Fields fields) {
        Json.JObject object = CollectionUtils.foldLeft(fields, Json.jEmptyObject(), (json, field) -> json.put(field.name, field.value.getOrElse(Json.jNull())));
        return new JacksonStreamingSerializer().writeToString(object);
    }
}
