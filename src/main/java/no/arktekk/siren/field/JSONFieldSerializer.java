package no.arktekk.siren.field;

import io.vavr.control.Option;
import net.hamnaberg.json.Json;
import no.arktekk.siren.Fields;
import no.arktekk.siren.MIMEType;

public enum JSONFieldSerializer implements FieldSerializer {
    INSTANCE;

    @Override
    public Option<String> serialize(MIMEType mimeType, Option<Fields> fields) {
        return MIMEType.JSON.includedBy(mimeType) ? fields.map(this::jsonFields) : Option.none();
    }

    private String jsonFields(Fields fields) {
        Json.JObject object = fields.toList().foldLeft(Json.jEmptyObject(), (json, field) -> json.put(field.name, field.value.getOrElse(Json.jNull())));
        return object.nospaces();
    }
}
