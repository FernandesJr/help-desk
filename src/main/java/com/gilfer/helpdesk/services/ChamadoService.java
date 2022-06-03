package com.gilfer.helpdesk.services;

import com.gilfer.helpdesk.domain.Chamado;
import com.gilfer.helpdesk.domain.Cliente;
import com.gilfer.helpdesk.domain.Tecnico;
import com.gilfer.helpdesk.domain.dto.ChamadoDTO;
import com.gilfer.helpdesk.domain.enums.Prioridade;
import com.gilfer.helpdesk.domain.enums.Status;
import com.gilfer.helpdesk.repositories.ChamadoRepository;
import com.gilfer.helpdesk.repositories.ClienteRepository;
import com.gilfer.helpdesk.repositories.TecnicoRepository;
import com.gilfer.helpdesk.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Chamado findById(Long id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(ChamadoDTO obj) {
        return repository.save(newChamado(obj));
    }

    public Chamado update(Long id, ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDTO);
        return repository.save(oldObj);
    }

    private Chamado newChamado(ChamadoDTO obj) {
        Tecnico tecnico = tecnicoRepository.findById(obj.getTecnico()).orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
        Cliente cliente = clienteRepository.findById(obj.getCliente()).orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));

        Chamado chamado = new Chamado();
        if(obj.getId() != null) {
            chamado.setId(obj.getId());
        }

        if(obj.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDateTime.now());
        }

        if(obj.getDataAbertura() != null){
            chamado.setDataAbertura(obj.getDataAbertura());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toPerfil(obj.getStatus()));
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;
    }

}
