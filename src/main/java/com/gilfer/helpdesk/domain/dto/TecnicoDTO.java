package com.gilfer.helpdesk.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gilfer.helpdesk.domain.Tecnico;
import com.gilfer.helpdesk.domain.enums.Perfil;
import org.hibernate.validator.constraints.br.CPF;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TecnicoDTO implements Serializable {

    private Long id;

    @NotBlank(message = "campo NOME é requerido")
    private String nome;

    @CPF
    @NotBlank(message = "campo CPF é requerido")
    private String cpf;

    @NotBlank(message = "campo EMAIL é requerido")
    private String email;

    @NotBlank(message = "campo SENHA é requerido")
    private String senha;

    private Set<Integer> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public TecnicoDTO(){}

    public TecnicoDTO(Long id, String nome, String cpf, String email, String senha, Set<Integer> perfis, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.perfis = perfis;
        this.dataCriacao = dataCriacao;
    }

    public TecnicoDTO(Tecnico entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.senha = entity.getSenha();
        this.dataCriacao = entity.getDataCriacao();
        this.perfis = entity.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        //entity.getPerfis().stream().map(p -> this.perfis.add(p.getCodigo()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(c -> Perfil.toPerfil(c)).collect(Collectors.toSet());
    }

    public void setPerfis(Set<Integer> perfis) {
        this.perfis = perfis;
    }

    public void addPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
