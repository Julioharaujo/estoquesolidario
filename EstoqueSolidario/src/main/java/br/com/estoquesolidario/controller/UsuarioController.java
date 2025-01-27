package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "usuarios", method = RequestMethod.POST)
    public String salva(@ModelAttribute Usuario usuario, Model model) {
        bo.insere(usuario);
        return "/usuario/formulario.html";
    }

    //resolver um erro aqui. aula 5 t3
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("usuarios", bo.lista());
        return new ModelAndView("usuario/lista", model);
    }
}
