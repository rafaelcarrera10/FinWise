package br.ifsul.finwise.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import br.ifsul.finwise.model.ContentModel;
import br.ifsul.finwise.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ContentService {

    private ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    // Salvar Conteúdo
    public ContentModel save(ContentModel content) {
        return contentRepository.save(content);
    }

    // Buscar

    // Busca conteúdo por título (case insensitive)
    public List<ContentModel> findByTitleIgnoreCase(String title) {
        return contentRepository.findByTitleIgnoreCase(title);
    }

    // Busca conteúdo por título contendo o texto (case insensitive)
    public List<ContentModel> findByTitleContainingIgnoreCase(String titlePart) {
        return contentRepository.findByTitleContainingIgnoreCase(titlePart);
    }

    // Busca conteúdo por descrição contendo o texto (case insensitive)
    public List<ContentModel> findByDescriptionContainingIgnoreCase(String descriptionPart) {
        return contentRepository.findByDescriptionContainingIgnoreCase(descriptionPart);
    }

    // Verifica se existe conteúdo com o título especificado
    public boolean existsByTitle(String title) {
        return contentRepository.existsByTitle(title);
    }

    // Verifica se existe conteúdo com a URL especificada
    public boolean existsByUrl(String url) {
        return contentRepository.existsByUrl(url);
    }

    // Conta quantos conteúdos existem no sistema
    public long count() {
        return contentRepository.count();
    }

    // Busca conteúdos ordenados por título (A-Z)
    public List<ContentModel> findAllOrderByTitleAsc() {
        return contentRepository.findAllOrderByTitleAsc();
    }

    // Busca conteúdos ordenados por título (Z-A)
    public List<ContentModel> findAllOrderByTitleDesc() {
        return contentRepository.findAllOrderByTitleDesc();
    }

    // Busca conteúdos ordenados por ID (mais recentes primeiro)
    public List<ContentModel> findAllOrderByIdDesc() {
        return contentRepository.findAllOrderByIdDesc();
    }

    // Busca conteúdos ordenados por ID (mais antigos primeiro)
    public List<ContentModel> findAllOrderByIdAsc() {
        return contentRepository.findAllOrderByIdAsc();
    }

    // Busca conteúdo por multiplas URL
    public List<ContentModel> findByUrls(List<String> urls) {
        return contentRepository.findByUrls(urls);
    }

    // Busca conteúdos por ID do professor
    public List<ContentModel> findByUserId(Long Id) {
        return contentRepository.findByUserId(Id);
    }

    // Atualizar

    // Atualiza o título de um conteúdo por ID
    public int updateTitleById(Long id, String newTitle) {
        return contentRepository.updateTitleById(id, newTitle);
    }

    // Atualiza a URL de um conteúdo por ID
    public int updateUrlById(Long id, String newUrl) {
        return contentRepository.updateUrlById(id, newUrl);
    }

    // Atualiza a descrição de um conteúdo por ID
    public int updateDescriptionById(Long id, String newDescription) {
        return contentRepository.updateDescriptionById(id, newDescription);
    }

    // Deletar

    // Remove conteúdo por título
    public int deleteByTitle(String title) {
        return contentRepository.deleteByTitle(title);
    }

    // Remove conteúdo por URL
    public int deleteByUrl(String url) {
        return contentRepository.deleteByUrl(url);
    }

    // Consultas de Busca Avançada

    // Busca conteúdos que contenham o texto em título, URL ou descrição 
    public List<ContentModel> findByTextInAnyField(String searchText) {
        return contentRepository.findByTextInAnyField(searchText);
    }

    
    // Busca conteúdos que contenham o texto em título ou descrição (case insensitive)
    public List<ContentModel> findByTextInTitleOrDescription(String searchText) {
        return contentRepository.findByTextInTitleOrDescription(searchText);
    }

    // Paginação
    public List<ContentModel> findByIdWithPagination(Long id, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return contentRepository.findByIdWithPagination(id, pageable).getContent();
    }

     //Busca os conteúdos mais recentes
    public List<ContentModel> findLatestContents(int limit) {
        return contentRepository.findLatestContents(limit);
    }
}