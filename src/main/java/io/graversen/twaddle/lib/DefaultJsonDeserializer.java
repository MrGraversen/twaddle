package io.graversen.twaddle.lib;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class DefaultJsonDeserializer extends JsonDeserializer<String> implements ContextualDeserializer
{
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
    {
        String value = jsonParser.getValueAsString();

        if (!StringUtils.isEmpty(jsonParser))
        {
            value = Jsoup.clean(value, Whitelist.none());
        }

        return value;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty)
    {
        return this;
    }
}
