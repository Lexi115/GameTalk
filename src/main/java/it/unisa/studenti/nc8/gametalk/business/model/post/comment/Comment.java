package it.unisa.studenti.nc8.gametalk.business.model.post.comment;

import it.unisa.studenti.nc8.gametalk.business.model.post.Post;

public class Comment extends Post {
    private long threadId;

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "threadId=" + threadId +
                "} " + super.toString();
    }
}
