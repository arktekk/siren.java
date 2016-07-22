package no.arktekk.siren.field;

import javaslang.control.Option;
import net.hamnaberg.json.Json;
import no.arktekk.siren.Field;
import no.arktekk.siren.Field.Type;
import no.arktekk.siren.Fields;
import no.arktekk.siren.MIMEType;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONFieldSerializerTest {
    JSONFieldSerializer serializer = JSONFieldSerializer.INSTANCE;

    @Test
    public void testSerialize() {
        Option<Fields> fields = Option.of(Fields.of(
                Field.of("field-1"),
                Field.of("href", Type.URL).with(Json.jString("http://www.example.com/meh?hello=hei&hei=jsdf"))
        ));

        assertTrue("JSON matched null", serializer.serialize(null, fields).isDefined());
        assertTrue("JSON didnt match All", serializer.serialize(MIMEType.All, fields).isDefined());
        assertTrue("JSON didnt match  json", serializer.serialize(MIMEType.JSON, fields).isDefined());
        assertFalse("JSON matched html", serializer.serialize(MIMEType.text("html"), fields).isDefined());


        Option<String> us = serializer.serialize(MIMEType.JSON, fields);
        String expected = "{\"field-1\":null,\"href\":\"http://www.example.com/meh?hello=hei&hei=jsdf\"}";
        assertEquals(expected, us.get());
    }
}
