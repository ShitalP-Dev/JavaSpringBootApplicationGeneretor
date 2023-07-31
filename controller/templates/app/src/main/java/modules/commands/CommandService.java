package <%= packageName %>.modules.<%= moduleName %>.commands;

import <%= packageName %>.modules.<%= moduleName %>.infrastructure.I<%= entityName %>PersistancyPort;
import <%= packageName %>.modules.<%= moduleName %>.infrastructure.<%= entityName %>OrmEntity;
import <%= packageName %>.commons.dtos.ResponseDto;
import <%= packageName %>.commons.converters.<%= entityName %>Mapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import <%= packageName %>.commons.utils.Constants;
import <%= packageName %>.commons.utils.Utilities;
<%_ const entityNameUpperCase = entityName.toUpperCase(); _%>  

@Service
@Transactional
public class <%= entityName %>CommandService {

    @Autowired
    private I<%= entityName %>PersistancyPort persistancyPort;

    public ResponseEntity<?> save<%= entityName %>(<%= entityName %>Command <%= entityVarName %>Command) {
         <%= entityVarName %>Command = persistancyPort.save<%= entityName %>(<%= entityName %>Mapper.INSTANCE.<%= entityVarName %>CommandTo<%= entityName %>OrmEntity(<%= entityVarName %>Command));
         return new ResponseEntity<ResponseDto<UUID>> (new ResponseDto<UUID>(Constants.<%= entityNameUpperCase %>_ADDED_SUCCESSFULLY, "<%= entityName %> created successfully",
	    		<%= entityVarName %>Command.getId()),HttpStatus.CREATED);	
    }

    public ResponseEntity<?> edit<%= entityName %>(<%= entityName %>Command <%= entityVarName %>Command) {
         <%= entityVarName %>Command = persistancyPort.edit<%= entityName %>(<%= entityName %>Mapper.INSTANCE.<%= entityVarName %>CommandTo<%= entityName %>OrmEntity(<%= entityVarName %>Command));
         return new ResponseEntity<ResponseDto<UUID>> (new ResponseDto<UUID>(Constants.<%= entityNameUpperCase %>_EDITED_SUCCESSFULLY, "<%= entityName %> updated successfully",
	    		<%= entityVarName %>Command.getId()),HttpStatus.CREATED);	
    }

    public ResponseEntity<?> delete<%= entityName %>(UUID id) {
        persistancyPort.delete<%= entityName %>By<%= entityName %>Id(id);
        return new ResponseEntity <ResponseDto<UUID>>(new ResponseDto<UUID>(Constants.<%= entityNameUpperCase %>_DELETED, "<%= entityName %> deleted successfully", id),HttpStatus.OK);	
    }

     /* 
    //This method will be used when we will introduce domain layer

    public ResponseEntity<?> save(<%= entityName %>Command <%= entityVarName %>Command) {
        <%= entityName %>Entity <%= entityVarName %>Entity = <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>CommandTo<%= entityName %>Entity(<%= entityVarName %>Command);
        <%= entityVarName %>Entity.somebusinesslogic();
        <%= entityVarName %>Entity = persistancyPort.save(<%= entityName %>Mapper.INSTANCE.<%= entityVarName %>EntityTo<%= entityName %>OrmEntity(<%= entityVarName %>Entity));
        return new ResponseEntity<ResponseDto<UUID>> (new ResponseDto<UUID>(HttpStatus.CREATED, "<%= entityName %> created successfully",
	    		<%= entityName %>Mapper.INSTANCE.<%= entityVarName %>EntityTo<%= entityName %>Command(<%= entityVarName %>).getId()),HttpStatus.CREATED);	
    }
    */
}
