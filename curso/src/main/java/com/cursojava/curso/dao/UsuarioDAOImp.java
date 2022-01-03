package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // va a tener la funcionalidad que permite acceso a la BDD
@Transactional // como va a ejecutar las sentencias sql
public class UsuarioDAOImp implements  UsuarioDAO{

    @PersistenceContext
    private EntityManager entityManager; //Conexión con la BDD

    @Override
    public List<Usuario> getUsuarios() {
        String query = "From Usuario";

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(int id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public void login(Usuario usuario) {

    }

    @Override
    public Usuario verificarCredenciales(Usuario usuario) {
        String query = "From Usuario WHERE email= :email";

       List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

       if(lista.isEmpty()) return null;

       Argon2 argon2  = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

       if(argon2.verify(lista.get(0).getPassword(), usuario.getPassword())) return lista.get(0);

       return null;
    }
}
