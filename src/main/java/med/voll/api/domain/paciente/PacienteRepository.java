package med.voll.api.domain.paciente;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PacienteRepository extends JpaRepositoryImplementation<Paciente, Long> {
    Page<Paciente> findByActivoTrue(Pageable paginacion);
}