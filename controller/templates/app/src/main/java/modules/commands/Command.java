package <%= packageName %>.modules.<%= moduleName %>.commands;

import <%= packageName %>.commons.dtos.BaseDto;
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
 
    <%_ for(const field of fields) { _%>
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
      private List<<%= relationship.otherEntityName %>Dto> <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-one") { _%>
      private <%= relationship.otherEntityName %>Dto <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-many") { _%>
      private List<<%= relationship.otherEntityName %>Dto> <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "one-to-one") { _%>  
      private <%= relationship.otherEntityName %>Dto <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ }else{ _%>
    <%_ if (relationship.relationshipType === "one-to-many") { _%>
      private List<<%= relationship.otherEntityName %>Command> <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-one") { _%>
      private <%= relationship.otherEntityName %>Command <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-many") { _%>
      private List<<%= relationship.otherEntityName %>Command> <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "one-to-one") { _%>  
      private <%= relationship.otherEntityName %>Command <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ } _%>
    
    <%_ } _%>  
}