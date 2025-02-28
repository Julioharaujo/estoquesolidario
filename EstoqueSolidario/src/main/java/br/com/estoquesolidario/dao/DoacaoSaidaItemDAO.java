package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.DoacaoSaidaItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DoacaoSaidaItemDAO implements CRUD<DoacaoSaidaItem, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DoacaoSaidaItem pesquisaPeloId(Long id) {
        return entityManager.find(DoacaoSaidaItem.class, id);
    }

    @Override
    public List<DoacaoSaidaItem> lista() {
        Query query = entityManager.createQuery("select nsi from DoacaoSaidaItem nsi");
        return query.getResultList();
    }

    @Override
    public void insere(DoacaoSaidaItem doacaoSaidaItem) {
        entityManager.persist(doacaoSaidaItem);
    }

    @Override
    public void atualiza(DoacaoSaidaItem doacaoSaidaItem) {
        entityManager.merge(doacaoSaidaItem);
    }

    @Override
    public void remove(DoacaoSaidaItem doacaoSaidaItem) {
        entityManager.remove(doacaoSaidaItem);
    }

    public List<DoacaoSaidaItem> listaItensDoacao(Long doacaoSaidaId){
        Query query = entityManager.createQuery("from DoacaoSaidaItem n where n.doacaoSaida.id = :doacaoSaidaId")
                .setParameter("doacaoSaidaId", doacaoSaidaId);
        return query.getResultList();
    }
}
