package com.sw.av.infrastructure.base.marshal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JacksonJsonConverter implements JsonConverter {

    private static final Log logger = LogFactory.getLog(JacksonJsonConverter.class);
    private static JacksonJsonConverter instance = null;
    private ObjectMapper mapper = new ObjectMapper();

    private JacksonJsonConverter() {
    }

    public static JacksonJsonConverter getInstance() {
        if (instance == null) {
            instance = new JacksonJsonConverter();
        }
        return instance;
    }

    public ObjectMapper getObjectMapper() {
        return this.mapper;
    }

    public Object fromJson(String objectAsString, Class<?> tipoObjeto) throws IOException {
        return this.mapper.readValue(objectAsString, tipoObjeto);
    }

    public Object fromJsonView(String objectAsString, Class<?> view) throws IOException {
        this.mapper.disable(new MapperFeature[]{MapperFeature.DEFAULT_VIEW_INCLUSION});
        return this.mapper.readerWithView(view).readValue(objectAsString);
    }

    public Object fromJson(String objectAsString, JavaType tipoObjeto) throws IOException {
        return this.mapper.readValue(objectAsString, tipoObjeto);
    }
    public Object fromJsonArray(String objectAsString,Class<?> ofType) throws IOException {
        return this.mapper.readValue(objectAsString, this.mapper.getTypeFactory().constructCollectionType(List.class,ofType));
    }

    public String toJson(Object object) throws IOException {
        return this.mapper.writeValueAsString(object);
    }

    public String toJson(Object object, Class<?> view) throws IOException {
        this.mapper.disable(new MapperFeature[]{MapperFeature.DEFAULT_VIEW_INCLUSION});
        return this.mapper.writerWithView(view).writeValueAsString(object);
    }

    public String prettyPrinter(String json) throws JsonProcessingException{
        return this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }
}
