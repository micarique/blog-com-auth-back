package com.example.blog_com_auth_backend.service;

import com.example.blog_com_auth_backend.dto.PostDTO;
import com.example.blog_com_auth_backend.model.Post;
import com.example.blog_com_auth_backend.model.User;
import com.example.blog_com_auth_backend.repository.PostRepository;
import com.example.blog_com_auth_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> listPost() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public ResponseEntity<Post> searchPost(Long id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Post> createPost(PostDTO dto, UserDetails userDetails) {
        User autor = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Post post = Post.builder()
                .titulo(dto.getTitulo())
                .conteudo(dto.getConteudo())
                .createdAt(LocalDateTime.now())
                .autor(autor)
                .build();
        postRepository.save(post);
        return ResponseEntity.ok(post);
    }

    public ResponseEntity<?> updatePost(Long id, PostDTO dto, UserDetails userDetails) {
        return postRepository.findById(id).map(post -> {
            if (!post.getAutor().getEmail().equals(userDetails.getUsername())) {
                return ResponseEntity.status(403).build();
            }
            post.setTitulo(dto.getTitulo());
            post.setConteudo(dto.getConteudo());
            postRepository.save(post);
            return ResponseEntity.ok(post);
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> deletePost(Long id, UserDetails userDetails) {
        return postRepository.findById(id).map(post -> {
            if (!post.getAutor().getEmail().equals(userDetails.getUsername())) {
                return ResponseEntity.status(403).build();
            }
            postRepository.delete(post);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
