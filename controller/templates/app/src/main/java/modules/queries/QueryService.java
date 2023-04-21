package <%= packageName %>.modules.<%= moduleName %>.queries;


import <%= packageName %>.modules.<%= moduleName %>.infrastructure.I<%= entityName %>PersistancyPort;
import java.util.List;
import <%= packageName %>.commons.converters.<%= entityName %>Mapper;
import java.util.Optional;
import java.util.UUID;
import <%= packageName %>.commons.dtos.ResponseDto;
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
public class <%= entityName %>QueryService {

    @Autowired
    private I<%= entityName %>PersistancyPort persistancyPort;

	public ResponseEntity<?> getAll<%= entityName %>s(){	
	return new ResponseEntity<ResponseDto<List<<%= entityName %>QueryResponseDto>>> (new ResponseDto<List<<%= entityName %>QueryResponseDto>>(Constants.<%= entityNameUpperCase %>_FOUND, "List of all <%= entityVarName %>s",
				<%= entityName %>Mapper.INSTANCE.<%= entityVarName %>QueryListTo<%= entityName %>QueryResponseDtoList(this.persistancyPort.getAll<%= entityName %>s())),HttpStatus.OK);	
	}
	
	public ResponseEntity<?> find<%= entityName %>(UUID id){	
	return new ResponseEntity<ResponseDto<<%= entityName %>QueryResponseDto>> (new ResponseDto<<%= entityName %>QueryResponseDto>(Constants.<%= entityNameUpperCase %>_FOUND, "<%= entityName %> found successfully",
				 <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>QueryTo<%= entityName %>QueryResponseDto(this.persistancyPort.get<%= entityName %>By<%= entityName %>Id(id))),HttpStatus.OK);	
	}

	<%_ if(addHistoryTable===true){ _%>
	public ResponseEntity<?> get<%= entityName %>HistoryByParentId(UUID parentId){	
	return new ResponseEntity<ResponseDto<List<<%= entityName %>HistoryResponseDto>>> (new ResponseDto<List<<%= entityName %>HistoryResponseDto>>(Constants.<%= entityNameUpperCase %>_FOUND, "List of all operations",
				this.persistancyPort.get<%= entityName %>History(parentId)),HttpStatus.OK);		
	}
	<%_ } _%>

}
