package <%= packageName %>.modules.<%= moduleName %>.commands;

import <%= packageName %>.commons.converters.<%= entityName %>Mapper;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("<%= apiBasePath %>")
@Slf4j
public class <%= entityName %>CommandHttpController {

    @Autowired
     private <%= entityName %>CommandService <%= entityVarName %>CommandService;

	<%_ for(const method of aggregateRoot.businessMethods) { _%>
	@PostMapping("/<%= method.name %>")
    public ResponseEntity<?> <%= method.name %>(@Validated @RequestBody <%= entityName %>CommandRequestDto <%= entityVarName %>RequestDto) {
		<%= entityName %>Command <%= entityVarName %>Command = <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>CommandRequestDtoTo<%= entityName %>Command(<%= entityVarName %>RequestDto);
		ResponseEntity<?> response= this.<%= entityVarName %>CommandService.<%= method.name %>(<%= entityVarName %>Command);
		return response;
    }
	<%_ } _%>
 
}
