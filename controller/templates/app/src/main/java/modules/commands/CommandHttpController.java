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

    @PostMapping("/create-<%= entityVarName %>")
	public ResponseEntity<?> save<%= entityName %>(@Validated @RequestBody <%= entityName %>CommandRequestDto <%= entityVarName %>ReqDto){
		<%= entityName %>Command <%= entityVarName %>Command= <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>CommandRequestDtoTo<%= entityName %>Command(<%= entityVarName %>ReqDto);
		ResponseEntity<?> response = this.<%= entityVarName %>CommandService.save<%= entityName %>(<%= entityVarName %>Command);
		return response;
	}
	
	@PostMapping("/edit-<%= entityVarName %>")
	public ResponseEntity<?> edit<%= entityName %>(@Validated @RequestBody <%= entityName %>CommandRequestDto <%= entityVarName %>ReqDto){
		<%= entityName %>Command <%= entityVarName %>Command= <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>CommandRequestDtoTo<%= entityName %>Command(<%= entityVarName %>ReqDto);
		ResponseEntity<?> response = this.<%= entityVarName %>CommandService.edit<%= entityName %>(<%= entityVarName %>Command);
		return response;
	}

    @PostMapping("/delete-<%= entityVarName %>/<%= entityVarName %>Id/{<%= entityVarName %>Id}")
    public ResponseEntity<?> delete<%= entityName %>(@PathVariable("<%= entityVarName %>Id")UUID <%= entityVarName %>Id) {
		ResponseEntity<?> response= this.<%= entityVarName %>CommandService.delete<%= entityName %>(<%= entityVarName %>Id);
		return response;
    }
 
}
