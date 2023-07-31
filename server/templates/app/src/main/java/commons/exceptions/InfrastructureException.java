package <%= packageName %>.commons.exceptions;

public class InfrastructureException extends RuntimeException {

	public InfrastructureException(String msg) {
		super(msg);
	}
}
