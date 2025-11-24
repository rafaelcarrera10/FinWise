package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; 

@RestController
@RequestMapping("/professores")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    // Cria um novo professor
    @PostMapping("/create")
    public ResponseEntity<ProfessorModelo> create(@RequestBody ProfessorModelo professor) {
        return ResponseEntity.ok(professorService.save(professor));
    }

    // Lista todos os professores
    @GetMapping("/all")
    public ResponseEntity<List<ProfessorModelo>> getAll() {
        return ResponseEntity.ok(professorService.findAll());
    }

    // Busca um professor pelo ID
    @GetMapping("/by-id")
    public ResponseEntity<ProfessorModelo> getById(@RequestParam Long id) {
        return professorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca professores (vários filtros de nome)
    @GetMapping("/search")
    public ResponseEntity<List<ProfessorModelo>> search(
            @RequestParam(value = "containing", required = false) String nameContaining,
            @RequestParam(value = "exact", required = false) String nameExact,
            @RequestParam(value = "prefix", required = false) String namePrefix) {

        if (nameContaining != null) {
            return ResponseEntity.ok(professorService.findByNameContaining(nameContaining));
        }
        if (nameExact != null) {
            return ResponseEntity.ok(professorService.findByNameExact(nameExact));
        }
        if (namePrefix != null) {
            return ResponseEntity.ok(professorService.findByNamePrefix(namePrefix));
        }
        return ResponseEntity.badRequest().build();
    }

    // Lista todos ordenados por nome ASC
    @GetMapping("/ordered/by-name")
    public ResponseEntity<List<ProfessorModelo>> getAllOrderByName() {
        return ResponseEntity.ok(professorService.findAllOrderByName());
    }

    // Lista todos ordenados por ID DESC (mais recentes)
    @GetMapping("/ordered/by-id-desc")
    public ResponseEntity<List<ProfessorModelo>> getAllOrderByIdDesc() {
        return ResponseEntity.ok(professorService.findAllOrderByIdDesc());
    }

    // Busca por uma lista de IDs
    @GetMapping("/by-ids")
    public ResponseEntity<List<ProfessorModelo>> getByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(professorService.findByIds(ids));
    }

    // Recupera a foto do perfil (codificada em Base64)
    @GetMapping("/foto-perfil")
    public ResponseEntity<String> getFotoPerfil(@RequestParam Long id) {
        String photoBase64 = professorService.getFotoPerfilBase64(id); // Assumindo que você crie este método no Service
        return photoBase64 != null ? ResponseEntity.ok(photoBase64) : ResponseEntity.notFound().build();
    }

    // Recupera a biografia
    @GetMapping("/biografia")
    public ResponseEntity<String> getBiografia(@RequestParam Long id) {
        String biografia = professorService.getBiografia(id);
        return biografia != null ? ResponseEntity.ok(biografia) : ResponseEntity.notFound().build();
    }

    // Busca status por ID
    @GetMapping("/status")
    public ResponseEntity<List<ProfessorModelo>> getStatusById(@RequestParam Long id) {
        List<ProfessorModelo> statusList = professorService.findStatusById(id);
        return ResponseEntity.ok(statusList);
    }

    // --- Mutações (POST) ---

    // Deleta um professor pelo ID
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        professorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Atualiza o nome, recebendo id e newName via Map
    @PostMapping("/update-name")
    public ResponseEntity<Void> updateName(@RequestBody Map<String, Object> body) {
        Long id = ((Number) body.get("id")).longValue();
        String newName = (String) body.get("newName");
        
        professorService.updateName(id, newName);
        return ResponseEntity.ok().build();
    }

    // Atualiza a senha, recebendo id e newPassword via Map
    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody Map<String, Object> body) {
        Long id = ((Number) body.get("id")).longValue();
        String newPassword = (String) body.get("newPassword");

        professorService.updatePassword(id, newPassword);
        return ResponseEntity.ok().build();
    }

    // Atualiza a biografia, recebendo id e biografia via Map
    @PostMapping("/update-biografia")
    public ResponseEntity<Void> updateBiografia(@RequestBody Map<String, Object> body) {
        Long id = ((Number) body.get("id")).longValue();
        String biografia = (String) body.get("biografia");

        professorService.updateBiografia(id, biografia);
        return ResponseEntity.ok().build();
    }

    // Atualiza a foto de perfil (recebendo Base64)
    @PostMapping("/update-foto-perfil")
    public ResponseEntity<Void> updateFotoPerfil(@RequestBody Map<String, Object> body) {
        try {
            Long id = ((Number) body.get("id")).longValue();
            String photoBase64 = (String) body.get("photo"); // Recebe a foto como uma string Base64

            professorService.updateFotoPerfilBase64(id, photoBase64); // Assumindo que você crie este método no Service
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Remove a foto de perfil
    @PostMapping("/remove-foto-perfil")
    public ResponseEntity<Void> removeFotoPerfil(@RequestParam Long id) {
        professorService.removeFotoPerfil(id);
        return ResponseEntity.ok().build();
    }

    // Remove a biografia
    @PostMapping("/remove-biografia")
    public ResponseEntity<Void> removeBiografia(@RequestParam Long id) {
        professorService.removeBiografia(id);
        return ResponseEntity.ok().build();
    }

    // Deleta por nome
    @PostMapping("/delete-by-name")
    public ResponseEntity<Void> deleteByName(@RequestParam String name) {
        professorService.deleteByName(name);
        return ResponseEntity.ok().build();
    }
}