package it.unisa.studenti.nc8.gametalk.business.factories;

import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;

public interface ServiceFactory {
    AuthenticationService createAuthenticationService();
    UserService createUserService();
    ThreadService createThreadService();
    CommentService createCommentService();
}
