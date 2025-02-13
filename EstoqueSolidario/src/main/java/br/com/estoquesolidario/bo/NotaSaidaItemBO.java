package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.NotaSaidaItemDAO;
import br.com.estoquesolidario.model.NotaSaidaItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaSaidaItemBO implements CRUD<NotaSaidaItem, Long> {

    @Autowired
    private NotaSaidaItemDAO notaSaidaItemDAO;

    @Override
    public NotaSaidaItem pesquisaPeloId(Long id) {
        return notaSaidaItemDAO.pesquisaPeloId(id);
    }

    @Override
    public List<NotaSaidaItem> lista() {
        return notaSaidaItemDAO.lista();
    }

    @Override
    public void insere(NotaSaidaItem notaSaidaItem) {
        notaSaidaItemDAO.insere(notaSaidaItem);
    }

    @Override
    public void atualiza(NotaSaidaItem notaSaidaItem) {
        notaSaidaItemDAO.atualiza(notaSaidaItem);
    }

    @Override
    public void remove(NotaSaidaItem notaSaidaItem) {
        notaSaidaItemDAO.remove(notaSaidaItem);
    }

    public boolean itemJaAdicionado(NotaSaidaItem notaSaidaItem) {
        Long notaSaidaId = notaSaidaItem.getNotaSaida().getId();
        List<NotaSaidaItem> itens = notaSaidaItemDAO.listaItensNota(notaSaidaId);
        Long produtoId = notaSaidaItem.getProduto().getId();

        if (notaSaidaItem.getId() == null) {
            for (NotaSaidaItem item : itens) {
                if (item.getProduto().getId() == produtoId) {
                    return true;
                }
            }
        } else {
            Long notaSaidaItemId = notaSaidaItem.getId();
            for (NotaSaidaItem item : itens) {
                if (item.getProduto().getId() == produtoId && notaSaidaItemId != item.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

}
