package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user;

import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMapper implements ResultSetMapper<User> {
    @Override
    public List<User> map(ResultSet rs) throws SQLException {
        return List.of();
    }
}
