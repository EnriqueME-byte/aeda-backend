package sakicorp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sakicorp.dtos.ResponseDTO;
import sakicorp.models.Usuario;
import sakicorp.services.IUsuarioService;

@RestController
@RequestMapping("api")
public class UsuarioController {

	@Autowired
	private IUsuarioService us;
	
	@GetMapping("usuarios/{fechaInicial}/{fechaFinal}")
	public ResponseEntity<ResponseDTO> getUsuariosPorFecha(@PathVariable String fechaInicial, @PathVariable String fechaFinal){
		ResponseDTO respuesta = new ResponseDTO();
		List<Usuario> data = us.getUsuariosPoFecha(fechaInicial, fechaFinal);
		if(data == null) {
			respuesta.setStatus("error");
			respuesta.setError_msg("No se encontraron resultados");
			respuesta.setData(new ArrayList<>());
		}else {
			respuesta.setStatus("success");
			respuesta.setData(data);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("usuarios/{status}")
	public ResponseEntity<ResponseDTO> getUsuariosPorStatus(@PathVariable Character status){
		ResponseDTO respuesta = new ResponseDTO();
		List<Usuario> data = us.getUsuarioPorStatus(status);
		if(data == null) {
			respuesta.setStatus("error");
			respuesta.setError_msg("No se encontraron resultados");
			respuesta.setData(new ArrayList<>());
		}else {
			respuesta.setStatus("success");
			respuesta.setData(data);			
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@PostMapping("usuarios/registro")
	public ResponseEntity<ResponseDTO> crearUsuario(@RequestBody Usuario u){
		ResponseDTO respuesta = new ResponseDTO();
		if(us.crearUsuario(u)) {
			respuesta.setStatus("success");
			respuesta.setMsg("Usuario registrado correctamente");
		}
		else {
			respuesta.setStatus("error");
			respuesta.setError_msg("Error al crear usuario");
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("usuarios/buscar")
	public ResponseEntity<ResponseDTO> getUsuariosPorNombre(@RequestParam String nombre){
		ResponseDTO respuesta = new ResponseDTO();
		List<Usuario> data = us.getUsuariosPorNombre(nombre);
		if(data == null)
			respuesta.setMsg("No se encontraron usuarios");
		respuesta.setStatus("success");
		respuesta.setData(data);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@PutMapping("usuarios")
	public ResponseEntity<ResponseDTO> modificarUsuario(@RequestBody Usuario u){
		ResponseDTO respuesta = new ResponseDTO();
		if(us.modificarUsuario(u)) {
			respuesta.setMsg("Usuario modificado correctamente");
			respuesta.setStatus("success");
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		}
		else {
			respuesta.setMsg("Error al modificar usuario");
			respuesta.setStatus("error");
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@DeleteMapping("usuarios")
	public ResponseEntity<ResponseDTO> eliminarUsuario(@RequestParam String id){
		ResponseDTO respuesta = new ResponseDTO();
		if(us.eliminarUsuario(id)) {
			respuesta.setMsg("Usuario eliminado correctamente");
			respuesta.setStatus("success");
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		}
		else {
			respuesta.setMsg("Error al eliminar usuario");
			respuesta.setStatus("error");
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
}
