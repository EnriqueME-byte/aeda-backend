package sakicorp.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import sakicorp.Login.IUsuario;
import sakicorp.services.IUsuarioService;

public class JWTCustom implements TokenEnhancer{

	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String,Object> map = new HashMap<>();
		IUsuario usuario = usuarioService.getUsuario(authentication.getName());
		map.put("nombre", usuario.getNombre());
		map.put("fecha", usuario.getFecha_Vigencia());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
		return accessToken;
	}

}
