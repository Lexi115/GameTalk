package it.unisa.studenti.nc8.gametalk.business.validators;

import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

public class CommentValidator implements Validator<Comment> {
    @Override
    public boolean validate(final Comment comment) {
        return false;
    }
}
