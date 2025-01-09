package it.unisa.studenti.nc8.gametalk.post.thread;

import it.unisa.studenti.nc8.gametalk.enums.Category;
import it.unisa.studenti.nc8.gametalk.post.Post;

public class Thread extends Post {
    private String title;
    private boolean archived;
    private Category category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "title='" + title + '\'' +
                ", archived=" + archived +
                ", category=" + category +
                "} " + super.toString();
    }
}
