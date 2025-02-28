package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.DoacaoSaidaItemDAO;
import br.com.estoquesolidario.model.DoacaoSaidaItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoacaoSaidaItemBO implements CRUD<DoacaoSaidaItem, Long> {

    @Autowired
    private DoacaoSaidaItemDAO doacaoSaidaItemDAO;

    @Override
    public DoacaoSaidaItem pesquisaPeloId(Long id) {
        return doacaoSaidaItemDAO.pesquisaPeloId(id);
    }

    @Override
    public List<DoacaoSaidaItem> lista() {
        return doacaoSaidaItemDAO.lista();
    }

    @Override
    public void insere(DoacaoSaidaItem doacaoSaidaItem) {
        doacaoSaidaItemDAO.insere(doacaoSaidaItem);
    }

    @Override
    public void atualiza(DoacaoSaidaItem doacaoSaidaItem) {
        doacaoSaidaItemDAO.atualiza(doacaoSaidaItem);
    }

    @Override
    public void remove(DoacaoSaidaItem doacaoSaidaItem) {
        doacaoSaidaItemDAO.remove(doacaoSaidaItem);
    }

    public boolean itemJaAdicionado(DoacaoSaidaItem doacaoSaidaItem) {
        Long doacaoSaidaId = doacaoSaidaItem.getDoacaoSaida().getId();
        List<DoacaoSaidaItem> itens = doacaoSaidaItemDAO.listaItensDoacao(doacaoSaidaId);
        Long produtoId = doacaoSaidaItem.getProduto().getId();

        if (doacaoSaidaItem.getId() == null) {
            for (DoacaoSaidaItem item : itens) {
                if (item.getProduto().getId() == produtoId) {
                    return true;
                }
            }
        } else {
            Long doacaoSaidaItemId = doacaoSaidaItem.getId();
            for (DoacaoSaidaItem item : itens) {
                if (item.getProduto().getId() == produtoId && doacaoSaidaItemId != item.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

}
