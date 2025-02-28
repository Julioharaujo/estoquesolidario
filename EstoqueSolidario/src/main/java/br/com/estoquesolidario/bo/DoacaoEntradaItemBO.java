package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.DoacaoEntradaItemDAO;
import br.com.estoquesolidario.model.DoacaoEntradaItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoacaoEntradaItemBO implements CRUD<DoacaoEntradaItem, Long> {

    @Autowired
    private DoacaoEntradaItemDAO doacaoEntradaItemDAO;

    @Override
    public DoacaoEntradaItem pesquisaPeloId(Long id) {
        return doacaoEntradaItemDAO.pesquisaPeloId(id);
    }

    @Override
    public List<DoacaoEntradaItem> lista() {
        return doacaoEntradaItemDAO.lista();
    }

    @Override
    public void insere(DoacaoEntradaItem doacaoEntradaItem) {
        doacaoEntradaItemDAO.insere(doacaoEntradaItem);
    }

    @Override
    public void atualiza(DoacaoEntradaItem doacaoEntradaItem) {
        doacaoEntradaItemDAO.atualiza(doacaoEntradaItem);
    }

    @Override
    public void remove(DoacaoEntradaItem doacaoEntradaItem) {
        doacaoEntradaItemDAO.remove(doacaoEntradaItem);
    }

    public boolean itemJaAdicionado(DoacaoEntradaItem doacaoEntradaItem) {
        Long doacaoEntradaId = doacaoEntradaItem.getDoacaoEntrada().getId();
        List<DoacaoEntradaItem> itens = doacaoEntradaItemDAO.listaItensDoacao(doacaoEntradaId);
        Long produtoId = doacaoEntradaItem.getProduto().getId();

        if (doacaoEntradaItem.getId() == null) {
            for (DoacaoEntradaItem item : itens) {
                if (item.getProduto().getId() == produtoId) {
                    return true;
                }
            }
        }else{
            Long doacaoEntradaItemId = doacaoEntradaItem.getId();
            for (DoacaoEntradaItem item : itens) {
                if (item.getProduto().getId() == produtoId && doacaoEntradaItemId != item.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
}