package it.unisa.studenti.nc8.gametalk.storage.dao.thread;

import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.util.List;

public interface ThreadDAO extends DAO<Thread> {
    List<Thread> getThreadsByTitle(String title) throws DAOException;
}
