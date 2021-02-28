package ru.demapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SerializerJson {
    private static final Logger log = LoggerFactory.getLogger(SerializerJson.class);
    static Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    public static void write(Map mapResult, String address) throws IOException {
        try (FileWriter writer = new FileWriter(new File(address))) {
            GSON.toJson(mapResult, writer);
            writer.flush();
        }
        log.info("Serialized to json");
    }
}
