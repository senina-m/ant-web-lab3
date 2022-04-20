package ru.nanikon.third.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nanikon.third.entity.ShotEntity;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/** Writes a list from ShotEntity in json-timing format
 *
 * @author Natalia Nikonova
 */
@ManagedBean(name = "parser")
@ApplicationScoped
public class ShotListJsonParser implements Serializable {
   private ObjectMapper objectMapper;

   public ShotListJsonParser() {
      objectMapper = new ObjectMapper();
   }

   public String fromObjectToJson(List<ShotEntity> list) {
      try {
         return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list.toArray());
      } catch (JsonProcessingException e) {
         throw new RuntimeException("Parser error with " + Arrays.toString(list.toArray()));
      }
   }
}
