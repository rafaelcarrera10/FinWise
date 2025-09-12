package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.ContentModel;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<ContentModel, Long> {
    
    // CRUD - Buscar
    /**
     * Busca conteúdo por título
     * @param title Título do conteúdo
     * @return Lista de conteúdos com o título especificado
     */
    List<ContentModel> findByTitle(String title);
    
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
     * Busca conteúdo por URL
     * @param url URL do conteúdo
     * @return Optional contendo o conteúdo se encontrado
     */
    Optional<ContentModel> findByUrl(String url);
    
    /**
     * Busca conteúdo por URL contendo o texto
     * @param urlPart Parte da URL
     * @return Lista de conteúdos que contêm o texto
     */
    List<ContentModel> findByUrlContaining(String urlPart);
    
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
     * Busca conteúdos por múltiplos títulos
     * @param titles Lista de títulos
     * @return Lista de conteúdos encontrados
     */
    @Query("SELECT c FROM ContentModel c WHERE c.title IN :titles")
    List<ContentModel> findByTitles(@Param("titles") List<String> titles);
    
    /**
     * Busca conteúdos por múltiplas URLs
     * @param urls Lista de URLs
     * @return Lista de conteúdos encontrados
     */
    @Query("SELECT c FROM ContentModel c WHERE c.url IN :urls")
    List<ContentModel> findByUrls(@Param("urls") List<String> urls);
    
    /**
     * Busca conteúdos com título vazio ou nulo
     * @return Lista de conteúdos com título vazio
     */
    @Query("SELECT c FROM ContentModel c WHERE c.title IS NULL OR c.title = ''")
    List<ContentModel> findByEmptyTitle();
    
    /**
     * Busca conteúdos com URL vazia ou nula
     * @return Lista de conteúdos com URL vazia
     */
    @Query("SELECT c FROM ContentModel c WHERE c.url IS NULL OR c.url = ''")
    List<ContentModel> findByEmptyUrl();
    
    /**
     * Busca conteúdos com descrição vazia ou nula
     * @return Lista de conteúdos com descrição vazia
     */
    @Query("SELECT c FROM ContentModel c WHERE c.description IS NULL OR c.description = ''")
    List<ContentModel> findByEmptyDescription();
    
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
    
    /**
     * Remove conteúdos com título vazio
     * @return Número de registros removidos
     */
    @Query("DELETE FROM ContentModel c WHERE c.title IS NULL OR c.title = ''")
    int deleteByEmptyTitle();
    
    /**
     * Remove conteúdos com URL vazia
     * @return Número de registros removidos
     */
    @Query("DELETE FROM ContentModel c WHERE c.url IS NULL OR c.url = ''")
    int deleteByEmptyUrl();
    
    /**
     * Remove conteúdos com descrição vazia
     * @return Número de registros removidos
     */
    @Query("DELETE FROM ContentModel c WHERE c.description IS NULL OR c.description = ''")
    int deleteByEmptyDescription();
    
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
     * Busca conteúdos com paginação
     * @param offset Posição inicial
     * @param limit Número máximo de registros
     * @return Lista de conteúdos com paginação
     */
    @Query("SELECT c FROM ContentModel c ORDER BY c.id ASC")
    List<ContentModel> findContentsWithPagination(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * Busca os conteúdos mais recentes
     * @param limit Número de conteúdos
     * @return Lista dos conteúdos mais recentes
     */
    @Query("SELECT c FROM ContentModel c ORDER BY c.id DESC")
    List<ContentModel> findLatestContents(@Param("limit") int limit);
    
    /**
     * Busca conteúdos que comecem com o texto especificado no título
     * @param prefix Prefixo do título
     * @return Lista de conteúdos encontrados
     */
    @Query("SELECT c FROM ContentModel c WHERE c.title LIKE CONCAT(:prefix, '%')")
    List<ContentModel> findByTitleStartingWith(@Param("prefix") String prefix);
    
    /**
     * Busca conteúdos que terminem com o texto especificado no título
     * @param suffix Sufixo do título
     * @return Lista de conteúdos encontrados
     */
    @Query("SELECT c FROM ContentModel c WHERE c.title LIKE CONCAT('%', :suffix)")
    List<ContentModel> findByTitleEndingWith(@Param("suffix") String suffix);
}
