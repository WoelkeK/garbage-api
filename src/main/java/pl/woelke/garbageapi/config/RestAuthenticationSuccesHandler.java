package pl.woelke.garbageapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class RestAuthenticationSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final long expirationTime;
    private final String secret;

    public RestAuthenticationSuccesHandler(
            @Value("${jwt.expirationTime}") long expirationTime,
            @Value("${jwt.secret}") String secret) {
        this.expirationTime = expirationTime;
        this.secret = secret;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        clearAuthenticationAttributes(request);

        UserDetails principal = (UserDetails) authentication.getPrincipal(); //1 pobieranie userdetails i przekazujemy do tokena
        String token = JWT.create() //2
                .withSubject(principal.getUsername()) //3
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) //4
                .sign(Algorithm.HMAC256(secret)); //5
        response.addHeader("Authorization", "Bearer" + token);

    }
}
