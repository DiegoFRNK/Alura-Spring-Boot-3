package med.voll.api.infra.errores;

public class ValidacionDentegridad extends RuntimeException {
    public ValidacionDentegridad(String s) {
        super(s);
    }
}
