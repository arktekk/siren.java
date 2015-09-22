package net.hamnaberg.siren.field;

import net.hamnaberg.siren.Field;
import net.hamnaberg.siren.MIMEType;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface FieldSerializer {
    Optional<String> serialize(MIMEType mimeType, List<Field> fields);
}
