package com.gilfer.helpdesk.resources;

import com.gilfer.helpdesk.domain.dto.TecnicoDTO;
import com.gilfer.helpdesk.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    @GetMapping("{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Long id){
        TecnicoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){
        List<TecnicoDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}
