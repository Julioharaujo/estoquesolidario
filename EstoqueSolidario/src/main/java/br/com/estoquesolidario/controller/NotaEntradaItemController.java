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

        Long produtoId = notaEntradaItem.getProduto().getId();
        if (produtoId == null) {
            result.rejectValue("produto","field.required");
        }

        if (notaEntradaItemBO.itemJaAdicionado(notaEntradaItem)){
            result.rejectValue("produto","duplicate");
        }

        if (result.hasErrors()){
            model.addAttribute("produtos", produtoBO.lista());
            return  "/nota-entrada-item/formulario";
        }

        NotaEntrada notaEntrada = notaEntradaBO.pesquisaPeloId(notaEntradaItem.getNotaEntrada().getId());
        notaEntradaItem.setNotaEntrada(notaEntrada);

        if(notaEntradaItem.getId() == null){
            notaEntradaItemBO.insere(notaEntradaItem);
            attr.addFlashAttribute("feedback","Produto adicionado com sucesso");
        }else{
            notaEntradaItemBO.atualiza(notaEntradaItem);
            attr.addFlashAttribute("feedback","Produto atualizado com sucesso");
        }
        Long notaEntradaId = notaEntradaItem.getNotaEntrada().getId();
        return "redirect:nota-entrada/edita/" + notaEntradaId;

    }

    @RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model ){
        model.addAttribute("notaEntradaItem",notaEntradaItemBO.pesquisaPeloId(id));
        model.addAttribute("produtos",produtoBO.lista());
        return new ModelAndView("nota-entrada-item/formulario",model);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr){
        Long notaId = 0L;
        NotaEntradaItem notaEntradaItem = notaEntradaItemBO.pesquisaPeloId(id);
        notaId = notaEntradaItem.getNotaEntrada().getId();
        notaEntradaItemBO.remove(notaEntradaItem);
        attr.addAttribute("feedback","Item removido com sucesso");
        return "redirect:nota-entrada/edita/" + notaId;
    }
}
