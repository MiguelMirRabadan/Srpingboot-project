package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;

import java.util.List;

public interface UsuarioDAO {

    List<Usuario> getUsuarios();

    void eliminar(int id);

    void registrar(Usuario usuario);

    void login(Usuario usuario);

    Usuario verificarCredenciales(Usuario usuario);
}
