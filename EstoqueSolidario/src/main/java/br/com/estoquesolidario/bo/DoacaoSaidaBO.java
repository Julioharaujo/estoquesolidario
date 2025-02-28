package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.DoacaoSaidaDAO;
import br.com.estoquesolidario.model.DoacaoSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoacaoSaidaBO implements CRUD<DoacaoSaida, Long> {

    @Autowired
    private DoacaoSaidaDAO doacaoSaidaDAO;

    @Override
    public DoacaoSaida pesquisaPeloId(Long id) {
        return doacaoSaidaDAO.pesquisaPeloId(id);
    }

    @Override
    public List<DoacaoSaida> lista() {
        return doacaoSaidaDAO.lista();
    }

    @Override
    public void insere(DoacaoSaida doacaoSaida) {
        doacaoSaidaDAO.insere(doacaoSaida);
    }

    @Override
    public void atualiza(DoacaoSaida doacaoSaida) {
        doacaoSaidaDAO.atualiza(doacaoSaida);
    }

    @Override
    public void remove(DoacaoSaida doacaoSaida) {
        doacaoSaidaDAO.remove(doacaoSaida);
    }
}
