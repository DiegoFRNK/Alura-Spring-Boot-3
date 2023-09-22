package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDentegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    //Inyectarmos como atributos las clases de reposuitorio
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;



    public void agendar(DatosAgendarConsulta datos){

        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDentegridad("El id del paciente no fue encotrado");
        }
        if(datos.idMedico() != null && medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDentegridad("El id del medico no fue encontrado");
        }

        //Asignamos un elemento de tipo paciente dentro de la variable paciente
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        // Repetimos lo anterior para la variable medico
        //var medico = medicoRepository.findById(datos.idMedico()).get();
        var medico = selecionaMedico(datos);

        //entidad
        var consula = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepository.save(consula);

    }

    private Medico selecionaMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad()==null){
            throw new ValidacionDentegridad("Debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }
}
