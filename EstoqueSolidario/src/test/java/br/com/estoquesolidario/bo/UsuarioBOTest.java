package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.model.Perfil;
import br.com.estoquesolidario.model.Sexo;
import br.com.estoquesolidario.model.Usuario;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioBOTest {

    @Autowired
    private UsuarioBO bo;

    @Test
    @Order(1)
    public void insere( ){
        Usuario usuario = new  Usuario();
        usuario.setNome("Mario Bros");
        usuario.setCpf("01234567899");
        usuario.setDataDeNascimento(LocalDate.of(2000,1,25));
        usuario.setSexo(Sexo.MASCULINO);
        usuario.setTelefone("0123456789");
        usuario.setCelular("11987654321");
        usuario.setAtivo(true);
        usuario.setPerfil(Perfil.ADMINISTRADOR); //Adicionando o Perfil no teste
        usuario.setEmail("mario@mario.com.br");
        bo.insere(usuario);
    }

    @Test
    @Order(2)
    public void pesquisaPeloId(){
        Usuario usuario = bo.pesquisaPeloId(1L);
        System.out.println(usuario);
    }

    @Test
    @Order(3)
    public void atualiza(){
        Usuario usuario = bo.pesquisaPeloId(1L);
        usuario.setCpf("98765432100");
        bo.atualiza(usuario);
    }
}