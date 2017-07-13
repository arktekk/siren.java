package no.arktekk.siren.field;

import io.vavr.control.Option;
import no.arktekk.siren.Fields;
import no.arktekk.siren.MIMEType;


@FunctionalInterface
public interface FieldSerializer {
    Option<String> serialize(MIMEType mimeType, Option<Fields> fields);
}
