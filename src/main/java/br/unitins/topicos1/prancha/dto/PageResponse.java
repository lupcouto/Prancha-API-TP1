package br.unitins.topicos1.prancha.dto;
import java.util.List;

public class PageResponse<T>{

    public List<T> content;
    public long totalRegistros;
    public int totalPaginas;
    public int pagina;
    public int pageSize;

}
