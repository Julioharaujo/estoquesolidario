package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.DoacaoSaida;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DoacaoSaidaDAO implements CRUD<DoacaoSaida, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DoacaoSaida pesquisaPeloId(Long id) {
        return entityManager.find(DoacaoSaida.class, id);
    }

    @Override
    public List<DoacaoSaida> lista() {
        Query query = entityManager.createQuery("select ns from DoacaoSaida ns");
        return query.getResultList();
    }

    @Override
    public void insere(DoacaoSaida doacaoSaida) {
        entityManager.persist(doacaoSaida);
    }

    @Override
    public void atualiza(DoacaoSaida doacaoSaida) {
        entityManager.merge(doacaoSaida);
    }

    @Override
    public void remove(DoacaoSaida doacaoSaida) {
        entityManager.remove(doacaoSaida);
    }
}
