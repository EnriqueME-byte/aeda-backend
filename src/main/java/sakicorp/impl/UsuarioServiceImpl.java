package sakicorp.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sakicorp.Login.IUsuario;
import sakicorp.dao.IUsuarioDao;
import sakicorp.models.Usuario;
import sakicorp.services.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

	private static Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private IUsuarioDao udao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public List<Usuario> getUsuariosPoFecha(String fechaInicial, String fechaFinal) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");		
		LocalDateTime ini = LocalDateTime.parse(fechaInicial, formatter);
		LocalDateTime fin = LocalDateTime.parse(fechaFinal, formatter);
		List<Usuario> data = udao.getUsuariosPorFecha(ini, fin);
		return (data.size() > 0) ? data : null;
	}

	@Override
	public List<Usuario> getUsuarioPorStatus(Character status) {
		try {
			List<Usuario> data = udao.getUsuarioPorStatus(status);
			return (data.size() > 0 ) ? data :  null;
		}catch(Exception e) {
			log.error("Erro en la consulta: " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean crearUsuario(Usuario u) {
		try {
			PasswordEncoder encoder = passwordEncoder;
			String password = encoder.encode(u.getPassword());
			u.setStatus('A');
			u.setPassword(password);
			udao.save(u);
			return true;
		}catch(Exception e) {
			log.error("Error: " + e.getMessage());
		}
		return false;
	}


	@Override
	public List<Usuario> getUsuariosPorNombre(String nombre) {
		try {
			List<Usuario> data = udao.getUsuariosPorNombre(nombre);
			return (data.size() > 0) ? data : null;
		}catch(Exception e) {
			log.error("Error en la consulta: " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean modificarUsuario(Usuario u) {
		try {
			Optional<Usuario> use = udao.findById(u.getLogin());
			if(use.isEmpty()) {
				log.info("Usuario no encontrado");
				return false;
			}
			use.get().setNombre(u.getNombre());
			use.get().setApellidoMaterno(u.getApellidoMaterno());
			use.get().setApellidoPaterno(u.getApellidoPaterno());
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
	        String date = dateFormat.format(Calendar.getInstance().getTime());
	        System.out.println(date);
	        Date d = dateFormat.parse(date);
	        System.out.println(d);
			use.get().setFechaModificacion(LocalDateTime.now());
			use.get().setStatus(u.getStatus());
			udao.save(use.get());
			return true;
		}catch(Exception e) {
			log.error("Error en la transaccion: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean eliminarUsuario(String id) {
		try {
			Optional<Usuario> use = udao.findById(id);
			if(use.isEmpty()) {
				log.info("Usuario no encontrado");
				return false;
			}
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
	        String date = dateFormat.format(Calendar.getInstance().getTime());
	        System.out.println(date);
	        Date d = dateFormat.parse(date);
	        System.out.println(d);
			use.get().setFechaModificacion(LocalDateTime.now());
			use.get().setStatus('B');
			udao.save(use.get());
			return true;
		}catch(Exception e) {
			log.error("Error en la transaccion: " + e.getMessage());
		}
		return false;

	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		IUsuario usuario = udao.login(username);
		System.out.println("LOGEAR: " + username);
		if(usuario != null) {				
			/*LocalDateTime d = usuario.getFecha_Vigencia();
			if(d.isBefore(LocalDateTime.now())) {
				log.error("fecha expirada");
			}*/
				
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			return new User(usuario.getLogin(), usuario.getPassword(),true,true,true,true,authorities);
		}
		return null;
	}

	@Override
	public IUsuario getUsuario(String login) {
		return udao.login(login);
	}
	
	
}