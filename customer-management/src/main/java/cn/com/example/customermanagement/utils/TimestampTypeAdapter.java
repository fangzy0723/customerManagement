package cn.com.example.customermanagement.utils;

import com.google.gson.*;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp>{

    public JsonElement serialize(Timestamp src, Type arg1, JsonSerializationContext arg2) {
        String dateFormatAsString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(src.getTime()));
        return new JsonPrimitive(dateFormatAsString);
    }

    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        try {
            Date date = DateUtils.parse("yyyy-MM-dd HH:mm:ss",json.getAsString());
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }

}