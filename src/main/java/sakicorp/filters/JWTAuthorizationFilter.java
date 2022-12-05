package sakicorp.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader("Authorization");
		
		if(!requireAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}
		
		boolean validToken;
		Claims token = null;
		try {
			token = Jwts.parser()
			.setSigningKey("llaveSecreta".getBytes())
			.parseClaimsJws(header.replace("Bearer ", "")).getBody();
			validToken = true;
		}catch(JwtException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			validToken = false;
		}
		
		UsernamePasswordAuthenticationToken authentication = null;
		if(validToken) {
			String username = token.getSubject();
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	protected boolean requireAuthentication(String header) {
		return (header == null || !header.startsWith("Bearer ")) ?  false : true;
	}

}
