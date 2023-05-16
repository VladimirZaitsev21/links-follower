package ru.tinkoff.edu.java.scrapper.domain.jdbc.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.model.TableLink;

@Component
public class LinkMapper implements RowMapper<TableLink> {

    public static final int UPDATE_INFO_COLUMN_INDEX = 4;
    public static final int UPDATED_AT_COLUMN_INDEX = 3;
    public static final int LINK_COLUMN_INDEX = 2;
    public static final int ID_COLUMN_INDEX = 1;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public TableLink mapRow(ResultSet rs, int rowNum) throws SQLException {
        var object = (PGobject) rs.getObject(UPDATE_INFO_COLUMN_INDEX);
        Map<String, Object> updateInfoFinal = null;

        if (object != null) {
            var updateInfo = object.getValue();
            try {
                updateInfoFinal = mapper.readValue(
                        updateInfo,
                        TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class)
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return new TableLink(
                rs.getLong(ID_COLUMN_INDEX),
                rs.getString(LINK_COLUMN_INDEX),
                rs.getTimestamp(UPDATED_AT_COLUMN_INDEX),
                updateInfoFinal == null ? new HashMap<>() : updateInfoFinal
        );
    }
}
