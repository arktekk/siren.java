package net.hamnaberg.siren;

public interface Entity {

    <T> T toJson(JsonSerializer<T> serializer);
}
