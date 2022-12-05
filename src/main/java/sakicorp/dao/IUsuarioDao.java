package sakicorp.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sakicorp.Login.IUsuario;
import sakicorp.models.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario,String>{

	@Query(value = "SELECT login, nombre, email, fecha_vigencia, password FROM usuarios WHERE login = ?1", nativeQuery = true)
	public IUsuario login(String login);
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.fechaAlta >= ?1 AND u.fechaAlta <= ?2" )
	public List<Usuario> getUsuariosPorFecha(LocalDateTime fechaInicial, LocalDateTime fechaFinal); 
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.status = ?1")
	public List<Usuario> getUsuarioPorStatus(Character status);
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre%")
	public List<Usuario> getUsuariosPorNombre(@Param("nombre") String nombre);
	
	
}
