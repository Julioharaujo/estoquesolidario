package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.NotaEntradaBO;
import br.com.estoquesolidario.bo.NotaEntradaItemBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.model.NotaEntrada;
import br.com.estoquesolidario.model.NotaEntradaItem;
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
@RequestMapping("/nota-entrada-item")
public class NotaEntradaItemController {

    @Autowired
    private ProdutoBO produtoBO;

    @Autowired
    private NotaEntradaBO notaEntradaBO;

    @Autowired
    private NotaEntradaItemBO notaEntradaItemBO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute NotaEntradaItem notaEntradaItem,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {

        if (notaEntradaItem.getProduto() == null || notaEntradaItem.getProduto().getId() == null) {
            result.rejectValue("produto", "field.required");
        }

        if (notaEntradaItem.getNotaEntrada() == null || notaEntradaItem.getNotaEntrada().getId() == null) {
            result.rejectValue("notaEntrada", "field.required", "A nota de entrada é obrigatória.");
        }

        if (notaEntradaItemBO.itemJaAdicionado(notaEntradaItem)) {
            result.rejectValue("produto", "duplicate");
        }

        if (result.hasErrors()) {
            model.addAttribute("notaEntradaItem", notaEntradaItem);
            model.addAttribute("produtos", produtoBO.lista());
            return "/nota-entrada-item/produtos-itens";
        }

        NotaEntrada notaEntrada = notaEntradaBO.pesquisaPeloId(notaEntradaItem.getNotaEntrada().getId());

        if (notaEntrada == null) {
            attr.addFlashAttribute("erro", "Nota de entrada não encontrada.");
            return "redirect:/nota-entrada";
        }

        notaEntradaItem.setNotaEntrada(notaEntrada);
        notaEntrada.getItens().add(notaEntradaItem);

        if (notaEntradaItem.getId() == null) {
            notaEntradaItemBO.insere(notaEntradaItem);
            attr.addFlashAttribute("feedback", "Produto adicionado com sucesso");
        } else {
            notaEntradaItemBO.atualiza(notaEntradaItem);
            attr.addFlashAttribute("feedback", "Produto atualizado com sucesso");
        }

        return "redirect:/nota-entrada/edita/" + notaEntrada.getId();
    }


    @RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        NotaEntradaItem notaEntradaItem = notaEntradaItemBO.pesquisaPeloId(id);

        if (notaEntradaItem == null) {
            notaEntradaItem = new NotaEntradaItem();
            notaEntradaItem.setNotaEntrada(new NotaEntrada()); // Evita null
        } else if (notaEntradaItem.getNotaEntrada() == null) {
            notaEntradaItem.setNotaEntrada(new NotaEntrada());
        }

        // Depuração: Exibir no console
        System.out.println("NotaEntradaItem: " + notaEntradaItem);

        model.addAttribute("notaEntradaItem", notaEntradaItem);
        model.addAttribute("produtos", produtoBO.lista());

        return new ModelAndView("nota-entrada-item/produtos-itens", model);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr){
        NotaEntradaItem notaEntradaItem = notaEntradaItemBO.pesquisaPeloId(id);
        Long notaId = (notaEntradaItem != null && notaEntradaItem.getNotaEntrada() != null) ?
                notaEntradaItem.getNotaEntrada().getId() : 0L;

        if (notaEntradaItem != null) {
            notaEntradaItemBO.remove(notaEntradaItem);
            attr.addAttribute("feedback","Item removido com sucesso");
        }

        return "redirect:/nota-entrada/edita/" + notaId;
    }

}
