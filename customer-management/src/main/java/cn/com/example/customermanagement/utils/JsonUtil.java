package cn.com.example.customermanagement.utils;

import com.google.gson.*;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JSON转换工具类
 *
 * @author liyuanming
 * @version 2010-12-24
 */
public class JsonUtil {

    private static Gson gson = new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(ArrayList.class, new ListInstanceCreator()).registerTypeAdapter(Date.class, new DateTypeAdapter()).registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).create();

    public static class ListInstanceCreator implements InstanceCreator<List<Object>> {

        public List<Object> createInstance(Type type) {
            return new ArrayList<Object>();
        }
    }

    public static class StringDeserializer implements JsonDeserializer<String> {

        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonPrimitive()) {
                return json.getAsString();
            }
            return "";
        }
    }

    public static <T> T fromJson(Reader read, Class<T> clazz) {
        try {
            T object = gson.fromJson(read, clazz);
            return object;
        } catch (Exception ex) {
            return new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(ArrayList.class, new ListInstanceCreator()).registerTypeAdapter(Date.class, new DateTypeAdapter()).registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).create().fromJson(read, clazz);
        } 
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            T object = gson.fromJson(json, clazz);
            return object;
        } catch (Exception ex) {
            return new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(ArrayList.class, new ListInstanceCreator()).registerTypeAdapter(Date.class, new DateTypeAdapter()).registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).create().fromJson(json, clazz);
        }
    }

    @SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Type TypeOfT) {
        try {
			T object = (T)gson.fromJson(json, TypeOfT);
            return object;
        } catch (Exception ex) {

            return (T)new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(ArrayList.class, new ListInstanceCreator()).registerTypeAdapter(Date.class, new DateTypeAdapter()).registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).create().fromJson(json, TypeOfT);
        }
    }

    public static String toJson(Object obj) {
        try {
            String str = gson.toJson(obj);
            return str;
        } catch (Exception ex) {
            return new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(ArrayList.class, new ListInstanceCreator()).registerTypeAdapter(Date.class, new DateTypeAdapter()).registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).create().toJson(obj);
        }
    }
    
    public static String toJson(Object obj,Type type) {
        try {
            String str = gson.toJson(obj, type);
            return str;
        } catch (Exception ex) {
            return new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(String.class, new StringDeserializer()).registerTypeAdapter(ArrayList.class, new ListInstanceCreator()).registerTypeAdapter(Date.class, new DateTypeAdapter()).registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).create().toJson(obj,type);
        }
    }
}
