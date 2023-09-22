package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        //medicoRepository.save(new Medico(datosRegistroMedico));
        //return 201 created
        //URL Donde encontrar al medico
        //GET http://localhost:8080/medicos/xx


             Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
             DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                     medico.getTelefono(), medico.getEspecialidad().toString(),
                     new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                             medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                             medico.getDireccion().getComplemento(), medico.getDireccion().getCodigoPostal(),
                             medico.getDireccion().getProvincia(), medico.getDireccion().getUrbanizacion()));
        URI url= uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

             return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    /*
        Esto devuelve una lista, pero si queremos hacer paginacion debemos retornar page
        public List<DatosListadoMedico> listadoMedicos(){
        //findAll viene de JPA
        return  medicoRepository.findAll().stream().map(DatosListadoMedico::new).toList();
    }*/
    public ResponseEntity<Page<DatosListadoMedico>>  listadoMedicos(@PageableDefault(size = 10) Pageable paginacion){
        //findAll viene de JPA
        //return  medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return  ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    //debemos retornar los datos del elemento modificado
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getCodigoPostal(),
                        medico.getDireccion().getProvincia(), medico.getDireccion().getUrbanizacion())));
    }

    //Delete logico
    //Retornamos un valor 204 No Content usando el response entity
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        //Indicamos el valor a regresar . seguido de un biuld para mostrarlo como valor http
        return  ResponseEntity.noContent().build();
    }

    /*Delete en bd
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
       medicoRepository.delete(medico);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getCodigoPostal(),
                        medico.getDireccion().getProvincia(), medico.getDireccion().getUrbanizacion()));
        return  ResponseEntity.ok(datosMedicos);
    }


}





