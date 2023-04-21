package <%= packageName %>.commons.valueobjects;
import lombok.Value;

@Value
public class <%= valueObject.valueObjectName %> {

<%_ if(typeof valueObject.targetDto !== 'undefined' && valueObject.targetDto){
    for(const dto of importModuleDtos){
        if(dto.name == valueObject.targetDto){
          for(const field of dto.fields){ _%>
             private <%- field.fieldType %> <%- field.fieldName %>;

<%_ }}} _%>  
<%_ }else{ _%> 
            private <%- valueObject.fieldTypeInDto %> <%- valueObject.fieldNameInDto %>;
            
<%_ } _%> 
}