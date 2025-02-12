package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.ProdutoEstoque;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProdutoEstoqueDAO implements CRUD<ProdutoEstoque, Long>{


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProdutoEstoque pesquisaPeloId(Long id) {
        return entityManager.find(ProdutoEstoque.class, id);
    }

    @Override
    public List<ProdutoEstoque> lista() {
        Query query = entityManager.createQuery("select pe from ProdutoEstoque pe");
        return query.getResultList();
    }

    @Override
    public void insere(ProdutoEstoque produtoEstoque) {
        entityManager.persist(produtoEstoque);
    }

    @Override
    public void atualiza(ProdutoEstoque produtoEstoque) {
        entityManager.merge(produtoEstoque);
    }

    @Override
    public void remove(ProdutoEstoque produtoEstoque) {
        entityManager.remove(produtoEstoque);
    }
}
