package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.NotaEntradaItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class NotaEntradaItemDAO implements CRUD<NotaEntradaItem, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NotaEntradaItem pesquisaPeloId(Long id) {
        return entityManager.find(NotaEntradaItem.class, id);
    }

    @Override
    public List<NotaEntradaItem> lista() {
        Query query = entityManager.createQuery("select nei from NotaEntradaItem nei");
        return query.getResultList();
    }

    @Override
    public void insere(NotaEntradaItem notaEntradaItem) {
        entityManager.persist(notaEntradaItem);
    }

    @Override
    public void atualiza(NotaEntradaItem notaEntradaItem) {
        entityManager.merge(notaEntradaItem);
    }

    @Override
    public void remove(NotaEntradaItem notaEntradaItem) {
        entityManager.remove(notaEntradaItem);
    }

    public List<NotaEntradaItem> listaItensNota(Long notaEntradaId){
        Query query = entityManager.createQuery("from NotaEntradaItem n where n.notaEntrada.id = :notaEntradaId")
                                    .setParameter("notaEntradaId", notaEntradaId);
        return query.getResultList();
    }
}



