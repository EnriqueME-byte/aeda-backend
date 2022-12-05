package sakicorp.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;




@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(unique = true, length = 20)
	private String login;
	
	//@Column(length = 30)
	private String password;
	
	@Column(length = 50)
	private String nombre;
	
	private Float cliente;
	
	@Column(unique = true, length = 50)
	private String email;
	
	@JsonFormat(pattern = "yyyy-M-dd hh:mm:ss")
	@CreationTimestamp
	private LocalDateTime fechaAlta;
	
	@JsonFormat(pattern = "yyyy-M-dd hh:mm:ss")
	private LocalDateTime fechaBaja;
	
	private Character status;
	
	private Integer intentos;
	
	@JsonFormat(pattern = "yyyy-M-dd hh:mm:ss")
	private LocalDateTime fechaRevocado;
	
	@JsonFormat(pattern = "yyyy-M-dd hh:mm:ss")
	private LocalDateTime fechaVigencia;
	
	private Integer noAcceso;
	
	@Column(length = 50)
	private String apellidoPaterno;
	
	@Column(length = 50)
	private String apellidoMaterno;
	
	private Float area;
	
	@JsonFormat(pattern = "yyyy-M-dd hh:mm:ss")
	@CreationTimestamp
	private LocalDateTime fechaModificacion;
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getCliente() {
		return cliente;
	}

	public void setCliente(Float cliente) {
		this.cliente = cliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public Integer getIntentos() {
		return intentos;
	}

	public void setIntentos(Integer intentos) {
		this.intentos = intentos;
	}

	public LocalDateTime getFechaRevocado() {
		return fechaRevocado;
	}

	public void setFechaRevocado(LocalDateTime fechaRevocado) {
		this.fechaRevocado = fechaRevocado;
	}

	public LocalDateTime getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(LocalDateTime fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public Integer getNoAcceso() {
		return noAcceso;
	}

	public void setNoAcceso(Integer noAcceso) {
		this.noAcceso = noAcceso;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}	
	
}
