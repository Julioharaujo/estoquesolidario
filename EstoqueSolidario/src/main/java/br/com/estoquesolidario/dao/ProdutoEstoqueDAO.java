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

    public Integer verificaQuantidadePorProdutoId(Long produtoId) {
        try {
            Query query = entityManager.createQuery(
                    "SELECT pe.quantidade FROM ProdutoEstoque pe WHERE pe.produto.id = :produtoId");
            query.setParameter("produtoId", produtoId);
            return (Integer) query.getSingleResult(); // Retorna a quantidade ou null se não encontrado
        } catch (Exception e) {
            return null; // Trata exceções, como nenhum registro encontrado
        }
    }


    public ProdutoEstoque buscaPorProdutoId(Long produtoId) {
        try {
            Query query = entityManager.createQuery(
                    "SELECT pe FROM ProdutoEstoque pe WHERE pe.produto.id = :produtoId");
            query.setParameter("produtoId", produtoId);
            return (ProdutoEstoque) query.getSingleResult(); // Retorna o ProdutoEstoque ou null se não encontrado
        } catch (Exception e) {
            return null; // Trata exceções, como nenhum registro encontrado
        }
    }
}
