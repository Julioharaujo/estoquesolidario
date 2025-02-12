package br.com.estoquesolidario.api;

import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.bo.ProdutoEstoqueBO;
import br.com.estoquesolidario.model.Produto;
import br.com.estoquesolidario.model.ProdutoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProdutoEstoqueRestController {

    @Autowired
    private ProdutoEstoqueBO produtoEstoqueBO;

    @Autowired
    private ProdutoBO produtoBO;

    @RequestMapping(value = "/api/estoque",method = RequestMethod.GET)
    public List<ProdutoEstoque> lista(){
        return produtoEstoqueBO.lista();
    }

    @RequestMapping(value = "/estoque/{id}",method = RequestMethod.GET)
    public ProdutoEstoque pesquisaPeloId(@PathVariable Long id){
        return produtoEstoqueBO.pesquisaPeloId(id);
    }

    @RequestMapping(value = "/estoque",method = RequestMethod.POST)
    public ProdutoEstoque insere(@RequestBody ProdutoEstoque produtoEstoque){
        Produto produto = produtoBO.pesquisaPeloId(produtoEstoque.getProduto().getId());
        produtoEstoque.setProduto(produto);
        produtoEstoqueBO.insere(produtoEstoque);
        return produtoEstoque;
    }

    @RequestMapping(value = "/estoque/{id}",method = RequestMethod.PUT)
    public ProdutoEstoque atualiza(@PathVariable Long id,
                                   @RequestBody ProdutoEstoque produtoEstoque){
        produtoEstoque.setId(id);
        produtoEstoque.setProduto(produtoBO.pesquisaPeloId(produtoEstoque.getProduto().getId()));
        produtoEstoqueBO.atualiza(produtoEstoque);
        return produtoEstoque;
    }

    @RequestMapping(value = "/estoque/{id}",method = RequestMethod.DELETE)
    public ProdutoEstoque remove(@PathVariable Long id){
        ProdutoEstoque produtoEstoque = produtoEstoqueBO.pesquisaPeloId(id);
        produtoEstoqueBO.remove(produtoEstoque);
        return produtoEstoque;
    }
}
