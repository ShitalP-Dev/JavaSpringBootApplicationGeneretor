package <%= packageName %>.modules.<%= moduleName %>.infrastructure.exceptions;

import <%= packageName %>.commons.exceptions.InfrastructureException;

public class FieldNotFoundException extends InfrastructureException {

	public FieldNotFoundException(String msg) {
		   super(msg);
	   }
}