package <%= packageName %>.modules.<%= moduleName %>.infrastructure.exceptions;

import <%= packageName %>.commons.exceptions.InfrastructureException;

public class DuplicateEntryException extends InfrastructureException {

	public DuplicateEntryException(String msg) {
		   super(msg);
	   }
}