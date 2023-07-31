package <%= packageName %>.modules.<%= moduleName %>.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class <%= entityName %>Dto {
<%_ for(const field of fields) { _%>
     <%_ if (field.validation) { _%>
    <%_ if (field.validation.notEmpty) { _%>
    @NotEmpty(message = "<%= field.fieldName %> must not be empty or null")
    <%_ } _%>
    <%_ if (field.validation.isEmail) { _%>
    @Email(regexp="<%= field.validation.regExp %>",message="'${validatedValue}' is not in Email ID format")
     <%_ }else{ _%>
    @Pattern(regexp="<%= field.validation.regExp %>")
    @Length(max=<%= field.validation.length %>,message = "<%= field.fieldName %> field length should not exceed 50 characters")
    <%_ } _%>
    <%_ } _%>
    private <%= field.fieldType %> <%= field.fieldName %>;

<%_ } _%>
 <%_ for(const relationship of relationships) { _%>
    <%_ if (relationship.relationshipType === "one-to-many") { _%>
    <%_ if (relationship.validation) { _%>
    <%_ if (relationship.validation.notNull) { _%>
    @NotNull(message = "<%= relationship.relationshipName %> must not be empty or null")
    <%_ } _%>
    <%_ if (relationship.validation.isValid) { _%>
    @Valid
    <%_ } _%>
    <%_ } _%>
    private List<<%= relationship.otherEntityName %>Dto> <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-one") { _%>
    <%_ if (relationship.validation) { _%>
    <%_ if (relationship.validation.notNull) { _%>
    @NotNull(message = "<%= relationship.relationshipName %> must not be empty or null")
    <%_ } _%>
    <%_ if (relationship.validation.isValid) { _%>
    @Valid
    <%_ } _%>
    <%_ } _%>
    private <%= relationship.otherEntityName %>Dto <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-many") { _%>
    <%_ if (relationship.validation) { _%>
    <%_ if (relationship.validation.notNull) { _%>
    @NotNull(message = "<%= relationship.relationshipName %> must not be empty or null")
    <%_ } _%>
    <%_ if (relationship.validation.isValid) { _%>
    @Valid
    <%_ } _%>
    <%_ } _%>
    private List<<%= relationship.otherEntityName %>Dto> <%= relationship.relationshipName %>;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "one-to-one") { _%>  
    <%_ if (relationship.validation) { _%>
    <%_ if (relationship.validation.notNull) { _%>
    @NotNull(message = "<%= relationship.relationshipName %> must not be empty or null")
    <%_ } _%>
    <%_ if (relationship.validation.isValid) { _%>
    @Valid
    <%_ } _%>
    <%_ } _%>
    private <%= relationship.otherEntityName %>Dto <%= relationship.relationshipName %> ;
    <%_ } _%>

    <%_ } _%>
}