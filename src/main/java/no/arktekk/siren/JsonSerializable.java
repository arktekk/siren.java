package no.arktekk.siren;

public interface JsonSerializable {

    <T> T toJson(JsonSerializer<T> serializer);
}
