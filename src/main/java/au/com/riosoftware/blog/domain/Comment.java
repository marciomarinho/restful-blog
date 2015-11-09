package au.com.riosoftware.blog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String message;

    @JsonBackReference
    @ManyToOne
    private Post post;

    public Comment() {
    }

    public Comment(Post post, String message) {
        this.post = post;
        this.message = message;
    }

    public long getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public Post getPost() {
        return this.post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (!message.equals(comment.message)) return false;
        return post.equals(comment.post);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + message.hashCode();
        result = 31 * result + post.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", post=" + post +
                '}';
    }

}
