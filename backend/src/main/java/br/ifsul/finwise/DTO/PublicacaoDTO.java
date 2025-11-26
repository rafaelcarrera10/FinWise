package br.ifsul.finwise.DTO;

import br.ifsul.finwise.model.PublicacaoModelo;

public class PublicacaoDTO {

    private Integer id;
    private String titulo;
    private String descricao;
    private String texto;

    public PublicacaoDTO(Integer id, String titulo, String descricao, String texto) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.texto = texto;
    }

    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getTexto() { return texto; }

    public static PublicacaoDTO toDTO(PublicacaoModelo p) {
        return new PublicacaoDTO(
            p.getId(),
            p.getTitulo(),
            p.getDescricao(),
            p.getTexto()
        );
    }
}
