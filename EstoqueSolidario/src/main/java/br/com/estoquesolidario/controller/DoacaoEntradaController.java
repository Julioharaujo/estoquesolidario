package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.DoacaoEntradaBO;
import br.com.estoquesolidario.bo.DoacaoEntradaItemBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.DoacaoEntrada;
import br.com.estoquesolidario.model.DoacaoEntradaItem;
import br.com.estoquesolidario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/doacao-entrada")
public class DoacaoEntradaController {

    @Autowired
    private DoacaoEntradaBO doacaoEntradaBO;

    @Autowired
    private DoacaoEntradaItemBO doacaoEntradaItemBO;

    @Autowired
    private UsuarioBO usuarioBO;

    @Autowired
    private ProdutoBO produtoBO;

    //http://localhost:8080/doacao-entrada/novo
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model){
        DoacaoEntrada doacaoEntrada = new DoacaoEntrada();
        doacaoEntrada.setUsuario(new Usuario());

        model.addAttribute("doacaoEntrada", doacaoEntrada);
        model.addAttribute("doacaoEntradaItem", new DoacaoEntradaItem());
        model.addAttribute("usuarios", usuarioBO.listaDoadores());

        return new ModelAndView("/doacao-entrada/novo.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute DoacaoEntrada doacaoEntrada,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {

        if (doacaoEntrada.getUsuario() == null) {
            result.rejectValue("usuario", "field.required");
        }

        if (doacaoEntrada.getUsuario().getId() == null) {
            result.rejectValue("usuario", "field.required");
        }

        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioBO.listaDoadores());
            return "/doacao-entrada/adicionar-produtos.html";
        }

        if (doacaoEntrada.getId() == null) {
            doacaoEntradaBO.insere(doacaoEntrada);
            attr.addFlashAttribute("feedback", "A doação de entrada foi cadastrada com sucesso!");
        } else {
            doacaoEntradaBO.atualiza(doacaoEntrada);
            attr.addFlashAttribute("feedback", "Os dados da doacao de entrada foram atualizados com sucesso!");
        }

        return "redirect:/doacao-entrada";
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("doacoes", doacaoEntradaBO.lista());
        return new ModelAndView("/doacao-entrada/lista.html", model);
    }

    @RequestMapping(value="/{id}/item", method=RequestMethod.GET)
    public ModelAndView produto(@PathVariable("id") Long id, ModelMap model) {
        DoacaoEntradaItem nei = new DoacaoEntradaItem();
        DoacaoEntrada ne = doacaoEntradaBO.pesquisaPeloId(id);
        nei.setDoacaoEntrada(ne);
        model.addAttribute("doacaoEntradaItem",nei);
        model.addAttribute("produtos", produtoBO.lista());
        return new ModelAndView("/doacao-entrada-item/produtos-itens",model);
    }

    @RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        DoacaoEntrada doacaoEntrada = doacaoEntradaBO.pesquisaPeloId(id);

        if (doacaoEntrada == null) {
            return new ModelAndView("redirect:/doacao-entrada");
        }

        DoacaoEntradaItem doacaoEntradaItem = new DoacaoEntradaItem();
        doacaoEntradaItem.setDoacaoEntrada(doacaoEntrada);

        model.addAttribute("doacaoEntrada", doacaoEntrada);
        model.addAttribute("doacaoEntradaItem", doacaoEntradaItem);
        model.addAttribute("usuarios", usuarioBO.lista());
        model.addAttribute("produtos", produtoBO.lista());

        return new ModelAndView("doacao-entrada/adicionar-produtos", model);
    }

    @RequestMapping(value="remove/{id}", method=RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
        DoacaoEntrada ne = doacaoEntradaBO.pesquisaPeloId(id);
        doacaoEntradaBO.remove(ne);
        attr.addAttribute("feedback", "Doacao de entrada removida com sucesso");
        return  "redirect:/doacao-entrada";
    }
}