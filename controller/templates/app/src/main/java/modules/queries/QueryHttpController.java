package <%= packageName %>.modules.<%= moduleName %>.queries;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("<%= apiBasePath %>")
@Slf4j
public class <%= entityName %>QueryHttpController {

    @Autowired
     private <%= entityName %>QueryService <%= entityVarName %>QueryService;

    @PostMapping("/find-<%= entityVarName %>s")
	public ResponseEntity<?> findAll<%= entityName %>s() {
		ResponseEntity<?> response = this.<%= entityVarName %>QueryService.getAll<%= entityName %>s();
		return response;
	}
	
	@PostMapping("/find-<%= entityVarName %>/<%= entityVarName %>Id/{<%= entityVarName %>Id}")
	public ResponseEntity<?> find<%= entityName %>(@PathVariable("<%= entityVarName %>Id")UUID <%= entityVarName %>Id) {
		ResponseEntity<?> response = this.<%= entityVarName %>QueryService.find<%= entityName %>(<%= entityVarName %>Id);
		return response;
	}

	<%_ if(addHistoryTable===true){ _%>
	@PostMapping("/find-<%= entityVarName %>-history/<%= entityVarName %>Id/{<%= entityVarName %>Id}")
	public ResponseEntity<?> find<%= entityName %>History(@PathVariable("<%= entityVarName %>Id")UUID parentId) {
	ResponseEntity<?> response = this.<%= entityVarName %>QueryService.get<%= entityName %>HistoryByParentId(parentId);
		return response;
	}
	<%_ } _%>
 
}
