package it.unisa.studenti.nc8.gametalk.service.threadService;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.service.post.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.service.post.ThreadServiceImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadServiceImplTest {
    private ThreadService threadService;

    @BeforeEach
    void setUp() {
        Database db = new DatabaseImpl("localhost", 3306, "root", "", "gametalk_db");
        threadService = new ThreadServiceImpl(db);
    }

    @Test
    void createThreadTest() throws ServiceException {
        threadService.createThread(3,"rose","rosse molto rosse", Category.General);
    }

    @Test
    void updateThreadTest() throws ServiceException {
        threadService.updateThread(1,"rosa","rosse molto rosse", Category.Announcements);
    }
}
