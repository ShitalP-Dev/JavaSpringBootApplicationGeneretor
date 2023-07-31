package <%= packageName %>.modules.<%= moduleName %>.queries;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import <%= packageName %>.commons.dtos.BaseRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class <%= entityName %>QueryRequestDto extends BaseRequestDto {

<%_ for(const field of fields) { _%>
     <%_ if (field.validation) { _%>
    <%_ if (field.validation.notEmpty) { _%>
    @NotEmpty(message = "<%= field.fieldName %> must not be empty or null")
    <%_ } _%>
    <%_ if (field.validation.isEmail) { _%>
    @Email(regexp="<%= field.validation.regExp %>",message="'${validatedValue}' is not in Email ID format")
     <%_ } _%>
     <%_ if (field.validation.regExp) { _%>
    @Pattern(regexp="<%= field.validation.regExp %>")
     <%_ }_%>
     <%_ if (field.validation.length) { _%>
    @Length(max=<%= field.validation.length %>,message = "<%= field.fieldName %> field length should not exceed 50 characters")
     <%_ }_%>
    <%_ if (field.validation.JsonIgnore) { _%>
    @JsonIgnore
     <%_ }_%>
    <%_ } _%>
    private <%= field.fieldType %> <%= field.fieldName %>;

    <%_ } _%>
    <%_ for(const relationship of relationships) { _%>
    <%_  var isAttributeClass = true; _%>
    <%_ for(var entityMetaData of entitiesMetaData) {
    if(entityMetaData.className === relationship.otherEntityName){
    if(entityMetaData.attributeClass === false){ 
       isAttributeClass = false;
    }}}_%>
    <%_ if (isAttributeClass) { _%>
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
    <%_ }else{ _%>
    <%_ if (relationship.relationshipType === "one-to-many") { _%>
    <%_ if (relationship.validation) { _%>
    <%_ if (relationship.validation.notNull) { _%>
    @NotNull(message = "<%= relationship.relationshipName %> must not be empty or null")
    <%_ } _%>
    <%_ if (relationship.validation.isValid) { _%>
    @Valid
    <%_ } _%>
    <%_ } _%>
    private List<<%= relationship.otherEntityName %>QueryRequestDto> <%= relationship.relationshipName %> ;
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
    private <%= relationship.otherEntityName %>QueryRequestDto <%= relationship.relationshipName %> ;
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
    private List<<%= relationship.otherEntityName %>QueryRequestDto> <%= relationship.relationshipName %>;
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
    private <%= relationship.otherEntityName %>QueryRequestDto <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ } _%>
    
    <%_ } _%>
}