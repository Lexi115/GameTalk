package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.dao.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.thread.ThreadMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.util.List;

public class ThreadService {
    private final ThreadDAO threadDAO;

    public ThreadService(Database db) {
        this.threadDAO = new ThreadDAOImpl(db,new ThreadMapper());
    }

    public Thread getThreadById(long id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("Id commento non valido");
            }

            return threadDAO.get(id);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Thread> getAllThreads() {
        try {
            return threadDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO ricerca!!!! paginazione!!!! non va bene cosi
    public List<Thread> getThreadsByTitle(String title) {
        try{
            if(title.isEmpty()){
                throw new IllegalArgumentException("Titolo non valido");
            }

            return threadDAO.getThreadsByTitle(title);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
    }
