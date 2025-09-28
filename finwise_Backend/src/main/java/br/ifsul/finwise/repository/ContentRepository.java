package br.ifsul.finwise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.ContentModel;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<ContentModel, Long> {
    
    // CRUD - Buscar
    
    /**
     * Busca conteúdo por título (case insensitive)
     * @param title Título do conteúdo
     * @return Lista de conteúdos com o título especificado
     */
    List<ContentModel> findByTitleIgnoreCase(String title);
    
    /**
     * Busca conteúdo por título contendo o texto (case insensitive)
     * @param titlePart Parte do título
     * @return Lista de conteúdos que contêm o texto
     */
    List<ContentModel> findByTitleContainingIgnoreCase(String titlePart);
    
    
    /**
     * Busca conteúdo por descrição contendo o texto (case insensitive)
     * @param descriptionPart Parte da descrição
     * @return Lista de conteúdos que contêm o texto
     */
    List<ContentModel> findByDescriptionContainingIgnoreCase(String descriptionPart);
    
    /**
     * Verifica se existe conteúdo com o título especificado
     * @param title Título a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByTitle(String title);
    
    /**
     * Verifica se existe conteúdo com a URL especificada
     * @param url URL a ser verificada
     * @return true se existe, false caso contrário
     */
    boolean existsByUrl(String url);
    
    /**
     * Conta quantos conteúdos existem no sistema
     * @return Número total de conteúdos
     */
    long count();
    
    /**
     * Busca conteúdos ordenados por título
     * @return Lista de conteúdos ordenados por título
     */
    @Query("SELECT c FROM ContentModel c ORDER BY c.title ASC")
    List<ContentModel> findAllOrderByTitleAsc();
    
    /**
     * Busca conteúdos ordenados por título (Z a A)
     * @return Lista de conteúdos ordenados por título decrescente
     */
    @Query("SELECT c FROM ContentModel c ORDER BY c.title DESC")
    List<ContentModel> findAllOrderByTitleDesc();
    
    /**
     * Busca conteúdos ordenados por ID (mais recentes primeiro)
     * @return Lista de conteúdos ordenados por ID decrescente
     */
    @Query("SELECT c FROM ContentModel c ORDER BY c.id DESC")
    List<ContentModel> findAllOrderByIdDesc();
    
    /**
     * Busca conteúdos ordenados por ID (mais antigos primeiro)
     * @return Lista de conteúdos ordenados por ID crescente
     */
    @Query("SELECT c FROM ContentModel c ORDER BY c.id ASC")
    List<ContentModel> findAllOrderByIdAsc();
   
    /**
     * Busca conteúdos por múltiplas URLs
     * @param urls Lista de URLs
     * @return Lista de conteúdos encontrados
     */
    @Query("SELECT c FROM ContentModel c WHERE c.url IN :urls")
    List<ContentModel> findByUrls(@Param("urls") List<String> urls);
    
    /**  
     *  Buscar conteudo por professor
     * @param Id ID do professor
     * @return Lista de conteúdos do professor
     */
    @Query("SELECT c FROM ContentModel c WHERE c.teacher.id = :userId")
    List<ContentModel> findByUserId(@Param("userId") Long userId);
    
    // CRUD - Atualizar
    
    /**
     * Atualiza o título de um conteúdo por ID
     * @param id ID do conteúdo
     * @param newTitle Novo título
     * @return Número de registros atualizados
     */
    @Query("UPDATE ContentModel c SET c.title = :newTitle WHERE c.id = :id")
    int updateTitleById(@Param("id") Long id, @Param("newTitle") String newTitle);
    
    /**
     * Atualiza a URL de um conteúdo por ID
     * @param id ID do conteúdo
     * @param newUrl Nova URL
     * @return Número de registros atualizados
     */
    @Query("UPDATE ContentModel c SET c.url = :newUrl WHERE c.id = :id")
    int updateUrlById(@Param("id") Long id, @Param("newUrl") String newUrl);
    
    /**
     * Atualiza a descrição de um conteúdo por ID
     * @param id ID do conteúdo
     * @param newDescription Nova descrição
     * @return Número de registros atualizados
     */
    @Query("UPDATE ContentModel c SET c.description = :newDescription WHERE c.id = :id")
    int updateDescriptionById(@Param("id") Long id, @Param("newDescription") String newDescription);
    
    // CRUD - Deletar
    
    /**
     * Remove conteúdo por título
     * @param title Título do conteúdo
     * @return Número de registros removidos
     */
    @Query("DELETE FROM ContentModel c WHERE c.title = :title")
    int deleteByTitle(@Param("title") String title);
    
    /**
     * Remove conteúdo por URL
     * @param url URL do conteúdo
     * @return Número de registros removidos
     */
    @Query("DELETE FROM ContentModel c WHERE c.url = :url")
    int deleteByUrl(@Param("url") String url);
    
    // Consultas de Busca Avançada
    
    /**
     * Busca conteúdos que contenham o texto em título, URL ou descrição
     * @param searchText Texto para busca
     * @return Lista de conteúdos encontrados
     */
    @Query("SELECT c FROM ContentModel c WHERE c.title LIKE %:searchText% OR c.url LIKE %:searchText% OR c.description LIKE %:searchText%")
    List<ContentModel> findByTextInAnyField(@Param("searchText") String searchText);
    
    /**
     * Busca conteúdos que contenham o texto em título ou descrição (case insensitive)
     * @param searchText Texto para busca
     * @return Lista de conteúdos encontrados
     */
    @Query("SELECT c FROM ContentModel c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<ContentModel> findByTextInTitleOrDescription(@Param("searchText") String searchText);
    
    /**
     * Busca transações com paginação
     * @param id id da transação
     * @param pageable Configuração de paginação
     * @return Lista de transações com paginação
     */
    @Query("SELECT c FROM ContentModel c WHERE c.id = :id")
    Page<ContentModel> findByIdWithPagination(@Param("id") Long id, Pageable pageable);

    /**
     * Busca os conteúdos mais recentes
     * @param limit Número de conteúdos
     * @return Lista dos conteúdos mais recentes
     */
    @Query("SELECT c FROM ContentModel c ORDER BY c.id DESC")
    List<ContentModel> findLatestContents(@Param("limit") int limit);
    
}
