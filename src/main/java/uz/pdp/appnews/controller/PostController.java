package uz.pdp.appnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnews.aop.CheckPermission;
import uz.pdp.appnews.entity.Post;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.PostDto;
import uz.pdp.appnews.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;


    // add
    @CheckPermission(permission = "ADD_POST")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.add(postDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // delete
    @CheckPermission(permission = "DELETE_POST")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = postService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // get all
    @GetMapping
    public ResponseEntity<?> get() {
        List<Post> posts = postService.get();
        return ResponseEntity.status(posts.size() != 0 ? 200 : 409).body(posts);
    }


    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Post post = postService.getById(id);
        return ResponseEntity.status(post != null ? 200 : 409).body(post);
    }


    // edit
    @CheckPermission(permission = "EDIT_POST")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.edit(id, postDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
