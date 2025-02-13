package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.NotaSaidaDAO;
import br.com.estoquesolidario.model.NotaSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaSaidaBO implements CRUD<NotaSaida, Long> {

    @Autowired
    private NotaSaidaDAO notaSaidaDAO;

    @Override
    public NotaSaida pesquisaPeloId(Long id) {
        return notaSaidaDAO.pesquisaPeloId(id);
    }

    @Override
    public List<NotaSaida> lista() {
        return notaSaidaDAO.lista();
    }

    @Override
    public void insere(NotaSaida notaSaida) {
        notaSaidaDAO.insere(notaSaida);
    }

    @Override
    public void atualiza(NotaSaida notaSaida) {
        notaSaidaDAO.atualiza(notaSaida);
    }

    @Override
    public void remove(NotaSaida notaSaida) {
        notaSaidaDAO.remove(notaSaida);
    }
}
