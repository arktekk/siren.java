package no.arktekk.siren.field;

import no.arktekk.siren.Field;
import no.arktekk.siren.MIMEType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class WWWUrlEncodedFieldSerializerTest {
    WWWUrlEncodedFieldSerializer serializer = WWWUrlEncodedFieldSerializer.INSTANCE;

    @Test
    public void testSerialize() {
        List<Field> list = Arrays.asList(
                Field.of("balle", "frans"),
                Field.of("href", "http://www.example.com/meh?hello=hei&hei=jsdf")
        );

        assertTrue("URLEncoded didnt match null", serializer.serialize(null, list).isPresent());
        assertTrue("URLEncoded didnt match All", serializer.serialize(MIMEType.All, list).isPresent());
        assertFalse("URLEncoded matched json", serializer.serialize(MIMEType.JSON, list).isPresent());
        assertFalse("URLEncoded matched html", serializer.serialize(MIMEType.text("html"), list).isPresent());


        Optional<String> us = serializer.serialize(MIMEType.URLEncoded, list);
        String expected = "balle=frans&href=http%3A%2F%2Fwww.example.com%2Fmeh%3Fhello%3Dhei%26hei%3Djsdf";
        assertEquals(expected, us.get());
    }
}