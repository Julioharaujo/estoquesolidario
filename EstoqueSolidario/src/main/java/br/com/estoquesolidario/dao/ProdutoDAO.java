package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProdutoDAO implements CRUD<Produto, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Produto pesquisaPeloId(Long id) {
        return entityManager.find(Produto.class, id);
    }

    @Override
    public List<Produto> lista() {
        Query query = entityManager.createQuery("select p from Produto p");
        return query.getResultList();
    }

    @Override
    public void insere(Produto produto) {
        entityManager.persist(produto);
    }

    @Override
    public void atualiza(Produto produto) {
        entityManager.merge(produto);
    }

    @Override
    public void remove(Produto produto) {
        entityManager.remove(produto);
    }
}