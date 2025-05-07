package com.example.blog_com_auth_backend.controller;

import com.example.blog_com_auth_backend.dto.PostDTO;
import com.example.blog_com_auth_backend.dto.PostResponseDTO;
import com.example.blog_com_auth_backend.model.Post;
import com.example.blog_com_auth_backend.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Endpoint público para listar posts
    @GetMapping
    public List<PostResponseDTO> listPost() {
        return postService.listPost();
    }

    // Endpoint público para obter post por ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> searchPost(@PathVariable Long id) {
        return postService.searchPost(id);
    }

    // Endpoint privado para criar post (requer autenticação)
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @Valid PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(postDTO, userDetails);
    }

    // Atualizar post (autenticado)
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.updatePost(id, postDTO, userDetails);
    }

    // Deletar post (autenticado)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.deletePost(id, userDetails);
    }
}
