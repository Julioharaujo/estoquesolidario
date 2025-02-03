package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.ProdutoDAO;
import br.com.estoquesolidario.model.Produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class ProdutoBO implements CRUD<Produto, Long> {

    @Autowired
    private ProdutoDAO dao;

    @Override
    public Produto pesquisaPeloId(Long id) {
        return dao.pesquisaPeloId(id);
    }

    @Override
    public List<Produto> lista() {
        return dao.lista();
    }

    @Override
    public void insere(Produto produto) {
        dao.insere(produto);
    }

    @Override
    public void atualiza(Produto produto) {
        dao.atualiza(produto);
    }

    @Override
    public void remove(Produto produto) {
        dao.remove(produto);
    }
}