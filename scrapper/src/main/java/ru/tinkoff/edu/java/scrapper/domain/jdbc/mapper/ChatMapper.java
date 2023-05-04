package ru.tinkoff.edu.java.scrapper.domain.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.model.TableChat;

@Component
public class ChatMapper implements RowMapper<TableChat> {

    @Override
    public TableChat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TableChat(rs.getLong(1), rs.getString(2));
    }
}
