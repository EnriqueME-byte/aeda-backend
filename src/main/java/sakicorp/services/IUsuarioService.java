package sakicorp.services;

import java.util.List;

import sakicorp.Login.IUsuario;
import sakicorp.models.Usuario;

public interface IUsuarioService {

	
	public List<Usuario> getUsuariosPoFecha(String fechaInicial, String fechaFinal);
	
	public List<Usuario> getUsuarioPorStatus(Character status);
	
	public List<Usuario> getUsuariosPorNombre(String nombre);
	
	public boolean crearUsuario(Usuario u);
	
	public boolean modificarUsuario(Usuario u);
	
	public boolean eliminarUsuario(String id);
	
	public IUsuario getUsuario(String login);
}
