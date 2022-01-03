package com.cursojava.curso.controllers;

import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.dao.UsuarioDAO;
import com.cursojava.curso.utils.JWTutil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {


    @Autowired // se crea un objeto de la clase automáticamente y se comparte en memoria por cada autowired que se indique.
    private UsuarioDAO usuarioDAO;

    @Autowired
    private JWTutil jwtutil;

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrar(@RequestBody Usuario usuario){

        Argon2 argon2  = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); // encryptar el password de manera irreversible
        String hash = argon2.hash(1,1024,1, usuario.getPassword()); // numero de iteraciones que va a realizar para encryptar el pwd
        usuario.setPassword(hash);
       usuarioDAO.registrar(usuario);
    }

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        Usuario usuarioVerificado = usuarioDAO.verificarCredenciales(usuario);
        if(usuarioVerificado != null){

            String TOKEN = jwtutil.create(String.valueOf(usuarioVerificado.getId()),String.valueOf(usuarioVerificado.getEmail()));

           return TOKEN;
        }
        return "ERROR";
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token){
        if(!validarToken(token)) return null;
        return usuarioDAO.getUsuarios();
    }


    @RequestMapping(value = "api/editar")
    public Usuario edit(){

        return null;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token,
                         @PathVariable int id){
        if(!validarToken(token)) return;

        usuarioDAO.eliminar(id);
    }

    private boolean validarToken(String token){
        return (jwtutil.getKey(token)) != null;
    }


}
