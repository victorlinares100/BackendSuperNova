package BackendSuperNova.ms_cliente.service;

import BackendSuperNova.ms_cliente.model.Solicitud;
import BackendSuperNova.ms_cliente.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    public List<Solicitud> findAll()          { return solicitudRepository.findAll(); }
    public Solicitud findById(Long id)        { return solicitudRepository.findById(id).orElse(null); }
    public Solicitud save(Solicitud s)        { return solicitudRepository.save(s); }
    public void delete(Long id)              { solicitudRepository.deleteById(id); }
}