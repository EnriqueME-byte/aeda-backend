package sakicorp.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sakicorp.models.Usuario;


public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter{

	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	private static final Logger log = LoggerFactory.getLogger(JWTAuthFilter.class);
	
	public JWTAuthFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/usuarios/login", "POST"));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		if(login != null && password != null) {
			log.info("LOGIN: " + login);
			log.info("PASS: " + password);
		}else {
			Usuario user  = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
				login = user.getLogin();
				password = user.getPassword();
				log.info("USUARIO[raw]: " + login);
				log.info("PASS[raw]: " + password);
			} catch (StreamReadException e) {
				e.printStackTrace();
			} catch (DatabindException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		login = login.trim();
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login,password);
		
		return authenticationManager.authenticate(authToken);
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		Claims claims = Jwts.claims();
		JwtBuilder builder = null; 
		builder = Jwts.builder()
				.setClaims(claims)
				.setSubject(authResult.getName())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 900000000L))
				.signWith(SignatureAlgorithm.HS512, "llaveSecreta".getBytes());
		String token = builder.compact();
		response.addHeader("Authorization", "Bearer " + token);
		Map<String,Object> body = new HashMap<>();
		body.put("token", token);
		body.put("User", (User)authResult.getPrincipal());
		body.put("status", "success");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String,Object> body = new HashMap<>();
		body.put("mensaje", "Usuario y/o password incorrectos");
		body.put("status", "error");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}
	
}
