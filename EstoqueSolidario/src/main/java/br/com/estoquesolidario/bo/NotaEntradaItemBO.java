package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.NotaEntradaItemDAO;
import br.com.estoquesolidario.model.NotaEntradaItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaEntradaItemBO implements CRUD<NotaEntradaItem, Long> {

    @Autowired
    private NotaEntradaItemDAO notaEntradaItemDAO;

    @Override
    public NotaEntradaItem pesquisaPeloId(Long id) {
        return notaEntradaItemDAO.pesquisaPeloId(id);
    }

    @Override
    public List<NotaEntradaItem> lista() {
        return notaEntradaItemDAO.lista();
    }

    @Override
    public void insere(NotaEntradaItem notaEntradaItem) {
        notaEntradaItemDAO.insere(notaEntradaItem);
    }

    @Override
    public void atualiza(NotaEntradaItem notaEntradaItem) {
        notaEntradaItemDAO.atualiza(notaEntradaItem);
    }

    @Override
    public void remove(NotaEntradaItem notaEntradaItem) {
        notaEntradaItemDAO.remove(notaEntradaItem);
    }

    public boolean itemJaAdicionado(NotaEntradaItem notaEntradaItem) {
        Long notaEntradaId = notaEntradaItem.getNotaEntrada().getId();
        List<NotaEntradaItem> itens = notaEntradaItemDAO.listaItensNota(notaEntradaId);
        Long produtoId = notaEntradaItem.getProduto().getId();

        if (notaEntradaItem.getId() == null) {
            for (NotaEntradaItem item : itens) {
                if (item.getProduto().getId() == produtoId) {
                    return true;
                }
            }
        }else{
            Long notaEntradaItemId = notaEntradaItem.getId();
            for (NotaEntradaItem item : itens) {
                if (item.getProduto().getId() == produtoId && notaEntradaItemId == item.getId()) {
                    return true;
                }
            }

        }
        return false;
    }
}