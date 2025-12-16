package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.AdministradorDTO;
import br.unitins.topicos1.prancha.model.Administrador;
import jakarta.validation.Valid;

public interface AdministradorService {
    
    List<Administrador> findAll();
    List<Administrador> findByNome(String nome);
    Administrador findById(Long id);
    Administrador create(@Valid AdministradorDTO dto);
    void update(Long id, @Valid AdministradorDTO dto);
    void delete(Long id);

}