package ru.tinkoff.edu.java.scrapper.domain.jooq.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.jooq.JSONB;
import org.jooq.Record4;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.model.TableLink;

@Component
public class LinkFieldsMapper implements RecordMapper<Record4<Integer, String, LocalDateTime, JSONB>, TableLink> {

    private final ObjectMapper objectMapper;

    public LinkFieldsMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public TableLink map(Record4<Integer, String, LocalDateTime, JSONB> recordToMap) {
        Map<String, Object> updateInfo = null;

        if (recordToMap.component4() != null) {
            try {
                updateInfo = objectMapper.readValue(
                        recordToMap.component4().data(),
                        TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class)
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return new TableLink(
                recordToMap.component1(),
                recordToMap.component2(),
                recordToMap.component3() == null
                    ? null : Timestamp.from(recordToMap.component3().atZone(ZoneId.systemDefault()).toInstant()),
                updateInfo == null ? new HashMap<>() : updateInfo
        );
    }
}
