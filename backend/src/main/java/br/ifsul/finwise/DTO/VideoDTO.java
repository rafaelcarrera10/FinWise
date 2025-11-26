package  br.ifsul.finwise.DTO;
import br.ifsul.finwise.model.VideoModelo;

public class VideoDTO {

    private Integer id;
    private String titulo;
    private String descricao;
    private String videoUrl;

    public VideoDTO(Integer id, String titulo, String descricao, String videoUrl) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.videoUrl = videoUrl;
    }

    // GETTERS
    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getVideoUrl() { return videoUrl; }

    // MÉTODO QUE CONVERTE Video → VideoDTO
    public static VideoDTO toDTO(VideoModelo v) {
        return new VideoDTO(
            v.getId(),
            v.getTitulo(),
            v.getDescricao(),
            v.getVideo()
        );
    }
}