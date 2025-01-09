package it.unisa.studenti.nc8.gametalk.post;

import java.time.LocalDate;
import java.util.Date;

public abstract class Post {
    protected long id;
    protected long userId;
    protected String body;
    protected int upVotes;
    protected Date creationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", body='" + body + '\'' +
                ", upVotes=" + upVotes +
                ", creationDate=" + creationDate +
                '}';
    }
}


