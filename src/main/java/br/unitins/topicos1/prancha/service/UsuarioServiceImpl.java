package br.unitins.topicos1.prancha.service;

import java.util.List;

import br.unitins.topicos1.prancha.dto.UsuarioSenhaDTO;
import br.unitins.topicos1.prancha.dto.UsuarioUpdateDTO;
import br.unitins.topicos1.prancha.model.Usuario;
import br.unitins.topicos1.prancha.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    // busca todos os registros no banco
    @Override
    public List<Usuario> findAll() {
        return repository.listAll();
    }

    // busca todos os registros pelo login no banco
    @Override
    public Usuario findByLogin(String login) {
        return repository.findByLogin(login);
    }

    // busca todos os registros pelo login e senha no banco
    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return repository.findByLoginSenha(login, senha);
    }

    // busca todos os registros pelo id no banco
    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id);
        if (usuario == null)
            return null;

        return usuario;
    }

    @Transactional
    public Usuario updateUsuario(String login, UsuarioUpdateDTO dto) {

        Usuario usuario = findByLogin(login);

        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado", Response.Status.NOT_FOUND);
        }

        usuario.getCliente().setNome(dto.nome());
        usuario.getCliente().setEmail(dto.email());

        usuario.getCliente().getTelefone().setDdd(dto.ddd());
        usuario.getCliente().getTelefone().setNumero(dto.numero());

        return usuario;
    }

    @Transactional
    public void alterarSenha(String login, UsuarioSenhaDTO dto) {

        Usuario usuario = findByLogin(login);

        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado", Response.Status.NOT_FOUND);
        }

        if (!usuario.getSenha().equals(dto.senhaAtual())) {
            throw new WebApplicationException("Senha incorreta", Response.Status.UNAUTHORIZED);
        }

        usuario.setSenha(dto.novaSenha());
    }

}