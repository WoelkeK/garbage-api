package pl.woelke.garbageapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.woelke.garbageapi.controller.dto.PostDto;
import pl.woelke.garbageapi.controller.dto.PostDtoMapper;
import pl.woelke.garbageapi.model.Post;
import pl.woelke.garbageapi.service.PostService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostDto> getPosts(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        List<PostDto> postList = PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber, sortDirection));
        return postList;
    }

    @GetMapping("/posts/comments")
    public List<Post> getPostsWithComments(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        List<Post> postList = postService.getPostsWithComments(pageNumber, sortDirection);
        return postList;
    }

    @GetMapping("/posts/{id}")
    public Post getSinglePost(
            @PathVariable long id
    ) {
        Post singlePost = postService.getSinglePost(id);
        return singlePost;
    }

    @PostMapping("/posts")
    public Post addPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return createdPost;

    }

    @PutMapping("/posts")
    public Post editPost(@RequestBody Post post) {
        Post updatedPost = postService.updatePost(post);
        return updatedPost;
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable long id){
        postService.deletePost(id);
    }
}
