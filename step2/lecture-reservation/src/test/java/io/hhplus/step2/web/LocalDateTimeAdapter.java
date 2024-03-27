package io.hhplus.step2.web;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Gson LocalDateTime 관련 Type 오류
 * Error Message : Failed making field 'java.time.LocalDateTime#date' accessible; either increase its visibility or write a custom TypeAdapter for its declaring type.
 * https://stackoverflow.com/questions/75862344/gson-java-time-localdatetime-serialization-error
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.value(value != null ? value.toString() : null);
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        return LocalDateTime.parse(in.nextString());
    }
}
