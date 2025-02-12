package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.NotaEntradaBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.NotaEntrada;
import br.com.estoquesolidario.model.NotaEntradaItem;
import br.com.estoquesolidario.model.Produto;
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

@Controller
@RequestMapping("/nota-entrada")
public class NotaEntradaController {

    @Autowired
    private NotaEntradaBO notaEntradaBO;

    @Autowired
    private UsuarioBO usuarioBO;

    @Autowired
    private ProdutoBO produtoBO;

    //http://localhost:8080/nota-entrada/novo
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model){
        Long usuarioId = null;
        //model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("notaEntrada",new NotaEntrada());
        model.addAttribute("usuarios", usuarioBO.listaDoadores());
        return new ModelAndView("/nota-entrada/formulario.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute NotaEntrada notaEntrada,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {
        if (notaEntrada.getUsuario().getId() == null) {
            result.rejectValue("usuario", "field.required");
        }

        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioBO.listaDoadores());
            return "/nota-entrada/formulario.html";
        }
        if (notaEntrada.getId() == null) {
            notaEntradaBO.insere(notaEntrada);
            attr.addFlashAttribute("feedback", "A nota de entrada foi cadastrada com sucesso!");
        } else {
            notaEntradaBO.atualiza(notaEntrada);
            attr.addFlashAttribute("feedback", "Os dados da nota de entrada foram atualizados com sucesso!");
        }

        return "redirect:/nota-entrada";
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("notas", notaEntradaBO.lista());
        return new ModelAndView("/nota-entrada/lista.html", model);
    }

    @RequestMapping(value="/{id}/item", method=RequestMethod.GET)
    public ModelAndView produto(@PathVariable("id") Long id, ModelMap model) {
        NotaEntradaItem nei = new NotaEntradaItem();
        NotaEntrada ne = notaEntradaBO.pesquisaPeloId(id);
        nei.setNotaEntrada(ne);
        model.addAttribute("notaEntradaItem",nei);
        model.addAttribute("produtos", produtoBO.lista());
        return new ModelAndView("/nota-entrada-item/formulario",model);
    }

    @RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {

        model.addAttribute("notaEntradaItem", new NotaEntradaItem());
        model.addAttribute("usuarios", usuarioBO.lista());//listaDoadores()
        model.addAttribute("notaEntrada", notaEntradaBO.pesquisaPeloId(id));
        System.out.println("NotaEntrada: " + id);

        return new ModelAndView("nota-entrada/formulario", model);
    }

    @RequestMapping(value="remove/{id}", method=RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
        NotaEntrada ne = notaEntradaBO.pesquisaPeloId(id);
        notaEntradaBO.remove(ne);
        attr.addAttribute("feedback", "Nota de entrada removida com sucesso");
        return  "redirect:/nota-entrada";
    }


}
