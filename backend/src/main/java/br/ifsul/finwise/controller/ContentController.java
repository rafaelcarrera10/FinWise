package br.ifsul.finwise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.ifsul.finwise.model.ContentModel;
import br.ifsul.finwise.service.ContentService;
import br.ifsul.finwise.service.EncryptionService;

@RestController
@RequestMapping("/contents")
public class ContentController {

    private final ContentService service;
    private final EncryptionService encryptionService;

    // ÚNICO construtor com ambos os serviços
    @Autowired
    public ContentController(ContentService service, EncryptionService encryptionService) {
        this.service = service;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/encrypt")
    public String encryptUserData(@RequestBody String data) {
        return encryptionService.encrypt(data); // chave usada automaticamente
    }

    @PostMapping("/decrypt")
    public String decryptUserData(@RequestBody String data) {
        return encryptionService.decrypt(data); // chave usada automaticamente
    }

    @PostMapping("/create")
    public ContentModel createContent(@RequestBody ContentModel content) {
        return service.save(content);
    }

    // Buscar conteúdo por título (case insensitive)
    @GetMapping("/by-title")
    public List<ContentModel> getByTitle(@RequestParam String title) {
        return service.findByTitleIgnoreCase(title);
    }

    // Buscar conteúdo por título contendo o texto (case insensitive)
    @GetMapping("/title-containing")
    public List<ContentModel> getByTitleContaining(@RequestParam String titlePart) {
        return service.findByTitleContainingIgnoreCase(titlePart);
    }

    // Buscar conteúdo por descrição contendo o texto (case insensitive)
    @GetMapping("/description-containing")
    public List<ContentModel> getByDescriptionContaining(@RequestParam String descriptionPart) {
        return service.findByDescriptionContainingIgnoreCase(descriptionPart);
    }

    // Verifica se existe conteúdo com o título especificado
    @GetMapping("/exists-by-title")
    public boolean existsByTitle(@RequestParam String title) {
        return service.existsByTitle(title);
    }

    // Verifica se existe conteúdo com a URL especificada
    @GetMapping("/exists-by-url")
    public boolean existsByUrl(@RequestParam String url) {
        return service.existsByUrl(url);
    }

    // Conta quantos conteúdos existem no sistema
    @GetMapping("/count")
    public long countContents() {
        return service.count();
    }

    // Busca conteúdos ordenados por título (A-Z)
    @GetMapping("/all-order-by-title-asc")  
    public List<ContentModel> getAllOrderByTitleAsc() {
        return service.findAllOrderByTitleAsc();
    }

    // Busca conteúdos ordenados por título (Z-A)
    @GetMapping("/all-order-by-title-desc")
    public List<ContentModel> getAllOrderByTitleDesc() {
        return service.findAllOrderByTitleDesc();
    }

    // Busca conteúdos ordenados por ID (mais recentes primeiro)
    @GetMapping("/all-order-by-id-desc")
    public List<ContentModel> getAllOrderByIdDesc() {
        return service.findAllOrderByIdDesc();
    }

    // Busca conteúdos ordenados por ID (mais antigos primeiro)
    @GetMapping("/all-order-by-id-asc")
    public List<ContentModel> getAllOrderByIdAsc() {
        return service.findAllOrderByIdAsc();
    }

    // Busca conteúdo por multiplas URL
    @PostMapping("/by-urls")
    public List<ContentModel> getByUrls(@RequestBody List<String> urls) {
        return service.findByUrls(urls);
    }       

    // Busca conteúdos por ID do professor
    @GetMapping("/by-teacher-id")
    public List<ContentModel> getByTeacherId(@RequestParam Long teacherId) {
        return service.findByUserId(teacherId);
    }   

    // Atualizar o título de um conteúdo por ID
    @PostMapping("/update-title")
    public int updateTitle(@RequestParam Long id, @RequestParam String newTitle) {
        return service.updateTitleById(id, newTitle);
    }

    // Atualiza a URL de um conteúdo por ID
    @PostMapping("/update-url")
    public int updateUrl(@RequestParam Long id, @RequestParam String newUrl) {
        return service.updateUrlById(id, newUrl);
    }

    // Atualiza a descrição de um conteúdo por ID
    @PostMapping("/update-description")
    public int updateDescription(@RequestParam Long id, @RequestParam String newDescription) {
        return service.updateDescriptionById(id, newDescription);
    }   

    // Remove conteúdo por título
    @PostMapping("/delete-by-title")
    public int deleteByTitle(@RequestParam String title) {
        return service.deleteByTitle(title);
    }

    // Remove conteúdo por URL
    @PostMapping("/delete-by-url")
    public int deleteByUrl(@RequestParam String url) {
        return service.deleteByUrl(url);
    }

    // Busca conteúdos que contenham o texto em título, URL ou descrição
    @GetMapping("/search-by-method")
    public List<ContentModel> searshByMethod(@RequestParam String searchText) {
        return service.findByTextInAnyField(searchText);
    }

    // Buscar conteudos que contenham um texto na descrição ou no título (case insensitive)
    @GetMapping("/search-by-text")
    public List<ContentModel> searchByText(@RequestParam String searchText) {
        return service.findByTextInTitleOrDescription(searchText);
    }

    // Paginação e Ordenação Avançada
    @GetMapping("/paginated")
    public List<ContentModel> getPaginatedContents(
            @RequestParam Long id,
            @RequestParam int page,
            @RequestParam int size) {
        return service.findByIdWithPagination(id, page, size);
    }

    // Buscar os conteúdos mais recentes
    @GetMapping("/latest")
    public List<ContentModel> getLatestContents(@RequestParam int limit) {
        return service.findLatestContents(limit);
    }
}
