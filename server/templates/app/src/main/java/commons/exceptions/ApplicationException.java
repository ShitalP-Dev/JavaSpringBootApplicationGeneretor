package <%= packageName %>.commons.exceptions;

public class ApplicationException extends RuntimeException {

	public ApplicationException(String msg) {
		super(msg);
	}
}