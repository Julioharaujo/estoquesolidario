package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.DoacaoEntradaItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DoacaoEntradaItemDAO implements CRUD<DoacaoEntradaItem, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DoacaoEntradaItem pesquisaPeloId(Long id) {
        return entityManager.find(DoacaoEntradaItem.class, id);
    }

    @Override
    public List<DoacaoEntradaItem> lista() {
        Query query = entityManager.createQuery("select nei from DoacaoEntradaItem nei");
        return query.getResultList();
    }

    @Override
    public void insere(DoacaoEntradaItem doacaoEntradaItem) {
        entityManager.persist(doacaoEntradaItem);
    }

    @Override
    public void atualiza(DoacaoEntradaItem doacaoEntradaItem) {
        entityManager.merge(doacaoEntradaItem);
    }

    @Override
    public void remove(DoacaoEntradaItem doacaoEntradaItem) {
        entityManager.remove(doacaoEntradaItem);
    }

    public List<DoacaoEntradaItem> listaItensDoacao(Long doacaoEntradaId){
        Query query = entityManager.createQuery("from DoacaoEntradaItem n where n.doacaoEntrada.id = :doacaoEntradaId")
                                    .setParameter("doacaoEntradaId", doacaoEntradaId);
        return query.getResultList();
    }
}



