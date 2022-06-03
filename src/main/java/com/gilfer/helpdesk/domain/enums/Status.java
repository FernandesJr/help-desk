package com.gilfer.helpdesk.domain.enums;

public enum Status {

    ABERTO(0, "ROLE_ABERTO"), ANDAMENTO(1, "ROLE_ANDAMENTO"), ENCERRADO(2, "ROLE_ENCERRADO");

    private Integer codigo;
    private String descricao;

    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status toPerfil(Integer codigo){
        if(codigo == null){
            return null;
        }
        for (Status p : Status.values()){
            if (p.getCodigo().equals(codigo)){
                return p;
            }
        }

        throw new IllegalArgumentException("Status inválido");
    }
}
