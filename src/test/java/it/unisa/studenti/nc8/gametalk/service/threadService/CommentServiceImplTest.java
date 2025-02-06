package it.unisa.studenti.nc8.gametalk.service.threadService;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.service.post.CommentService;
import it.unisa.studenti.nc8.gametalk.business.service.post.CommentServiceImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommentServiceImplTest {
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        Database db = new DatabaseImpl("localhost", 3306, "root", "root_pw", "gametalk_db");
        commentService = new CommentServiceImpl(db);
    }

    @Test
    void createComment() throws ServiceException {
        commentService.addComment(7,"BlazeX","schifo piu cacca");
    }

    @Test
    void upvoteComment() throws ServiceException {
        commentService.rateComment(1,"ApexLegends",0);
        commentService.rateComment(1,"AlphaMaster",0);
    }

    @Test
    void getCommentsByThread() throws ServiceException {
        System.out.println(commentService.findCommentsByThreadId(7,null,1, 12));
    }

}
