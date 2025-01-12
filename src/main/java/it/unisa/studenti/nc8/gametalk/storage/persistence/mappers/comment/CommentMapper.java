package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper implements ResultSetMapper<Comment> {
    @Override
    public List<Comment> map(ResultSet rs) throws SQLException {
        List<Comment> comments = new ArrayList<>();

        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setThreadId(rs.getLong("thread_id"));
            comment.setUserId(rs.getLong("user_id"));
            comment.setBody(rs.getString("body"));
            comment.setVotes(rs.getInt("votes"));
            comment.setCreationDate(rs.getDate("creation_date"));
            comments.add(comment);
        }

        return comments;
    }
}
