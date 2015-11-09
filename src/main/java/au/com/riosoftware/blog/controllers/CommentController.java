package au.com.riosoftware.blog.controllers;

import au.com.riosoftware.blog.domain.Comment;
import au.com.riosoftware.blog.domain.CommentRepository;
import au.com.riosoftware.blog.domain.Post;
import au.com.riosoftware.blog.domain.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity createNew(@PathVariable long postId, @RequestBody Comment newComment) {
        Post post = postRepository.findOne(postId);
        Comment comment = new Comment(post, newComment.getMessage());
        commentRepository.save(comment);
        post.addComment(comment);
        postRepository.save(post);
        return new ResponseEntity(comment, HttpStatus.CREATED);
    }

    @RequestMapping(value="", method=RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Comment comment) {

        if (commentRepository.exists(comment.getId())) {
            commentRepository.save(comment);
        } else {
            return new ResponseEntity<String>("Comment #" + comment.getId() + " does not exist.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(comment, HttpStatus.OK);

    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable long commentId) {
        if (commentRepository.exists(commentId)) {
            Comment comment = commentRepository.findOne(commentId);
            comment.getPost().removeComment(comment);
            postRepository.save(comment.getPost());
            commentRepository.delete(comment);
        }else{
            return new ResponseEntity<String>("Comment #" + commentId + " does not exist.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
