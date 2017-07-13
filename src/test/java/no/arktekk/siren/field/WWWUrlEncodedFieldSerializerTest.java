package no.arktekk.siren.field;

import io.vavr.control.Option;
import net.hamnaberg.json.Json;
import no.arktekk.siren.Field;
import no.arktekk.siren.Field.Type;
import no.arktekk.siren.Fields;
import no.arktekk.siren.MIMEType;
import org.junit.Test;

import static org.junit.Assert.*;

public class WWWUrlEncodedFieldSerializerTest {
    WWWUrlEncodedFieldSerializer serializer = WWWUrlEncodedFieldSerializer.INSTANCE;

    @Test
    public void testSerialize() {
        Option<Fields> fields = Option.of(Fields.of(
                Field.of("field-1"),
                Field.of("href", Type.URL).with(Json.jString("http://www.example.com/meh?hello=hei&hei=jsdf"))
        ));

        assertTrue("URLEncoded didnt match null", serializer.serialize(null, fields).isDefined());
        assertTrue("URLEncoded didnt match All", serializer.serialize(MIMEType.All, fields).isDefined());
        assertFalse("URLEncoded matched json", serializer.serialize(MIMEType.JSON, fields).isDefined());
        assertFalse("URLEncoded matched html", serializer.serialize(MIMEType.text("html"), fields).isDefined());


        Option<String> us = serializer.serialize(MIMEType.URLEncoded, fields);
        String expected = "field-1=&href=http%3A%2F%2Fwww.example.com%2Fmeh%3Fhello%3Dhei%26hei%3Djsdf";
        assertEquals(expected, us.get());
    }
}
