package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.model.Usuario;
import br.unitins.topicos1.prancha.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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

}