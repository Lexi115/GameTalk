package it.unisa.studenti.nc8.gametalk.business.services.post.comment;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

@Disabled
public class CommentServiceImplTest {
    private static CommentService commentService;

    @BeforeAll
    static void setUp() throws ConnectException {
        Database db = new DatabaseImpl("localhost", 3306, "root", "root_pw", "gametalk_db", "mysql");
        //commentService = new CommentServiceImpl(db);
    }

    @Test
    void addComment() throws ServiceException {
        commentService.addComment(7,"BlazeX","schifo piu cacca");
    }

    @Test
    void rateComment() throws ServiceException {
        commentService.rateComment(1,"ApexLegends",0);
        commentService.rateComment(1,"AlphaMaster",0);
    }

    @Test
    void findCommentsByThreadId() throws ServiceException {
        commentService.findCommentsByThreadId(7,null,1, 12);
    }
}
