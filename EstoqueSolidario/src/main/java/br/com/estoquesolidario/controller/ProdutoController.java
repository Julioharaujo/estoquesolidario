package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.model.Categoria;
import br.com.estoquesolidario.model.Produto;
import br.com.estoquesolidario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequestMapping("/produtos")
//http://localhost:8080/produtos
public class ProdutoController {

    @Autowired
    private ProdutoBO produtoBO;

    //http://localhost:8080/produtos/novo
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", Arrays.asList(Categoria.values()));
        return new ModelAndView("produto/formulario.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute Produto produto, Model model) {
        produtoBO.insere(produto);
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
}
