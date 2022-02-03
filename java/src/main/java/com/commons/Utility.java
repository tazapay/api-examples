package com.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import lombok.SneakyThrows;
import retrofit2.Call;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Utility {

    public static String getCurrentDate(String format) {

        DateFormat dateFormat = new SimpleDateFormat(format);
        //get current date time with Date()
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        return (dateFormat.format(date)).toString();
    }

    public static Properties readFromPropertiesFile(String fileName) throws IOException {
        InputStream resourceAsStream = Utility.class.getClassLoader().getResourceAsStream(fileName);
        Properties prop = new Properties();
        prop.load(resourceAsStream);
        return prop;
    }

    public static String prettyPrintJson(String json){

        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(json);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(parse);
    }

    public static String prettyPrintJson(Reader json){

        JsonReader reader = new JsonReader(json);
        reader.setLenient(true);
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(reader);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(parse);
    }


    public static String prettyPrintJson(InputStream is){
        return prettyPrintJson( new InputStreamReader(is));
    }

    public static String prettyPrintJson(Object obj){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(obj);
    }

    @SneakyThrows
    public static <T> T execute(Call<T> call){
        return call.execute().body();
    }
}
