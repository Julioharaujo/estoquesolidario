package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.DoacaoEntradaBO;
import br.com.estoquesolidario.bo.DoacaoEntradaItemBO;
import br.com.estoquesolidario.bo.ProdutoBO;
import br.com.estoquesolidario.model.DoacaoEntrada;
import br.com.estoquesolidario.model.DoacaoEntradaItem;
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
@RequestMapping("/doacao-entrada-item")
public class DoacaoEntradaItemController {

    @Autowired
    private ProdutoBO produtoBO;

    @Autowired
    private DoacaoEntradaBO doacaoEntradaBO;

    @Autowired
    private DoacaoEntradaItemBO doacaoEntradaItemBO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute DoacaoEntradaItem doacaoEntradaItem,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {

        Long produtoId = doacaoEntradaItem.getProduto().getId();
        if (produtoId == null) {
            result.rejectValue("produto","field.required");
        }

        if (doacaoEntradaItemBO.itemJaAdicionado(doacaoEntradaItem)){
            result.rejectValue("produto","duplicate");
        }

        if (result.hasErrors()) {
            StringBuilder mensagemErro = new StringBuilder("Erro ao adicionar o produto: ");

            result.getAllErrors().forEach(error -> {
                mensagemErro.append(error.getDefaultMessage()).append(" ");
            });

            attr.addFlashAttribute("erro", mensagemErro.toString().trim());

            Long doacaoId = doacaoEntradaItem.getDoacaoEntrada().getId();
            return "redirect:doacao-entrada/edita/" + doacaoId;
        }

        DoacaoEntrada doacaoEntrada = doacaoEntradaBO.pesquisaPeloId(doacaoEntradaItem.getDoacaoEntrada().getId());
        doacaoEntradaItem.setDoacaoEntrada(doacaoEntrada);

        if(doacaoEntradaItem.getId() == null){
            doacaoEntradaItemBO.insere(doacaoEntradaItem);
            attr.addFlashAttribute("feedback","Produto adicionado com sucesso");
        }else{
            doacaoEntradaItemBO.atualiza(doacaoEntradaItem);
            attr.addFlashAttribute("feedback","Produto atualizado com sucesso");
        }
        Long doacaoEntradaId = doacaoEntradaItem.getDoacaoEntrada().getId();
        return "redirect:doacao-entrada/edita/" + doacaoEntradaId;

    }

    @RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model ){
        model.addAttribute("doacaoEntradaItem",doacaoEntradaItemBO.pesquisaPeloId(id));
        model.addAttribute("produtos",produtoBO.lista());
        return new ModelAndView("doacao-entrada-item/produtos-itens",model);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr){
        Long doacaoId = 0L;
        DoacaoEntradaItem doacaoEntradaItem = doacaoEntradaItemBO.pesquisaPeloId(id);
        doacaoId = doacaoEntradaItem.getDoacaoEntrada().getId();
        doacaoEntradaItemBO.remove(doacaoEntradaItem);
        attr.addAttribute("feedback","Item removido com sucesso");
        return "redirect:doacao-entrada/edita/" + doacaoId;
    }
}
