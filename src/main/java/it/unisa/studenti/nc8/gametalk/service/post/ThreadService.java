package it.unisa.studenti.nc8.gametalk.service.post;

import it.unisa.studenti.nc8.gametalk.dao.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.dao.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.persistence.Database;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.thread.ThreadMapper;
import it.unisa.studenti.nc8.gametalk.post.thread.Thread;

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
