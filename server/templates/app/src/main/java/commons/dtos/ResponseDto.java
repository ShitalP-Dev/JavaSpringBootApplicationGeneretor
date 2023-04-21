package <%= packageName %>.commons.dtos;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {

    private String code;
	
	private String message;
    
	private T result;
}