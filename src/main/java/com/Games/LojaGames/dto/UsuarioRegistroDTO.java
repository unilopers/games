package com.Games.LojaGames.dto;

public record UsuarioRegistroDTO(
        String nomeUsuario, 
        String email, 
        String senha, 
        String nomeCompleto
) {}