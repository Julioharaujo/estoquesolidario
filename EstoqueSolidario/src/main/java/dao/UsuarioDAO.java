package dao;

import model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UsuarioDAO implements CRUD<Usuario, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Usuario pesquisaPeloId(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> lista() {
        Query query = entityManager.createQuery("select c from Usuario c");
        return (List<Usuario>) ((jakarta.persistence.Query) query).getResultList();
    }

    @Override
    public void insere(Usuario usuario) {
        entityManager.persist(usuario);
    }

    @Override
    public void atualiza(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public void remove(Usuario usuario) {
        entityManager.remove(usuario);
    }
}
