package <%= packageName %>.commons.utils;

import org.springframework.http.HttpStatus;
<%_ const entityNameUpperCase = function(str){
    return str.toUpperCase();
} _%>  

public class Utilities {
	
	public static String getErrorMessageCode(String errorMessage) {
			
		switch (errorMessage) {
            
            case "email must not be empty or null":
			case "last_name must not be empty or null":
			case "first_name must not be empty or null":
			case "address must not be empty or null":
            case "full_address must not be empty or null":
            case "city must not be empty or null":
            case "state must not be empty or null":
            case "country must not be empty or null":
            case "id is missing":
			case "Field missing":
				return Constants.REQUIRED_FIELD_MISSING;

            case "Email is not in Email ID format":
			case "Email value must have proper format":
				return Constants.INVALID_EMAIL_ID;

			case "Invalid Field Length":	
			case "first_name field length should not exceed 50 characters":
			case "last_name field length should not exceed 50 characters":
            case "full_address field length should not exceed 50 characters":
            case "city field length should not exceed 50 characters":
            case "state field length should not exceed 50 characters":
            case "country field length should not exceed 50 characters":
				return Constants.INVALID_FILED_LENGTH;

            case "address must not be same":
			case "Duplicate entry":
			case "Duplicate entry - email":
			case "Duplicate entry - address":
            case "User already exists":
				return Constants.DUPLICATE_ENTRY;

            case "Method value must not be other then get,post,put or delete":
				return Constants.INVALID_FIELD_VALUE;

			<%_  for(let module of metadata.modules){ _%>
            <%_  for(let entity of module.entities){ _%>
        	<%_  if(entity.attributeClass===false){ _%>
			case "<%= entity.className %> not found":
				return Constants.<%= entityNameUpperCase(entity.className) %>_NOT_FOUND;
			<%_ }}} _%> 

            case "Empty Request Body":
				return Constants.EMPTY_REQUEST_BODY;

            default:
				return Constants.INTERNAL_SERVER_ERROR;
        }
    }


	public static HttpStatus getHttpStatusCode(String errorMessage) {
			
		switch (errorMessage) {
            
            case "email must not be empty or null":
			case "last_name must not be empty or null":
			case "first_name must not be empty or null":
			case "address must not be empty or null":
            case "full_address must not be empty or null":
            case "city must not be empty or null":
            case "state must not be empty or null":
            case "country must not be empty or null":
			case "Email is not in Email ID format":
			case "Email value must have proper format":
			case "Invalid Field Length":	
			case "first_name field length should not exceed 50 characters":
			case "last_name field length should not exceed 50 characters":
            case "full_address field length should not exceed 50 characters":
            case "city field length should not exceed 50 characters":
            case "state field length should not exceed 50 characters":
            case "country field length should not exceed 50 characters":
			case "id is missing":
				return HttpStatus.BAD_REQUEST;

            case "address must not be same":
			case "Duplicate entry":
			case "Duplicate entry - email":
			case "Duplicate entry - address":
			<%_  for(let module of metadata.modules){ _%>
            <%_  for(let entity of module.entities){ _%>
        	<%_  if(entity.attributeClass===false){ _%>
			case "<%= entity.className %> already exists":
			<%_ }}} _%>
				return HttpStatus.CONFLICT;

		    <%_  if(metadata.modules.length !== 0){ _%>
			<%_  for(let module of metadata.modules){ _%>
            <%_  for(let entity of module.entities){ _%>
        	<%_  if(entity.attributeClass===false){ _%>
			case "<%= entity.className %> not found":
			<%_ }}} _%>
				return HttpStatus.NOT_FOUND;
			<%_ } _%>

            case "Empty Request Body":
			case "Unsupported media type":
			case "JSON error":
				return HttpStatus.UNSUPPORTED_MEDIA_TYPE;

            default:
				return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}