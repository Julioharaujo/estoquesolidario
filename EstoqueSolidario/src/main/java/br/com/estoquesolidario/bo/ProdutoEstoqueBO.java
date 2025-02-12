package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.ProdutoEstoqueDAO;
import br.com.estoquesolidario.model.ProdutoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoEstoqueBO implements CRUD<ProdutoEstoque, Long> {

    @Autowired
    private ProdutoEstoqueDAO produtoEstoqueDAO;

    @Override
    public ProdutoEstoque pesquisaPeloId(Long id) {
        return produtoEstoqueDAO.pesquisaPeloId(id);
    }

    @Override
    public List<ProdutoEstoque> lista() {
        return produtoEstoqueDAO.lista();
    }

    @Override
    public void insere(ProdutoEstoque produtoEstoque) {
        produtoEstoqueDAO.insere(produtoEstoque);
    }

    @Override
    public void atualiza(ProdutoEstoque produtoEstoque) {
        produtoEstoqueDAO.atualiza(produtoEstoque);
    }

    @Override
    public void remove(ProdutoEstoque produtoEstoque) {
        produtoEstoqueDAO.remove(produtoEstoque);
    }
}
