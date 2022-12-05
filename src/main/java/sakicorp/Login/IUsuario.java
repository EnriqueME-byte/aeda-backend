package sakicorp.Login;

import java.time.LocalDateTime;

public interface IUsuario {
	String getLogin();
	String getNombre();
	String getPassword();
	LocalDateTime getFecha_Vigencia();
}
