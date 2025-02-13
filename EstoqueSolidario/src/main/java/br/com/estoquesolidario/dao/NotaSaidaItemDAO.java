package br.com.estoquesolidario.dao;

import br.com.estoquesolidario.model.NotaSaidaItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class NotaSaidaItemDAO implements CRUD<NotaSaidaItem, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NotaSaidaItem pesquisaPeloId(Long id) {
        return entityManager.find(NotaSaidaItem.class, id);
    }

    @Override
    public List<NotaSaidaItem> lista() {
        Query query = entityManager.createQuery("select nsi from NotaSaidaItem nsi");
        return query.getResultList();
    }

    @Override
    public void insere(NotaSaidaItem notaSaidaItem) {
        entityManager.persist(notaSaidaItem);
    }

    @Override
    public void atualiza(NotaSaidaItem notaSaidaItem) {
        entityManager.merge(notaSaidaItem);
    }

    @Override
    public void remove(NotaSaidaItem notaSaidaItem) {
        entityManager.remove(notaSaidaItem);
    }

    public List<NotaSaidaItem> listaItensNota(Long notaSaidaId){
        Query query = entityManager.createQuery("from NotaSaidaItem n where n.notaSaida.id = :notaSaidaId")
                .setParameter("notaSaidaId", notaSaidaId);
        return query.getResultList();
    }
}
