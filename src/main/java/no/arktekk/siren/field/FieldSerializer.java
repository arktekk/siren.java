package no.arktekk.siren.field;

import no.arktekk.siren.Field;
import no.arktekk.siren.MIMEType;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface FieldSerializer {
    Optional<String> serialize(MIMEType mimeType, List<Field> fields);
}
