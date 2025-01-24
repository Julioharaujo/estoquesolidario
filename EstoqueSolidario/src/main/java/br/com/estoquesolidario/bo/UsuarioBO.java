package br.com.estoquesolidario.bo;

import br.com.estoquesolidario.dao.CRUD;
import br.com.estoquesolidario.dao.UsuarioDAO;
import br.com.estoquesolidario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioBO implements CRUD<Usuario, Long> {
    @Autowired
    private UsuarioDAO dao;

    @Override
    public Usuario pesquisaPeloId(Long id) {
        return dao.pesquisaPeloId(id);
    }

    @Override
    public List<Usuario> lista() {
        return dao.lista();
    }

    @Override
    public void insere(Usuario usuario) {
        dao.insere(usuario);
    }

    @Override
    public void atualiza(Usuario usuario) {
        dao.atualiza(usuario);
    }

    @Override
    public void remove(Usuario usuario) {
        dao.remove(usuario);
    }

    public void inativa (Usuario usuario){
        usuario.setAtivo(false);
        dao.atualiza(usuario);
    }

    public void ativa (Usuario usuario){
        usuario.setAtivo(true);
        dao.atualiza(usuario);
    }
}
