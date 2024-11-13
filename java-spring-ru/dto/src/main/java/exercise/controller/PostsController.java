package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        var posts = postRepository.findAll();
        var result = posts.stream().map(this::toPostDto).toList();
        return result;
    }

    @GetMapping("/{id}")
    public Optional<PostDTO> getPostById(@PathVariable Long id) {
        var post = postRepository.findById(id).map(this::toPostDto).orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return Optional.ofNullable(post);
    }

    private PostDTO toPostDto(Post post) {
        var postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());
        postDTO.setComments(commentRepository.findByPostId(post.getId()).stream().map(this::toCommentDto).toList());
        return postDTO;
    }

    private CommentDTO toCommentDto(Comment comment) {
        var commentsDto = new CommentDTO();
        commentsDto.setId(comment.getId());
        commentsDto.setBody(comment.getBody());
        return commentsDto;
    }
}
// END
