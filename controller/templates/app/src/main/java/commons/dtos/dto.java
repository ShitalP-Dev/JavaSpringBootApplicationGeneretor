package <%= packageName %>.commons.dtos;

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
public class <%= importModuleDtos.name %>{
 <%_ for(const field of importModuleDtos.fields) { _%>
    private <%- field.fieldType %> <%- field.fieldName %>;

 <%_ } _%>  
}