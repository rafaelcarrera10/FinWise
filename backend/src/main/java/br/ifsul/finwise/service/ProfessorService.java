package br.ifsul.finwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ifsul.finwise.model.ProfessorModelo;
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
    public Optional<ProfessorModelo> findById(Long id) {
        return professorRepositorio.findById(id);
    }

    // Exclui um professor pelo ID
    public void deleteById(Long id) {
        professorRepositorio.deleteById(id);
    }

    // Busca professores cujo nome contém a string fornecida (ignora maiúsculas/minúsculas)
    public List<ProfessorModelo> findByNameContaining(String name) {
        return professorRepositorio.findByNameContainingIgnoreCase(name);
    }

    // Busca professores com nome exatamente igual ao fornecido (ignora maiúsculas/minúsculas)
    public List<ProfessorModelo> findByNameExact(String name) {
        return professorRepositorio.findByNameIgnoreCase(name);
    }

    // Busca professores cujo nome começa com o prefixo informado
    public List<ProfessorModelo> findByNamePrefix(String prefix) {
        return professorRepositorio.findByNameStartingWithIgnoreCase(prefix);
    }

    // Lista todos os professores em ordem alfabética pelo nome
    public List<ProfessorModelo> findAllOrderByName() {
        return professorRepositorio.findAllOrderByNameAsc();
    }

    // Lista todos os professores em ordem decrescente pelo ID (mais recentes primeiro)
    public List<ProfessorModelo> findAllOrderByIdDesc() {
        return professorRepositorio.findAllOrderByIdDesc();
    }

    // Busca múltiplos professores pelos IDs fornecidos
    public List<ProfessorModelo> findByIds(List<Long> ids) {
        return professorRepositorio.findByIds(ids);
    }

    // Atualiza o nome de um professor pelo ID
    @Transactional
    public int updateName(Long id, String newName) {
        return professorRepositorio.updateUserNameById(id, newName);
    }

    // Atualiza a senha de um professor pelo ID
    @Transactional
    public int updatePassword(Long id, String newPassword) {
        return professorRepositorio.updateUserPasswordById(id, newPassword);
    }

    // Atualiza a foto de perfil do professor pelo ID
    @Transactional
    public int updateFotoPerfil(Long id, byte[] fotoPerfil) {
        return professorRepositorio.updateUserfotoPerfilById(id, fotoPerfil);
    }

    // Atualiza a biografia do professor pelo ID
    @Transactional
    public int updateBiografia(Long id, String biografia) {
        return professorRepositorio.updateUserbiografiaById(id, biografia);
    }

    // Remove a foto de perfil de um professor pelo ID
    @Transactional
    public int removeFotoPerfil(Long id) {
        return professorRepositorio.removeUserfotoPerfilById(id);
    }

    // Remove a biografia de um professor pelo ID
    @Transactional
    public int removeBiografia(Long id) {
        return professorRepositorio.removeUserbiografiaById(id);
    }

    // Exclui professores pelo nome
    @Transactional
    public int deleteByName(String name) {
        return professorRepositorio.deleteByName(name);
    }

    // Recupera a foto de perfil de um professor pelo ID
    public byte[] getFotoPerfil(Long id) {
        return professorRepositorio.findfotoPerfilByUserId(id);
    }

    // Recupera a biografia de um professor pelo ID
    public String getBiografia(Long id) {
        return professorRepositorio.findbiografiaByUserId(id);
    }

    // Busca o status de um professor pelo ID
    public List<ProfessorModelo> findStatusById(Long id) {
        return professorRepositorio.findStatusById(id);
    }
}
