package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.DoacaoSaidaBO;
import br.com.estoquesolidario.bo.DoacaoSaidaItemBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.model.DoacaoSaida;
import br.com.estoquesolidario.model.DoacaoSaidaItem;
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
@RequestMapping("/doacao-saida-item")
public class DoacaoSaidaItemController {

    @Autowired
    private ProdutoBO produtoBO;

    @Autowired
    private DoacaoSaidaBO doacaoSaidaBO;

    @Autowired
    private DoacaoSaidaItemBO doacaoSaidaItemBO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute DoacaoSaidaItem doacaoSaidaItem,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {

        Long produtoId = doacaoSaidaItem.getProduto().getId();
        if (produtoId == null) {
            result.rejectValue("produto","field.required");
        }

        if (doacaoSaidaItemBO.itemJaAdicionado(doacaoSaidaItem)){
            result.rejectValue("produto","duplicate");
        }

        if (result.hasErrors()){
            model.addAttribute("produtos", produtoBO.lista());
            return  "/doacao-saida-item/formulario";
        }

        DoacaoSaida doacaoSaida = doacaoSaidaBO.pesquisaPeloId(doacaoSaidaItem.getDoacaoSaida().getId());
        doacaoSaidaItem.setDoacaoSaida(doacaoSaida);

        if(doacaoSaidaItem.getId() == null){
            doacaoSaidaItemBO.insere(doacaoSaidaItem);
            attr.addFlashAttribute("feedback","Produto adicionado com sucesso");
        }else{
            doacaoSaidaItemBO.atualiza(doacaoSaidaItem);
            attr.addFlashAttribute("feedback","Produto atualizado com sucesso");
        }
        Long doacaoSaidaId = doacaoSaidaItem.getDoacaoSaida().getId();
        return "redirect:doacao-saida/edita/" + doacaoSaidaId;

    }

    @RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model ){
        model.addAttribute("doacaoSaidaItem",doacaoSaidaItemBO.pesquisaPeloId(id));
        model.addAttribute("produtos",produtoBO.lista());
        return new ModelAndView("doacao-saida-item/formulario",model);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr){
        Long doacaoId = 0L;
        DoacaoSaidaItem doacaoSaidaItem = doacaoSaidaItemBO.pesquisaPeloId(id);
        doacaoId = doacaoSaidaItem.getDoacaoSaida().getId();
        doacaoSaidaItemBO.remove(doacaoSaidaItem);
        attr.addAttribute("feedback","Item removido com sucesso");
        return "redirect:doacao-saida/edita/" + doacaoId;
    }

}
