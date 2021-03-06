package com.gilfer.helpdesk.services;

import com.gilfer.helpdesk.domain.Pessoa;
import com.gilfer.helpdesk.domain.Tecnico;
import com.gilfer.helpdesk.domain.dto.TecnicoDTO;
import com.gilfer.helpdesk.domain.enums.Perfil;
import com.gilfer.helpdesk.repositories.PessoaRepository;
import com.gilfer.helpdesk.repositories.TecnicoRepository;
import com.gilfer.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.gilfer.helpdesk.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public TecnicoDTO findById(Long id){
        Tecnico entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
        return new TecnicoDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<TecnicoDTO> findAll(){
        List<Tecnico> list = repository.findAll();
        return list.stream().map(t -> new TecnicoDTO(t)).collect(Collectors.toList());
    }

    @Transactional
    public TecnicoDTO create(TecnicoDTO dto){
        this.validarCpfAndEmail(dto);
        Tecnico entity = new Tecnico(dto);
        for (Perfil p: dto.getPerfis()) {
            entity.addPerfil(p);
        }
        entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        entity = repository.save(entity);
        return new TecnicoDTO(entity);
    }

    @Transactional
    public TecnicoDTO update(Long id, TecnicoDTO dto){
        dto.setId(id);
        try{
            Tecnico entity = repository.getReferenceById(id); //Validação de ID
            this.validarCpfAndEmail(dto);
            entity.setCpf(dto.getCpf());
            entity.setEmail(dto.getEmail());
            entity.setNome(dto.getNome());
            if(!entity.getSenha().equals(dto.getSenha())){
                entity.setSenha(passwordEncoder.encode(dto.getSenha()));
            }
            for(Perfil p : dto.getPerfis()){
                entity.addPerfil(p);
            }
            return new TecnicoDTO(entity);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Entidade não encontrada");
        }catch (org.springframework.dao.DataIntegrityViolationException d){
            throw new DataIntegrityViolationException("O tecnico possui chamados atrelado a ele");
        }
    }

    public void validarCpfAndEmail(TecnicoDTO dto){
        Optional<Pessoa> entity = pessoaRepository.findByCpf(dto.getCpf());
        if(entity.isPresent() && entity.get().getId() != dto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        entity = pessoaRepository.findByEmail(dto.getEmail());
        if(entity.isPresent() && entity.get().getId() != dto.getId()){
            throw new DataIntegrityViolationException("Email já cadastrado");
        }
    }
}
