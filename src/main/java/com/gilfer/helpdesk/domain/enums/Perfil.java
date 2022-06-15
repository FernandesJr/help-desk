package com.gilfer.helpdesk.domain.enums;

public enum Perfil {

    ADMIN(0, "ADMIN"), CLIENTE(1, "CLIENTE"), TECNICO(2, "TECNICO");

    private Integer codigo;
    private String descricao;

    Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toPerfil(Integer codigo){
        if(codigo == null){
            return null;
        }
        for (Perfil p : Perfil.values()){
            if (p.getCodigo().equals(codigo)){
                return p;
            }
        }

        throw new IllegalArgumentException("Perfil inválido");
    }
}
