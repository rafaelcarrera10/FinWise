package br.ifsul.finwise.controller;

// import br.ifsul.finwise.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller para demonstrar e testar as validações de dados
 */
@RestController
@RequestMapping("/api/validation")
public class ValidationController {
    
    @Autowired
    // private ValidationService validationService;
    
    /**
     * Endpoint para testar validação de email
     */
    @PostMapping("/email")
    public ResponseEntity<Map<String, Object>> validateEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        // response.put("isValid", validationService.isValidEmail(email));
        
        // if (!validationService.isValidEmail(email)) {
        //     response.put("error", "Email inválido: formato incorreto ou domínio suspeito.");
        // }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para testar validação de nome
     */
    @PostMapping("/name")
    public ResponseEntity<Map<String, Object>> validateName(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        
        Map<String, Object> response = new HashMap<>();
        response.put("name", name);
        // response.put("isValid", validationService.isValidName(name));
        
        // if (!validationService.isValidName(name)) {
        //     response.put("error", "Nome inválido: deve conter apenas letras, espaços, acentos e hífens. Não pode conter palavrões, palavras soltas ou caracteres inadequados.");
        // }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para testar validação completa de usuário
     */
    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> validateUser(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        
        // ValidationService.ValidationResult result = validationService.validateUserData(name, email);
        
        Map<String, Object> response = new HashMap<>();
        response.put("name", name);
        response.put("email", email);
        // response.put("isValid", result.isValid());
        
        // if (!result.isValid()) {
        //     response.put("errors", result.getErrors());
        //     response.put("errorMessage", result.getErrorMessage());
        // }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para testar detecção de palavrões
     */
    @PostMapping("/profanity")
    public ResponseEntity<Map<String, Object>> checkProfanity(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        
        Map<String, Object> response = new HashMap<>();
        response.put("text", text);
        // response.put("containsProfanity", validationService.containsProfanity(text));
        // response.put("containsRandomWords", validationService.containsRandomWords(text));
        // response.put("containsSpam", validationService.containsSpam(text));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para obter exemplos de validação
     */
    @GetMapping("/examples")
    public ResponseEntity<Map<String, Object>> getExamples() {
        Map<String, Object> examples = new HashMap<>();
        
        // Exemplos de emails válidos e inválidos
        Map<String, Object> emailExamples = new HashMap<>();
        emailExamples.put("valid", new String[]{
            "usuario@email.com",
            "joao.silva@empresa.com.br",
            "maria123@universidade.edu"
        });
        emailExamples.put("invalid", new String[]{
            "email-sem-arroba",
            "usuario@tempmail.com",
            "email@dominio-suspeito.com"
        });
        
        // Exemplos de nomes válidos e inválidos
        Map<String, Object> nameExamples = new HashMap<>();
        nameExamples.put("valid", new String[]{
            "João Silva",
            "Maria José da Silva",
            "José-Augusto Santos"
        });
        nameExamples.put("invalid", new String[]{
            "arroz feijão",
            "João123",
            "Maria@#$%",
            "João P****"
        });
        
        examples.put("emails", emailExamples);
        examples.put("names", nameExamples);
        
        return ResponseEntity.ok(examples);
    }
}
