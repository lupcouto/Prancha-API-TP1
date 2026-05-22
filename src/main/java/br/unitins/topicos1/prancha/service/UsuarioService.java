package br.unitins.topicos1.prancha.service;
import java.util.List;

import br.unitins.topicos1.prancha.dto.UsuarioSenhaDTO;
import br.unitins.topicos1.prancha.dto.UsuarioUpdateDTO;
import br.unitins.topicos1.prancha.model.Usuario;

public interface UsuarioService {

    List<Usuario> findAll();
    Usuario findByLogin(String login);
    Usuario findByLoginAndSenha(String login, String senha);
    Usuario findById(Long id);
    Usuario updateUsuario(String login, UsuarioUpdateDTO dto);
    void alterarSenha(String login, UsuarioSenhaDTO dto);

}