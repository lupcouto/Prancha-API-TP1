package br.unitins.topicos1.prancha.repository;
import br.unitins.topicos1.prancha.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    // busca um usuário pelo login e senha
    public Usuario findByLoginSenha(String login, String senha) {
        return find("SELECT u FROM Usuario u WHERE u.login = ?1 AND u.senha = ?2 ", login, senha).firstResult();
    }

    // busca um usuário pelo login
    public Usuario findByLogin(String login) {
       return find("SELECT u FROM Usuario u WHERE u.login = ?1 ", login).firstResult();
    }

}