package com.example.blog_com_auth_backend.dto;

import com.example.blog_com_auth_backend.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private String autor;
    private LocalDateTime createdAt;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.titulo = post.getTitulo();
        this.conteudo = post.getConteudo();
        this.autor = post.getAutor().getName();
        this.createdAt = post.getCreatedAt();
    }
}