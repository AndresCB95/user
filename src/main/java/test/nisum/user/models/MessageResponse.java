package test.nisum.user.models;

public class MessageResponse {
	
	public final static String USER_DELETE = "Usuario Eliminado";
	private String mensaje;

	public MessageResponse(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
