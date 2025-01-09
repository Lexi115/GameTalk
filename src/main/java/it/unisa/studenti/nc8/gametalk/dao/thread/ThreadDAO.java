package it.unisa.studenti.nc8.gametalk.dao.thread;

import it.unisa.studenti.nc8.gametalk.dao.DAO;
import it.unisa.studenti.nc8.gametalk.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.post.thread.Thread;

import java.util.List;

public interface ThreadDAO extends DAO<Thread> {
    List<Thread> getThreadsByTitle(String title) throws DAOException;
}
