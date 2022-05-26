package domain.enums;

public enum Prioridade {

    BAIXA(0, "ROLE_BAIXA"), MEDIA(1, "ROLE_MEDIA"), ALTA(2, "ROLE_ALTA");

    private Integer codigo;
    private String descricao;

    Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Prioridade toPerfil(Integer codigo){
        if(codigo == null){
            return null;
        }
        for (Prioridade p : Prioridade.values()){
            if (p.getCodigo().equals(codigo)){
                return p;
            }
        }

        throw new IllegalArgumentException("Descrição inválida");
    }
}
