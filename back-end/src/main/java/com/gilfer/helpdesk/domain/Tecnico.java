package com.gilfer.helpdesk.domain;

import com.gilfer.helpdesk.domain.dto.TecnicoDTO;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Tecnico extends Pessoa{

    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
    }

    public Tecnico(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }

    public Tecnico(TecnicoDTO dto) {
        super();
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
        //this.dataCriacao = dto.getDataCriacao();
        //this.perfis = dto.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        dto.getPerfis().stream().map(p -> this.perfis.add(p.getCodigo()));
    }
}
