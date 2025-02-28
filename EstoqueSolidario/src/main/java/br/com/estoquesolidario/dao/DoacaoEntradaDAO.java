package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.DoacaoEntrada;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DoacaoEntradaDAO implements CRUD<DoacaoEntrada, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DoacaoEntrada pesquisaPeloId(Long id) {
        return entityManager.find(DoacaoEntrada.class, id);
    }

    @Override
    public List<DoacaoEntrada> lista() {
        Query query = entityManager.createQuery("select ne from DoacaoEntrada ne");
        return query.getResultList();
    }

    @Override
    public void insere(DoacaoEntrada doacaoEntrada) {
        entityManager.persist(doacaoEntrada);
    }

    @Override
    public void atualiza(DoacaoEntrada doacaoEntrada) {
        entityManager.merge(doacaoEntrada);
    }

    @Override
    public void remove(DoacaoEntrada doacaoEntrada) {
        entityManager.remove(doacaoEntrada);
    }
}
