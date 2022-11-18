package pl.woelke.garbageapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.woelke.garbageapi.model.Comment;
import pl.woelke.garbageapi.model.Post;
import pl.woelke.garbageapi.repository.CommentRepository;
import pl.woelke.garbageapi.repository.PostRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final int PAGESIZE = 5;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<Post> getPosts(int page, Sort.Direction sort) {
        List<Post> postList = postRepository.findAllPosts(PageRequest.of(page, PAGESIZE, Sort.by(sort, "id")));
        return postList;
    }

    public Post getSinglePost(Long id) {
        Optional<Post> singlePost = postRepository.findById(id);
        return singlePost.orElseThrow();
    }

    public List<Post> getPostsWithComments(int page, Sort.Direction sort) {
        List<Post> allPosts = postRepository.findAllPosts(PageRequest.of(page, PAGESIZE, Sort.by(sort, "id")));
        List<Long> ids = allPosts.stream()
                .map(Post::getId)
                .collect(Collectors.toList());
        List<Comment> comments = commentRepository.findAllByPostIdIn(ids);
        allPosts.forEach(post -> post.setComment(extractComments(comments, post.getId())));
        return allPosts;
    }

    private List<Comment> extractComments(List<Comment> comments, Long id) {
        return comments.stream()
                .filter(comment -> comment.getPostId() == id)
                .collect(Collectors.toList());
    }

    public Post createPost(Post post) {
        Post createdPost = postRepository.save(post);
        return createdPost;
    }
    @Transactional
    public Post updatePost(Post post) {
        Post editedPost = postRepository.findById(post.getId()).orElseThrow();
        editedPost.setTitle(post.getTitle());
        editedPost.setContent(post.getContent());
        Post updatedPost = postRepository.save(post);
        return updatedPost;
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
