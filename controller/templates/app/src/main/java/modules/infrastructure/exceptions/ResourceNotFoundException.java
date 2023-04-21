package <%= packageName %>.modules.<%= moduleName %>.infrastructure.exceptions;

import <%= packageName %>.commons.exceptions.InfrastructureException;

public class ResourceNotFoundException extends InfrastructureException {

	public ResourceNotFoundException(String msg) {
		   super(msg);
	   }
}