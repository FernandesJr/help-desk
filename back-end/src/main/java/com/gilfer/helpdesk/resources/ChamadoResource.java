package com.gilfer.helpdesk.resources;

import com.gilfer.helpdesk.domain.Chamado;
import com.gilfer.helpdesk.domain.Cliente;
import com.gilfer.helpdesk.domain.Tecnico;
import com.gilfer.helpdesk.domain.dto.ChamadoDTO;
import com.gilfer.helpdesk.domain.enums.Prioridade;
import com.gilfer.helpdesk.domain.enums.Status;
import com.gilfer.helpdesk.repositories.ClienteRepository;
import com.gilfer.helpdesk.repositories.TecnicoRepository;
import com.gilfer.helpdesk.services.ChamadoService;
import com.gilfer.helpdesk.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chamados")
public class ChamadoResource {

    @Autowired
    private ChamadoService service;

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Long id) {
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<Chamado> list = service.findAll();
        List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO obj) {
        Chamado newObj = service.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Long id, @Valid @RequestBody ChamadoDTO objDTO) {
        Chamado newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ChamadoDTO(newObj));
    }



}
