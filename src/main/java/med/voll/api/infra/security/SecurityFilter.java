package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
//No es un servicio ni un repositorio
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //System.out.println("Este es el inicio del Filter");
        //Para saber si funciona hay que ponerlo a prueba con un mensaje
        //System.out.println("El filtro esta siendo llamado");
        //si solo mandamos un mensaje no habra respuesta, por lo que no podremos ver los datos solicitados
        //Es por esto que debemos indicar que la cadena de filtros que recibimos debe hacer el filtro correspondiente
        //esto se logra con los parametros recibidos haciendo la llmada al siguiente filtro
        //Primero debemos obtener el token del header

        //El nombre del header donde llega el token se llama authorization
        // El prefijo no se utiliza en este caso, por lo cual lo remplazamos con el código .replace(.....)
        var authHeader = request.getHeader("Authorization");
        //System.out.println(token);
        if(token == null || token == ""){
            throw new RuntimeException("El token no es valido");
        }
        if(authHeader != null){
            //System.out.println("Validamos que token no es null");
            var token = authHeader.replace("Bearer ","");
            System.out.println(token);
            System.out.println(tokenService.getSubject(authHeader)); //Este usuario tiene sesion?
            var subject = tokenService.getSubject(token);
            System.out.println(subject);
            if (subject != null){
                //El token es valido
                //Forzamos inicio de sesion es sistema
                var usuario = usuarioRepository.findByLogin(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,
                        null, usuario.getAuthorities()); // Forzamos el inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        if(authHeader != null){
            //System.out.println("Validamos que el token no es null");
            var token = authHeader.replace("Bearer ", "");
            //System.out.println(token);
            //System.out.println(tokenService.getSubject(token));
            var nombreUsuario = tokenService.getSubject(token);//Extract Username
            if(nombreUsuario != null){
                //Token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());//Forzamos inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}*/
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del header
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token); // extract username
            if (nombreUsuario != null) {
                // Token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); // Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
