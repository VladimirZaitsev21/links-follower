package ru.tinkoff.edu.java.scrapper.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.model.Link;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LinkMapper implements RowMapper<Link> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        var updateInfoString = rs.getString(4);
        Map<String, Object> updateInfo;
        try {
            updateInfo = mapper.readValue(
                    updateInfoString,
                    TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new Link(
                rs.getLong(1),
                URI.create(rs.getString(2)),
                rs.getTimestamp(3),
                updateInfo
                );
    }
}
