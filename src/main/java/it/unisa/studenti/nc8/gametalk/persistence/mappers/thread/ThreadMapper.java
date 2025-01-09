package it.unisa.studenti.nc8.gametalk.persistence.mappers.thread;

import it.unisa.studenti.nc8.gametalk.enums.Category;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.post.thread.Thread;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ThreadMapper implements ResultSetMapper<Thread> {
    @Override
    public List<Thread> map(ResultSet rs) throws SQLException {
        // Convertire da stringa ad enum
        Category category = Category.valueOf(rs.getString("category"));

        // Convertire da enum a stringa
        String categoryString = category.toString(); // es. "Welcome"

        return List.of();
    }
}
