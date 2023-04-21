package <%= packageName %>.commons.exceptions;

public class DomainException extends RuntimeException {

	public DomainException(String msg) {
		super(msg);
	}
}