package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentMapper implements ResultSetMapper<Comment> {
    @Override
    public List<Comment> map(ResultSet rs) throws SQLException {
        return List.of();
    }
}
