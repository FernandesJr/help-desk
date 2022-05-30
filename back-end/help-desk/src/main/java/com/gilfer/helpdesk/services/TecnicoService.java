package com.gilfer.helpdesk.services;

import com.gilfer.helpdesk.domain.Tecnico;
import com.gilfer.helpdesk.domain.dto.TecnicoDTO;
import com.gilfer.helpdesk.repositories.TecnicoRepository;
import com.gilfer.helpdesk.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

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
}
