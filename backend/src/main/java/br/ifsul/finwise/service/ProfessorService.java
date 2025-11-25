package br.ifsul.finwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.repository.ProfessorRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepositorio professorRepositorio;

    // Salva um novo professor ou atualiza um existente
    public ProfessorModelo save(ProfessorModelo professor) {
        return professorRepositorio.save(professor);
    }

    // Retorna todos os professores cadastrados
    public List<ProfessorModelo> findAll() {
        return professorRepositorio.findAll();
    }

    // Busca um professor pelo ID
    public Optional<ProfessorModelo> findById(Integer id) {
        return professorRepositorio.findById(id);
    }

    // Exclui um professor pelo ID
    public void deleteById(Integer id) {
        professorRepositorio.deleteById(id);
    }

    // Busca professores cujo nome contém a string fornecida (ignora maiúsculas/minúsculas)
    public List<UsuarioModelo> findByNameContaining(String name) {
        return professorRepositorio.findByNameContainingIgnoreCase(name);
    }

    // Busca professores com nome exatamente igual ao fornecido (ignora maiúsculas/minúsculas)
    public List<UsuarioModelo> findByNameExact(String name) {
        return professorRepositorio.findByNameIgnoreCase(name);
    }

    // Busca professores cujo nome começa com o prefixo informado
    public List<UsuarioModelo> findByNamePrefix(String prefix) {
        return professorRepositorio.findByNameStartingWithIgnoreCase(prefix);
    }

    // Lista todos os professores em ordem alfabética pelo nome
    public List<UsuarioModelo> findAllOrderByName() {
        return professorRepositorio.findAllOrderByNameAsc();
    }

    // Lista todos os professores em ordem decrescente pelo ID (mais recentes primeiro)
    public List<UsuarioModelo> findAllOrderByIdDesc() {
        return professorRepositorio.findAllOrderByIdDesc();
    }

    // Busca múltiplos professores pelos IDs fornecidos
    public List<UsuarioModelo> findByIds(List<Integer> ids) {
        return professorRepositorio.findByIds(ids);
    }

    // Atualiza o nome de um professor pelo ID
    @Transactional
    public int updateName(Integer id, String newName) {
        return professorRepositorio.updateUserNameById(id, newName);
    }

    // Atualiza a senha de um professor pelo ID
    @Transactional
    public int updatePassword(Integer id, String newPassword) {
        return professorRepositorio.updateUserPasswordById(id, newPassword);
    }

    // Atualiza a foto de perfil do professor pelo ID
    @Transactional
    public int updateFotoPerfil(Integer id, byte[] fotoPerfil) {
        return professorRepositorio.updateUserfotoPerfilById(id, fotoPerfil);
    }

    // Atualiza a biografia do professor pelo ID
    @Transactional
    public int updateBiografia(Integer id, String biografia) {
        return professorRepositorio.updateUserbiografiaById(id, biografia);
    }

    // Remove a foto de perfil de um professor pelo ID
    @Transactional
    public int removeFotoPerfil(Integer id) {
        return professorRepositorio.removeUserfotoPerfilById(id);
    }

    // Remove a biografia de um professor pelo ID
    @Transactional
    public int removeBiografia(Integer id) {
        return professorRepositorio.removeUserbiografiaById(id);
    }

    // Exclui professores pelo nome
    @Transactional
    public int deleteByName(String name) {
        return professorRepositorio.deleteByName(name);
    }

    // Recupera a foto de perfil de um professor pelo ID
    public byte[] getFotoPerfil(Integer id) {
        return professorRepositorio.findfotoPerfilByUserId(id);
    }

    // Recupera a biografia de um professor pelo ID
    public String getBiografia(Integer id) {
        return professorRepositorio.findbiografiaByUserId(id);
    }

    // Busca o status de um professor pelo ID
    public List<ProfessorModelo> findStatusById(Integer id) {
        return professorRepositorio.findStatusById(id);
    }
}
