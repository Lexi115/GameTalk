package it.unisa.studenti.nc8.gametalk.business.model.post;

import java.util.Date;

public abstract class Post {
    protected long id;
    protected long userId;
    protected String body;
    protected int votes;
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
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
                ", upVotes=" + votes +
                ", creationDate=" + creationDate +
                '}';
    }
}


