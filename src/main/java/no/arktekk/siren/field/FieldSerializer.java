package no.arktekk.siren.field;

import no.arktekk.siren.Fields;
import no.arktekk.siren.MIMEType;

import java.util.Optional;

@FunctionalInterface
public interface FieldSerializer {
    Optional<String> serialize(MIMEType mimeType, Optional<Fields> fields);
}
