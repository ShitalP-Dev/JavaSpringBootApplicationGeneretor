package <%= packageName %>.modules.<%= moduleName %>.commands;

import <%= packageName %>.commons.dtos.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class <%= entityName %>Command extends BaseDto{

 <%_ for(const field of aggregateRoot.fields) { _%>
 <%_ if (typeof field.referenceFields !== 'undefined' && field.referenceFields.length > 0) { _%>
 <%_ for(const refField of field.referenceFields) { _%>
 private <%- field.fieldType %> <%- refField %>;

 <%_ } _%>   
 <%_ }else{ _%> 
 <%_ if (typeof field.isValueObject !== 'undefined' && field.isValueObject === true) { _%>
 private <%- field.fieldTypeInDto %> <%- field.fieldNameInDto %>;

 <%_ }else{ _%> 
 private <%- field.fieldType %> <%- field.fieldName %>;

 <%_ } _%> 
 <%_ } _%> 
 <%_ } _%>
}