package avers66.postalitem.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ZonedDateTimeTypeAdapter
 *
 * @Author Tretyakov Alexandr
 */

public class ZonedDateTimeTypeAdapter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.nZZZZZ");

    @Override
    public JsonElement serialize(ZonedDateTime zonedDateTime, Type srcType,
                                 JsonSerializationContext context) {

        return new JsonPrimitive(formatter.format(zonedDateTime));
    }

    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {

        return ZonedDateTime.parse(json.getAsString(), formatter);
    }
}
