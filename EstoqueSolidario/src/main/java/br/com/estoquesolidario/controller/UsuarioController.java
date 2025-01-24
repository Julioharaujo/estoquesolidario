package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioBO bo;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("usuario", new Usuario());
        return new ModelAndView("/usuario/formulario.html", model);
    }

    //esta dando erro: aula 5 tema 2//
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String salva(@ModelAttribute Usuario usuario) {
        bo.insere(usuario);
        //return "redirect:/usuarios/novo";
        return "redirect:/usuario/formulario.html";
    }
}
