package net.hamnaberg.siren;

public interface JsonSerializable {

    <T> T toJson(JsonSerializer<T> serializer);
}
