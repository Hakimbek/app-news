package uz.pdp.appnews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appnews.entity.Comment;
import uz.pdp.appnews.entity.Post;
import uz.pdp.appnews.entity.User;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.CommentDto;
import uz.pdp.appnews.repository.CommentRepository;
import uz.pdp.appnews.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;


    // add
    public ApiResponse add(CommentDto commentDto) {
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (!optionalPost.isPresent()) {
            return new ApiResponse("Post not found", false);
        }
        Post post = optionalPost.get();

        Comment comment = new Comment(commentDto.getText(), post);
        commentRepository.save(comment);
        return new ApiResponse("Successfully added", true);
    }


    // delete my comment
    public ApiResponse deleteMyComment(Long id) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isPresent()) {
                Comment comment = optionalComment.get();
                if (comment.getCreatedBy().equals(user)) {
                    commentRepository.deleteById(id);
                }
            }
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    // get all
    public List<Comment> get() {
        return commentRepository.findAll();
    }


    // get by id
    public Comment getById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }


    // edit
    public ApiResponse edit(Long id, CommentDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) {
            return new ApiResponse("Comment not found", false);
        }
        Comment comment = optionalComment.get();
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return new ApiResponse("Comment successfully edited", true);
    }


    // delete
    public ApiResponse delete(Long id) {
        try {
            commentRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }
}
