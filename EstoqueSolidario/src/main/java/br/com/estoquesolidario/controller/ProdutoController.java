package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.bo.ProdutoEstoqueBO;
import br.com.estoquesolidario.dao.ProdutoEstoqueDAO;
import br.com.estoquesolidario.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping("/produtos")
//http://localhost:8080/produtos
public class ProdutoController {

    @Autowired
    private ProdutoBO produtoBO;

    @Autowired
    private ProdutoEstoqueDAO produtoEstoqueDAO;

    @Autowired
    private ProdutoEstoqueBO produtoEstoqueBO;

    //http://localhost:8080/produtos/novo
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", Arrays.asList(Categoria.values()));
        return new ModelAndView("produto/formulario.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@Valid @ModelAttribute Produto produto,
                        BindingResult result,
                        RedirectAttributes attr,
                        Model model) {
        if (result.hasErrors()) {
            return "produto/formulario";
        }

        if (produto.getId() == null) {
            produtoBO.insere(produto); // Insere um novo produto
            attr.addFlashAttribute("feedback", "O Produto foi cadastrado com sucesso");
        } else {
            produtoBO.atualiza(produto); // Atualiza um produto existente
            attr.addFlashAttribute("feedback", "O Produto foi atualizado com sucesso");
        }
        return "redirect:/produtos";
    }

    //http://localhost:8080/produtos
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("produtos", produtoBO.lista());
        return new ModelAndView("produto/lista", model);
    }

    //http://localhost:8080/produtos/edita/id
    @RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        try {
            model.addAttribute("produto", produtoBO.pesquisaPeloId(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/produto/formulario", model);
    }

    //@Transactional
    @RequestMapping(value="remove/{id}", method=RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {

        Produto produto = produtoBO.pesquisaPeloId(id);

        ProdutoEstoque produtoEstoque = produtoEstoqueDAO.buscaPorProdutoId(id);

        if(produtoEstoque == null) {

            attr.addFlashAttribute("feedbackErro", "Produto não encontrado no estoque.");

            return "redirect:/produto/lista";

        } else {
            if (produtoEstoque.getQuantidade() > 0) {

                attr.addFlashAttribute("feedbackErro", "Não é possível remover o produto pois ele possui estoque.");
                return "redirect:/produtos";

            } else {

                produtoEstoqueBO.remove(produtoEstoque);

                if (produtoEstoque == null) {

                    produtoBO.remove(produto);
                    attr.addFlashAttribute("feedback", "Produto removido com sucesso");
                }

                return "redirect:/produtos";
            }
        }
    }
}
