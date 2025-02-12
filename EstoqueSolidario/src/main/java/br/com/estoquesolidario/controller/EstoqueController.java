package br.com.estoquesolidario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EstoqueController {

    @RequestMapping(value = "/estoque", method = RequestMethod.GET)
    public String estoque(){
        return "/estoque/lista.html";
    }
}
