package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.NotaEntrada;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class NotaEntradaDAO implements CRUD<NotaEntrada, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NotaEntrada pesquisaPeloId(Long id) {
        return entityManager.find(NotaEntrada.class, id);
    }

    @Override
    public List<NotaEntrada> lista() {
        Query query = entityManager.createQuery("select ne from NotaEntrada ne");
        return query.getResultList();
    }

    @Override
    public void insere(NotaEntrada notaEntrada) {
        entityManager.persist(notaEntrada);
    }

    @Override
    public void atualiza(NotaEntrada notaEntrada) {
        entityManager.merge(notaEntrada);
    }

    @Override
    public void remove(NotaEntrada notaEntrada) {
        entityManager.remove(notaEntrada);
    }
}
