package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThreadMapper implements ResultSetMapper<Thread> {
    @Override
    public List<Thread> map(ResultSet rs) throws SQLException {
        List<Thread> threads = new ArrayList<>();

        while (rs.next()) {
            Thread thread = new Thread();
            thread.setId(rs.getLong("id"));
            thread.setUserId(rs.getLong("user_id"));
            thread.setTitle(rs.getString("title"));
            thread.setBody(rs.getString("body"));
            thread.setVotes(rs.getInt("votes"));
            thread.setArchived(rs.getBoolean("archived"));
            thread.setCategory(Category.valueOf(rs.getString("category")));
            thread.setCreationDate(rs.getDate("creation_date"));

            threads.add(thread);
        }

        return threads;
    }
}
