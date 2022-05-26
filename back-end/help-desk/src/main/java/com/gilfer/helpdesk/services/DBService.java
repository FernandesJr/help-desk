package com.gilfer.helpdesk.services;

import com.gilfer.helpdesk.domain.Chamado;
import com.gilfer.helpdesk.domain.Cliente;
import com.gilfer.helpdesk.domain.Tecnico;
import com.gilfer.helpdesk.domain.enums.Prioridade;
import com.gilfer.helpdesk.domain.enums.Status;
import com.gilfer.helpdesk.repositories.ChamadoRepository;
import com.gilfer.helpdesk.repositories.ClienteRepository;
import com.gilfer.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    public void instanciaDB(){
        Tecnico tecnico1 = new Tecnico(null, "Fernandes", "52545854584",
                "fernandes@email.com", "123");

        Cliente cliente1 = new Cliente(null, "Teca", "145847447458",
                "teca@email.com", "123");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "PC não liga", "Já fiz de tudo",
                cliente1, tecnico1);

        tecnicoRepository.saveAll(Arrays.asList(tecnico1));
        clienteRepository.saveAll(Arrays.asList(cliente1));
        chamadoRepository.saveAll(Arrays.asList(c1));
    }
}
