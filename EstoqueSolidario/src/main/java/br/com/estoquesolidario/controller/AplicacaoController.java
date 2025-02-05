package br.com.estoquesolidario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AplicacaoController {

    @RequestMapping(value = {"/",""}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    //http://localhost:8080/login
    @RequestMapping(value = "/login", method= RequestMethod.GET)
    public String login(){
        return "login";
    }
}