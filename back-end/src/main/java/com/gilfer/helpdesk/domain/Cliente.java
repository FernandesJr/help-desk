package com.gilfer.helpdesk.domain;

import com.gilfer.helpdesk.domain.dto.ClienteDTO;
import com.gilfer.helpdesk.domain.enums.Perfil;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Cliente extends Pessoa{

    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente() {
        super();
    }

    public Cliente(ClienteDTO dto) {
        super();
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
        //this.dataCriacao = dto.getDataCriacao();
        this.perfis = dto.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.perfis.add(1);
        //dto.getPerfis().stream().map(p -> this.perfis.add(p.getCodigo()));
    }

    public Cliente(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }
}
