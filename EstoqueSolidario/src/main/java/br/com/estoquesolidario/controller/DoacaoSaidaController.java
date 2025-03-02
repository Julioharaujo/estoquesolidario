package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.DoacaoSaidaBO;
import br.com.estoquesolidario.bo.DoacaoSaidaItemBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.DoacaoSaida;
import br.com.estoquesolidario.model.DoacaoSaidaItem;
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
@RequestMapping("/doacao-saida")
public class DoacaoSaidaController {

    @Autowired
    private DoacaoSaidaBO doacaoSaidaBO;

    @Autowired
    private DoacaoSaidaItemBO doacaoSaidaItemBO;

    @Autowired
    private UsuarioBO usuarioBO;

    @Autowired
    private ProdutoBO produtoBO;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model){
        DoacaoSaida doacaoSaida = new DoacaoSaida();
        doacaoSaida.setUsuario(new Usuario());

        model.addAttribute("doacaoSaida", doacaoSaida);
        model.addAttribute("doacaoSaidaItem", new DoacaoSaidaItem());
        model.addAttribute("usuarios", usuarioBO.listaDonatarios());

        return new ModelAndView("/doacao-saida/novo.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute DoacaoSaida doacaoSaida,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {

        if (doacaoSaida.getUsuario() == null) {
            result.rejectValue("usuario", "field.required");
        }

        if (doacaoSaida.getUsuario().getId() == null) {
            result.rejectValue("usuario", "field.required");
        }

        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioBO.listaDonatarios());
            return "/doacao-saida/adicionar-produtos.html";
        }
        if (doacaoSaida.getId() == null) {
            doacaoSaidaBO.insere(doacaoSaida);
            attr.addFlashAttribute("feedback", "A doacao de saída foi cadastrada com sucesso!");
        } else {
            doacaoSaidaBO.atualiza(doacaoSaida);
            attr.addFlashAttribute("feedback", "Os dados da doacao de saída foram atualizados com sucesso!");
        }
        return "redirect:/doacao-saida";
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("doacoes", doacaoSaidaBO.lista());
        return new ModelAndView("/doacao-saida/lista.html", model);
    }

    @RequestMapping(value="/{id}/item", method=RequestMethod.GET)
    public ModelAndView produto(@PathVariable("id") Long id, ModelMap model) {
        DoacaoSaidaItem nsi = new DoacaoSaidaItem();
        DoacaoSaida ns = doacaoSaidaBO.pesquisaPeloId(id);
        nsi.setDoacaoSaida(ns);
        model.addAttribute("doacaoSaidaItem", nsi);
        model.addAttribute("produtos", produtoBO.lista());
        return new ModelAndView("/doacao-saida-item/produtos-itens", model);
    }

    @RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        DoacaoSaida doacaoSaida = doacaoSaidaBO.pesquisaPeloId(id);

        if (doacaoSaida == null) {
            return new ModelAndView("redirect:/doacao-saida");
        }

        DoacaoSaidaItem doacaoSaidaItem = new DoacaoSaidaItem();
        doacaoSaidaItem.setDoacaoSaida(doacaoSaida);

        model.addAttribute("doacaoSaida", doacaoSaida);
        model.addAttribute("doacaoSaidaItem", doacaoSaidaItem);
        model.addAttribute("usuarios", usuarioBO.lista());
        model.addAttribute("produtos", produtoBO.lista());
        //model.addAttribute("doacaoSaida", doacaoSaidaBO.pesquisaPeloId(id));

        return new ModelAndView("doacao-saida/adicionar-produtos", model);
    }

    @RequestMapping(value="remove/{id}", method=RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
        DoacaoSaida ns = doacaoSaidaBO.pesquisaPeloId(id);
        doacaoSaidaBO.remove(ns);
        attr.addAttribute("feedback", "Doacao de saída removida com sucesso");
        return "redirect:/doacao-saida";
    }
}