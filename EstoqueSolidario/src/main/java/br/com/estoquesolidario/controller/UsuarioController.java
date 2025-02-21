package br.com.estoquesolidario.controller;

import br.com.estoquesolidario.bo.UsuarioBO;
import br.com.estoquesolidario.model.NotaEntrada;
import br.com.estoquesolidario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/usuarios")
//http://localhost:8080/usuarios
public class UsuarioController {

    @Autowired
    private UsuarioBO usuarioBO;

    //http://localhost:8080/usuarios/novo
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("usuario", new Usuario());
        return new ModelAndView("/usuario/formulario.html", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public String salva(@ModelAttribute Usuario usuario, Model model) {
        usuarioBO.insere(usuario);
        return "redirect:/usuarios";
        //inserir metodo usuarioBO.atualisa();
    }

    //http://localhost:8080/usuarios
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("usuarios", usuarioBO.lista());
        return new ModelAndView("/usuario/lista", model);
    }

    //http://localhost:8080/usuarios/edita/id
    @RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        try {
            model.addAttribute("usuario", usuarioBO.pesquisaPeloId(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/usuario/formulario", model);
    }


    @RequestMapping(value = "/inativa/{id}", method = RequestMethod.GET)
    public String inativa(@PathVariable("id") Long id, RedirectAttributes attr) {
        System.out.println(id);
        try {
            Usuario usuario = usuarioBO.pesquisaPeloId(id);
            usuarioBO.inativa(usuario);
            attr.addFlashAttribute("feedback", "Usuario foi inativado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/usuarios";
    }

    @RequestMapping(value = "/ativa/{id}", method = RequestMethod.GET)
    public String ativa(@PathVariable("id") Long id, RedirectAttributes attr) {
        System.out.println(id);
        try {
            Usuario usuario = usuarioBO.pesquisaPeloId(id);
            usuarioBO.ativa(usuario);
            attr.addFlashAttribute("feedback", "Usuario foi ativado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/usuarios";
    }

    @RequestMapping(value="remove/{id}", method=RequestMethod.GET)
    public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
        Usuario usuario = usuarioBO.pesquisaPeloId(id);
        usuarioBO.remove(usuario);
        attr.addAttribute("feedback", "Usuário removido com sucesso");
        return  "redirect:/usuarios";
    }
}