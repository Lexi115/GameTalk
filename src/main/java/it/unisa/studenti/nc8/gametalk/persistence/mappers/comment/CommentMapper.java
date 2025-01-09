package it.unisa.studenti.nc8.gametalk.persistence.mappers.comment;

import it.unisa.studenti.nc8.gametalk.dao.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentMapper implements ResultSetMapper<CommentDAO> {
    @Override
    public List<CommentDAO> map(ResultSet rs) throws SQLException {
        return List.of();
    }
}
