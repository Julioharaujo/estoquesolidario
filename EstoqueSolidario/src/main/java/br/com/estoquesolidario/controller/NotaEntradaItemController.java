package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.NotaEntradaBO;
import br.com.estoquesolidario.bo.NotaEntradaItemBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.model.NotaEntrada;
import br.com.estoquesolidario.model.NotaEntradaItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
                        RedirectAttributes redirect,
                        Model model) {

        Long produtoId = notaEntradaItem.getProduto().getId();

        // validação do produto

        //validação duplicidade

        if (result.hasErrors()){
            model.addAttribute("produtos", produtoBO.lista());
            return  "/nota-entrada-item/formulario";
        }

        NotaEntrada notaEntrada = notaEntradaBO.pesquisaPeloId(notaEntradaItem.getNotaEntrada().getId());
        notaEntradaItem.setNotaEntrada(notaEntrada);

        if(notaEntradaItem.getId() == null){
            notaEntradaItemBO.insere(notaEntradaItem);
        }else{
            notaEntradaItemBO.atualiza(notaEntradaItem);
        }
        Long notaEntradaId = notaEntradaItem.getNotaEntrada().getId();
        return "redirect:/nota-entrada/edita" + notaEntradaId;
    }

}
