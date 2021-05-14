package uz.pdp.appnews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnews.entity.Post;
import uz.pdp.appnews.entity.User;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.PostDto;
import uz.pdp.appnews.repository.PostRepository;
import uz.pdp.appnews.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;


    // add
    public ApiResponse add(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getText(), postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Successfully added", true);
    }


    // delete
    public ApiResponse delete(Long id) {
        try {
            postRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    // get all
    public List<Post> get() {
        return postRepository.findAll();
    }


    // get by id
    public Post getById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }


    // edit
    public ApiResponse edit(Long id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return new ApiResponse("Post not found",false);
        }
        Post post = optionalPost.get();
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Successfully edited", true);
    }
}
