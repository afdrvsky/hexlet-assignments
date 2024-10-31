package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<Post>> postsByUser(@PathVariable int id) {
        var userPosts = posts.stream().filter(p -> p.getUserId() == id).toList();
        return ResponseEntity.ok().body(userPosts);
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return ResponseEntity.status(201).body(post);
    }
}
// END
