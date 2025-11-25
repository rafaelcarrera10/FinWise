package br.ifsul.finwise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.repository.ProfessorRepositorio;

@Service
public class ProfessorService {

    private final ProfessorRepositorio professorRepositorio;

    public ProfessorService(ProfessorRepositorio professorRepositorio) {
        this.professorRepositorio = professorRepositorio;
    }

    public ProfessorModelo save(ProfessorModelo professor) {
        return professorRepositorio.save(professor);
    }

    public List<ProfessorModelo> findAll() {
        return professorRepositorio.findAll();
    }

    public Optional<ProfessorModelo> findById(Integer id) {
        return professorRepositorio.findById(id);
    }

    public void deleteById(Integer id) {
        professorRepositorio.deleteById(id);
    }

    public List<ProfessorModelo> findByNameContaining(String name) {
        return professorRepositorio.findByNameContainingIgnoreCase(name);
    }

    public List<ProfessorModelo> findByNameExact(String name) {
        return professorRepositorio.findByNameIgnoreCase(name);
    }

    public List<ProfessorModelo> findByNamePrefix(String prefix) {
        return professorRepositorio.findByNameStartingWithIgnoreCase(prefix);
    }

    public List<ProfessorModelo> findAllOrderByName() {
        return professorRepositorio.findAllOrderByNameAsc();
    }

    public List<ProfessorModelo> findAllOrderByIdDesc() {
        return professorRepositorio.findAllOrderByIdDesc();
    }

    public List<ProfessorModelo> findByIds(List<Integer> ids) {
        return professorRepositorio.findByIds(ids);
    }

    @Transactional
    public int updateName(Integer id, String newName) {
        return professorRepositorio.updateNameById(id, newName);
    }

    @Transactional
    public int updatePassword(Integer id, String newPassword) {
        return professorRepositorio.updatePasswordById(id, newPassword);
    }

    @Transactional
    public int updateFotoPerfil(Integer id, byte[] fotoPerfil) {
        return professorRepositorio.updateFotoPerfilById(id, fotoPerfil);
    }

    @Transactional
    public int updateBiografia(Integer id, String biografia) {
        return professorRepositorio.updateBiografiaById(id, biografia);
    }

    @Transactional
    public int removeFotoPerfil(Integer id) {
        return professorRepositorio.removeFotoPerfilById(id);
    }

    @Transactional
    public int removeBiografia(Integer id) {
        return professorRepositorio.removeBiografiaById(id);
    }

    @Transactional
    public int deleteByName(String name) {
        return professorRepositorio.deleteByName(name);
    }

    public byte[] getFotoPerfil(Integer id) {
        return professorRepositorio.findFotoPerfilById(id);
    }

    public String getBiografia(Integer id) {
        return professorRepositorio.findBiografiaById(id);
    }

    public Boolean getStatusById(Integer id) {
        return professorRepositorio.findStatusById(id);
    }

    // Converter foto para Base64
    public String getFotoPerfilBase64(Integer id) {
        byte[] foto = getFotoPerfil(id);
        return foto != null ? java.util.Base64.getEncoder().encodeToString(foto) : null;
    }
}
