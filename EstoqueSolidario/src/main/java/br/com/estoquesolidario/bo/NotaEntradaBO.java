package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.NotaEntradaDAO;
import br.com.estoquesolidario.model.NotaEntrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaEntradaBO implements CRUD<NotaEntrada, Long> {

    @Autowired
    private NotaEntradaDAO notaEntradaDAO;

    @Override
    public NotaEntrada pesquisaPeloId(Long id) {
        return notaEntradaDAO.pesquisaPeloId(id);
    }

    @Override
    public List<NotaEntrada> lista() {
        return notaEntradaDAO.lista();
    }

    @Override
    public void insere(NotaEntrada notaEntrada) {
        notaEntradaDAO.insere(notaEntrada);
    }

    @Override
    public void atualiza(NotaEntrada notaEntrada) {
        notaEntradaDAO.atualiza(notaEntrada);
    }

    @Override
    public void remove(NotaEntrada notaEntrada) {
        notaEntradaDAO.remove(notaEntrada);
    }
}
