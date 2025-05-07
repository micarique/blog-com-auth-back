package com.example.blog_com_auth_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDTO {

    @NotBlank
    private String titulo;

    @NotBlank
    private String conteudo;
}

