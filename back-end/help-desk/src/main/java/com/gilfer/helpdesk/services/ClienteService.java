package com.gilfer.helpdesk.services;

import com.gilfer.helpdesk.domain.Pessoa;
import com.gilfer.helpdesk.domain.Cliente;
import com.gilfer.helpdesk.domain.dto.ClienteDTO;
import com.gilfer.helpdesk.domain.enums.Perfil;
import com.gilfer.helpdesk.repositories.PessoaRepository;
import com.gilfer.helpdesk.repositories.ClienteRepository;
import com.gilfer.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.gilfer.helpdesk.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id){
        Cliente entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
        return new ClienteDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll(){
        List<Cliente> list = repository.findAll();
        return list.stream().map(t -> new ClienteDTO(t)).collect(Collectors.toList());
    }

    @Transactional
    public ClienteDTO create(ClienteDTO dto){
        this.validarCpfAndEmail(dto);
        System.out.println(dto.getPerfis().toString());
        Cliente entity = new Cliente(dto);
        System.out.println(entity.getPerfis().toString());
        entity = repository.save(entity);
        return new ClienteDTO(entity);
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto){
        dto.setId(id);
        try{
            Cliente entity = repository.getReferenceById(id); //Validação de ID
            this.validarCpfAndEmail(dto);
            entity.setCpf(dto.getCpf());
            entity.setEmail(dto.getEmail());
            entity.setNome(dto.getNome());
            entity.setSenha(dto.getSenha());
            for(Perfil p : dto.getPerfis()){
                entity.addPerfil(p);
            }
            return new ClienteDTO(entity);
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

    public void validarCpfAndEmail(ClienteDTO dto){
        Optional<Pessoa> entity = pessoaRepository.findByCpf(dto.getCpf());
        if(entity.isPresent() && entity.get().getCpf() != dto.getCpf()){
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        entity = pessoaRepository.findByEmail(dto.getEmail());
        if(entity.isPresent() && entity.get().getEmail() != dto.getEmail()){
            throw new DataIntegrityViolationException("Email já cadastrado");
        }
    }
}
