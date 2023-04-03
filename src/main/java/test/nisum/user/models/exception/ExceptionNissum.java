package test.nisum.user.models.exception;

import org.springframework.http.HttpStatus;

public class ExceptionNissum extends Exception{

	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;;
	private static final long serialVersionUID = 6900159478767780202L;
	public static final String USER_DUPLICATE = "El correo ya se encuentra registrado";
	public final static String MESSAGE_ERROR = "Se presento un error procesado la solicitud";
	public final static String USER_NO = "No se tiene usuarios ";
	public final static String USER_NO_EXISTE = "El usuario ingresado no existe";
	public final static String TOKEN_USER_INCORRECTO= "Token con usuario incorrecto";
	public final static String TOKEN_NO_VALIDO= "El token no es valido";
	public final static String CAMPO_NO_VALIDO= "El valor ingresado no cumple con los requerimientos necesarios";
	

	public ExceptionNissum() {
		super();
	}

	public ExceptionNissum(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExceptionNissum(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionNissum(String message) {
		super(message);
	}
	
	public ExceptionNissum(String message, HttpStatus status){
		super(message);
		this.status = status;
	}

	public ExceptionNissum(Throwable cause) {
		super(cause);
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	
	

}
