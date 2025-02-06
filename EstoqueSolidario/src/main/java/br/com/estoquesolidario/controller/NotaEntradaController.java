package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.NotaEntradaBO;
import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.NotaEntrada;
import br.com.estoquesolidario.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/nota-entrada")
public class NotaEntradaController {

    @Autowired
    private NotaEntradaBO notaEntradaBO;

    @Autowired
    private UsuarioBO usuarioBO;

    //http://localhost:8080/nota-entrada/novo
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model){
        Long usuarioId = null;
        //model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("notaEntrada",new NotaEntrada());
        model.addAttribute("usuarios", usuarioBO.listaDoadores());// precisa selecionar apenas perfil doador
        return new ModelAndView("/nota-entrada/formulario.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@ModelAttribute NotaEntrada notaEntrada, Model model) {
        notaEntradaBO.insere(notaEntrada);

        if (notaEntrada.getId() == null) {
            notaEntradaBO.insere(notaEntrada);

        }else{
            notaEntradaBO.atualiza(notaEntrada);

        }

        return "redirect:/nota-entrada";
    }


}
