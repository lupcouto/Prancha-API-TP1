package br.unitins.topicos1.prancha.service;
import br.unitins.topicos1.prancha.model.Perfil;

public interface JwtService {

    public String generateJwt(String usuario, Perfil perfil);
    
}