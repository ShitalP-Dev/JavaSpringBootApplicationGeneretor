package <%= packageName %>.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import <%= packageName %>.commons.dtos.ResponseDto;
import <%= packageName %>.commons.utils.Utilities;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.exception.ExceptionUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exception,WebRequest request){
		log.error(ExceptionUtils.getStackTrace(exception));
     	ResponseDto<String> error = new ResponseDto<String>("500","Internal server error",request.getSessionId());
		return new ResponseEntity<ResponseDto<String>>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @ExceptionHandler(ApplicationException.class)
	public ResponseEntity<?> handleApplicationException(ApplicationException exception,WebRequest request){
		ResponseDto<String> error = new ResponseDto<String>(Utilities.getErrorMessageCode(exception.getMessage()),exception.getMessage(),request.getSessionId());
		return new ResponseEntity<ResponseDto<String>>(error,Utilities.getHttpStatusCode(exception.getMessage()));
	}

    @ExceptionHandler(InfrastructureException.class)
	public ResponseEntity<?> handleInfrastructureException(InfrastructureException exception,WebRequest request){
		ResponseDto<String> error = new ResponseDto<String>(Utilities.getErrorMessageCode(exception.getMessage()),exception.getMessage(),request.getSessionId());
		return new ResponseEntity<ResponseDto<String>>(error,Utilities.getHttpStatusCode(exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMandatoryFeildException(MethodArgumentNotValidException exception,WebRequest request) {
    	List<String> messages = new ArrayList<String>();
    	exception.getBindingResult().getAllErrors().forEach((error) ->{
    		String message = error.getDefaultMessage();
    		messages.add( message);
    	});
    	ResponseDto<String> error = new ResponseDto<String>(Utilities.getErrorMessageCode(messages.toString()), messages.toString(), request.getSessionId());
    	return new ResponseEntity<ResponseDto<String>>(error, HttpStatus.BAD_REQUEST);
}
}