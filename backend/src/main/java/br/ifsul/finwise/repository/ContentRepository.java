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

    // Busca conteúdo com título exato (ignora maiúsculas/minúsculas)
    List<ContentModel> findByTitleIgnoreCase(String title);

    // Busca conteúdo contendo parte do título (ignora maiúsculas/minúsculas)
    List<ContentModel> findByTitleContainingIgnoreCase(String titlePart);

    // Busca conteúdo contendo parte da descrição (ignora maiúsculas/minúsculas)
    List<ContentModel> findByDescriptionContainingIgnoreCase(String descriptionPart);

    // Verifica se existe conteúdo com o título informado
    boolean existsByTitle(String title);

    // Verifica se existe conteúdo com a URL informada
    boolean existsByUrl(String url);

    // Conta o número total de conteúdos
    long count();

    // Lista todos os conteúdos em ordem alfabética crescente
    @Query("SELECT c FROM ContentModel c ORDER BY c.title ASC")
    List<ContentModel> findAllOrderByTitleAsc();

    // Lista todos os conteúdos em ordem alfabética decrescente
    @Query("SELECT c FROM ContentModel c ORDER BY c.title DESC")
    List<ContentModel> findAllOrderByTitleDesc();

    // Lista os conteúdos mais recentes (por ID decrescente)
    @Query("SELECT c FROM ContentModel c ORDER BY c.id DESC")
    List<ContentModel> findAllOrderByIdDesc();

    // Lista os conteúdos mais antigos (por ID crescente)
    @Query("SELECT c FROM ContentModel c ORDER BY c.id ASC")
    List<ContentModel> findAllOrderByIdAsc();

    // Busca conteúdos por uma lista de URLs
    @Query("SELECT c FROM ContentModel c WHERE c.url IN :urls")
    List<ContentModel> findByUrls(@Param("urls") List<String> urls);

    // Busca conteúdos criados por um determinado usuário (autor)
    @Query("SELECT c FROM ContentModel c WHERE c.autor.id = :userId")
    List<ContentModel> findByUserId(@Param("userId") Long userId);

    // Atualiza o título de um conteúdo
    @Query("UPDATE ContentModel c SET c.title = :newTitle WHERE c.id = :id")
    int updateTitleById(@Param("id") Long id, @Param("newTitle") String newTitle);

    // Atualiza a URL de um conteúdo
    @Query("UPDATE ContentModel c SET c.url = :newUrl WHERE c.id = :id")
    int updateUrlById(@Param("id") Long id, @Param("newUrl") String newUrl);

    // Atualiza a descrição de um conteúdo
    @Query("UPDATE ContentModel c SET c.description = :newDescription WHERE c.id = :id")
    int updateDescriptionById(@Param("id") Long id, @Param("newDescription") String newDescription);

    // Exclui conteúdo pelo título
    @Query("DELETE FROM ContentModel c WHERE c.title = :title")
    int deleteByTitle(@Param("title") String title);

    // Exclui conteúdo pela URL
    @Query("DELETE FROM ContentModel c WHERE c.url = :url")
    int deleteByUrl(@Param("url") String url);

    // Busca conteúdos que contenham o texto em título, URL ou descrição
    @Query("SELECT c FROM ContentModel c WHERE c.title LIKE %:searchText% OR c.url LIKE %:searchText% OR c.description LIKE %:searchText%")
    List<ContentModel> findByTextInAnyField(@Param("searchText") String searchText);

    // Busca conteúdos que contenham o texto em título ou descrição (ignora maiúsculas/minúsculas)
    @Query("SELECT c FROM ContentModel c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<ContentModel> findByTextInTitleOrDescription(@Param("searchText") String searchText);

    // Busca conteúdo com paginação
    @Query("SELECT c FROM ContentModel c WHERE c.id = :id")
    Page<ContentModel> findByIdWithPagination(@Param("id") Long id, Pageable pageable);

    // Retorna os conteúdos mais recentes (limitado por parâmetro)
    @Query("SELECT c FROM ContentModel c ORDER BY c.id DESC")
    List<ContentModel> findLatestContents(@Param("limit") int limit);
}
