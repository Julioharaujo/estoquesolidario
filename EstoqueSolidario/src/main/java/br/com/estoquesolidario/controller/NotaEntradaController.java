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
        model.addAttribute("usuarios", usuarioBO.listaDoadores());// precisa selecionar apenas perfil doador
        return new ModelAndView("nota-entrada/formulario.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute NotaEntrada notaEntrada, RedirectAttributes attr) {
        try {
            if (notaEntrada.getId() == null) {
                notaEntradaBO.insere(notaEntrada);
            } else {
                notaEntradaBO.atualiza(notaEntrada);
            }
            attr.addFlashAttribute("feedback", "Nota de entrada salva com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao salvar a nota de entrada: " + e.getMessage());
        }
        return "redirect:/nota-entrada";
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("notas", notaEntradaBO.lista());
        return new ModelAndView("nota-entrada/lista.html", model);
    }

    @RequestMapping(value="/{id}/item", method=RequestMethod.GET)
    public ModelAndView produto(@PathVariable("id") Long id, ModelMap model) {
        NotaEntradaItem nei = new NotaEntradaItem();
        NotaEntrada ne = notaEntradaBO.pesquisaPeloId(id);
        nei.setNotaEntrada(ne);
        model.addAttribute("notaEntradaItem",nei);
        model.addAttribute("produtos", produtoBO.lista());
        return new ModelAndView("nota-entrada-item/formulario",model);
    }

    @RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("notaEntradaItem", new NotaEntradaItem());
        model.addAttribute("usuarios", usuarioBO.listaDoadores());
        model.addAttribute("notaEntrada", notaEntradaBO.pesquisaPeloId(id));
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
