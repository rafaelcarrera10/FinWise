package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; 

@RestController
@RequestMapping("/professores")
@CrossOrigin(origins = "*")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessorModelo> create(@RequestBody ProfessorModelo professor) {
        return ResponseEntity.ok(professorService.save(professor));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProfessorModelo>> getAll() {
        return ResponseEntity.ok(professorService.findAll());
    }

    @GetMapping("/by-id")
    public ResponseEntity<ProfessorModelo> getById(@RequestParam Integer id) {
        return professorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @GetMapping("/ordered/by-name")
    public ResponseEntity<List<ProfessorModelo>> getAllOrderByName() {
        return ResponseEntity.ok(professorService.findAllOrderByName());
    }

    @GetMapping("/ordered/by-id-desc")
    public ResponseEntity<List<ProfessorModelo>> getAllOrderByIdDesc() {
        return ResponseEntity.ok(professorService.findAllOrderByIdDesc());
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<ProfessorModelo>> getByIds(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok(professorService.findByIds(ids));
    }

    @GetMapping("/foto-perfil")
    public ResponseEntity<String> getFotoPerfil(@RequestParam Integer id) {
        String base64 = professorService.getFotoPerfilBase64(id);
        return base64 != null ? ResponseEntity.ok(base64) : ResponseEntity.notFound().build();
    }

    @GetMapping("/biografia")
    public ResponseEntity<String> getBiografia(@RequestParam Integer id) {
        String bio = professorService.getBiografia(id);
        return bio != null ? ResponseEntity.ok(bio) : ResponseEntity.notFound().build();
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> getStatusById(@RequestParam Integer id) {
        Boolean status = professorService.getStatusById(id);
        return status != null ? ResponseEntity.ok(status) : ResponseEntity.notFound().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        professorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-name")
    public ResponseEntity<Void> updateName(@RequestBody Map<String, Object> body) {
        Integer id = ((Number) body.get("id")).intValue();
        String newName = (String) body.get("newName");
        professorService.updateName(id, newName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody Map<String, Object> body) {
        Integer id = ((Number) body.get("id")).intValue();
        String newPassword = (String) body.get("newPassword");
        professorService.updatePassword(id, newPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-biografia")
    public ResponseEntity<Void> updateBiografia(@RequestBody Map<String, Object> body) {
        Integer id = ((Number) body.get("id")).intValue();
        String biografia = (String) body.get("biografia");
        professorService.updateBiografia(id, biografia);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-foto-perfil")
    public ResponseEntity<Void> updateFotoPerfil(@RequestBody Map<String, Object> body) {
        Integer id = ((Number) body.get("id")).intValue();
        String photoBase64 = (String) body.get("photo");
        byte[] foto = java.util.Base64.getDecoder().decode(photoBase64);
        professorService.updateFotoPerfil(id, foto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-foto-perfil")
    public ResponseEntity<Void> removeFotoPerfil(@RequestParam Integer id) {
        professorService.removeFotoPerfil(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-biografia")
    public ResponseEntity<Void> removeBiografia(@RequestParam Integer id) {
        professorService.removeBiografia(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-by-name")
    public ResponseEntity<Void> deleteByName(@RequestParam String name) {
        professorService.deleteByName(name);
        return ResponseEntity.ok().build();
    }
}
