package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.DoacaoEntradaDAO;
import br.com.estoquesolidario.model.DoacaoEntrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoacaoEntradaBO implements CRUD<DoacaoEntrada, Long> {

    @Autowired
    private DoacaoEntradaDAO doacaoEntradaDAO;

    @Override
    public DoacaoEntrada pesquisaPeloId(Long id) {
        return doacaoEntradaDAO.pesquisaPeloId(id);
    }

    @Override
    public List<DoacaoEntrada> lista() {
        return doacaoEntradaDAO.lista();
    }

    @Override
    public void insere(DoacaoEntrada doacaoEntrada) {
        doacaoEntradaDAO.insere(doacaoEntrada);
    }

    @Override
    public void atualiza(DoacaoEntrada doacaoEntrada) {
        doacaoEntradaDAO.atualiza(doacaoEntrada);
    }

    @Override
    public void remove(DoacaoEntrada doacaoEntrada) {
        doacaoEntradaDAO.remove(doacaoEntrada);
    }
}
