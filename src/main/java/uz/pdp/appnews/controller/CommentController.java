package uz.pdp.appnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnews.aop.CheckPermission;
import uz.pdp.appnews.entity.Comment;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.CommentDto;
import uz.pdp.appnews.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;


    // add
    @CheckPermission(permission = "ADD_COMMENT")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CommentDto commentDto) {
        ApiResponse apiResponse = commentService.add(commentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // delete my comment
    @CheckPermission(permission = "DELETE_MY_COMMENT")
    @DeleteMapping("/deleteMyComment/{id}")
    public ResponseEntity<?> deleteMyComment(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // delete
    @CheckPermission(permission = "DELETE_COMMENT")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // get all
    @GetMapping
    public ResponseEntity<?> get() {
        List<Comment> comments = commentService.get();
        return ResponseEntity.status(comments.size() != 0 ? 200 : 409).body(comments);
    }


    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        return ResponseEntity.status(comment != null ? 200 : 409).body(comment);
    }


    // edit
    @CheckPermission(permission = "EDIT_COMMENT")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        ApiResponse apiResponse = commentService.edit(id, commentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
