package au.com.riosoftware.blog.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String message;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post() {
        initializeComments();
    }

    public Post(String title, String message) {
        initializeComments();
        this.title = title;
        this.message = message;
    }

    private void initializeComments(){
        this.comments = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return message;
    }

    public String getMessage() {
        return message;
    }

    public boolean addComment(Comment comment) {
        return comments.add(comment);
    }

    public boolean removeComment(Comment comment) {
        return this.comments.remove(comment);
    }

    public Iterable<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message +
                '}';
    }

}
