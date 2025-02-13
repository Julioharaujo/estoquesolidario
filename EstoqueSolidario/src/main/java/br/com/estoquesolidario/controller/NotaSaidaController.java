package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.NotaSaidaBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.NotaSaida;
import br.com.estoquesolidario.model.NotaSaidaItem;
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
@RequestMapping("/nota-saida")
public class NotaSaidaController {

    @Autowired
    private NotaSaidaBO notaSaidaBO;

    @Autowired
    private UsuarioBO usuarioBO;

    @Autowired
    private ProdutoBO produtoBO;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model){
        model.addAttribute("notaSaida", new NotaSaida());
        model.addAttribute("usuarios", usuarioBO.listaFavorecidos());
        return new ModelAndView("/nota-saida/formulario.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute NotaSaida notaSaida,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {
        if (notaSaida.getUsuario().getId() == null) {
            result.rejectValue("usuario", "field.required");
        }

        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioBO.listaFavorecidos());
            return "/nota-saida/formulario.html";
        }
        if (notaSaida.getId() == null) {
            notaSaidaBO.insere(notaSaida);
            attr.addFlashAttribute("feedback", "A nota de saída foi cadastrada com sucesso!");
        } else {
            notaSaidaBO.atualiza(notaSaida);
            attr.addFlashAttribute("feedback", "Os dados da nota de saída foram atualizados com sucesso!");
        }
        return "redirect:/nota-saida";
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("notas", notaSaidaBO.lista());
        return new ModelAndView("/nota-saida/lista.html", model);
    }

    @RequestMapping(value="/{id}/item", method=RequestMethod.GET)
    public ModelAndView produto(@PathVariable("id") Long id, ModelMap model) {
        NotaSaidaItem nsi = new NotaSaidaItem();
        NotaSaida ns = notaSaidaBO.pesquisaPeloId(id);
        nsi.setNotaSaida(ns);
        model.addAttribute("notaSaidaItem", nsi);
        model.addAttribute("produtos", produtoBO.lista());
        return new ModelAndView("/nota-saida-item/formulario", model);
    }

    @RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("notaSaidaItem", new NotaSaidaItem());
        model.addAttribute("usuarios", usuarioBO.lista());
        model.addAttribute("notaSaida", notaSaidaBO.pesquisaPeloId(id));
        return new ModelAndView("nota-saida/formulario", model);
    }

    @RequestMapping(value="remove/{id}", method=RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
        NotaSaida ns = notaSaidaBO.pesquisaPeloId(id);
        notaSaidaBO.remove(ns);
        attr.addAttribute("feedback", "Nota de saída removida com sucesso");
        return "redirect:/nota-saida";
    }
}