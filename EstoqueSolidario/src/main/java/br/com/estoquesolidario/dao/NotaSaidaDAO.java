package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.NotaSaida;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class NotaSaidaDAO implements CRUD<NotaSaida, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NotaSaida pesquisaPeloId(Long id) {
        return entityManager.find(NotaSaida.class, id);
    }

    @Override
    public List<NotaSaida> lista() {
        Query query = entityManager.createQuery("select ns from NotaSaida ns");
        return query.getResultList();
    }

    @Override
    public void insere(NotaSaida notaSaida) {
        entityManager.persist(notaSaida);
    }

    @Override
    public void atualiza(NotaSaida notaSaida) {
        entityManager.merge(notaSaida);
    }

    @Override
    public void remove(NotaSaida notaSaida) {
        entityManager.remove(notaSaida);
    }
}
