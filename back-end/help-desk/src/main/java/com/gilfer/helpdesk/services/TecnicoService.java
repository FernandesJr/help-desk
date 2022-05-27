package com.gilfer.helpdesk.services;

import com.gilfer.helpdesk.domain.Tecnico;
import com.gilfer.helpdesk.domain.dto.TecnicoDTO;
import com.gilfer.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    //@Transactional(readOnly = true)
    public TecnicoDTO findById(Long id){
        Tecnico entity = repository.findById(id).orElse(null);
        return new TecnicoDTO(entity);
    }
}
