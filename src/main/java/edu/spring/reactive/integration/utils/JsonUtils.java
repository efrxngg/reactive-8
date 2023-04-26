package edu.spring.reactive.integration.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonUtils {
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param object Objeto Java a convertir
     * @return
     * @throws JsonProcessingException
     */
    public String convertirAJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     *
     * @param json  String a convertir
     * @param clase Clase resultante
     * @throws IOException
     */
    public <T> T convertirAObjeto(final String json, final Class<T> clase) throws IOException {
        return objectMapper.readValue(json, clase);
    }

    /**
     *
     * @param json String a convertir
     * @param ref  Referencia de tipos para objetos genericos
     * @return Object, castear al tipo enviado.
     * @throws IOException
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object convertirAObjeto(final String json, TypeReference ref) throws IOException {
        return objectMapper.readValue(json, ref);
    }

}
