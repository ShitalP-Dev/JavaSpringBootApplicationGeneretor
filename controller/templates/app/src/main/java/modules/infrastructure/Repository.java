package <%= packageName %>.modules.<%= moduleName %>.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;
import java.util.List;

@Repository
public interface <%= entityName %>Repository extends JpaRepository<<%= entityName %>OrmEntity, UUID> {

<%_ if(isHardDeletion === false){ _%>
 List<<%= entityName %>OrmEntity> findByIsDeleted(boolean isdeleted);
<%_ }_%>
<%_ let capitalizeFirst = function(word){
     let value = word.charAt(0).toUpperCase() + word.slice(1);
     return value;
} _%>

<%_  const toPascalCase = str => (str.match(/[a-zA-Z0-9]+/g) || []).map(w => `${w.charAt(0).toUpperCase()}${w.slice(1)}`).join(''); _%>

/*
<%_  if(uniqueConstraints.length > 0 && typeof uniqueConstraints !== 'undefined'){ _%>
<%_ for(let uniqueConstraint of uniqueConstraints){ _%>
<%_  if(uniqueConstraint.criteria.length>1){ _%>
   <%_ let methodName = "get"+entityName+"By"; _%>
   <%_  let query= "\"select * from "+entityVarName+" where "; _%>
   <%_  let parameters =""; _%>
<%_  for(let criteria of uniqueConstraint.criteria){ _%>
      <%_    methodName = methodName+toPascalCase(criteria.fieldName)+"And";
         query= query+criteria.fieldName+"=:"+criteria.fieldName+" and ";
         parameters = parameters+"@Param(\""+criteria.fieldName+"\")String "+criteria.fieldName+","; _%>
   <%_ }_%>  
  <%_   String.prototype.replaceLast = function (search, replace) {
        return this.replace(new RegExp(search+"([^"+search+"]*)$"), replace+"$1");
    } _%>  
   <%_   methodName = methodName.replaceLast("And","");
    query= query.replaceLast(" and ","\"");
    parameters = parameters.replaceLast(",","");
    _%>
 @Query(nativeQuery = true, value = <%= query %>)
 <%= entityName %>OrmEntity <%= methodName %>(<%= parameters %>);

<%_ }else{ _%>
<%_  for(let criteria of uniqueConstraint.criteria){ _%>
public <%= entityName %>OrmEntity findBy<%= toPascalCase(criteria.fieldName) %>(<%= criteria.fieldType %> <%= criteria.fieldName %>);
 
 <%_ }_%> 
<%_ }_%>  
<%_ }_%>  
<%_ }_%> 
*/


<%_  if(uniqueConstraints.length > 0 && typeof uniqueConstraints !== 'undefined'){ _%>
<%_ for(let uniqueConstraint of uniqueConstraints){ _%>
<%_  if(uniqueConstraint.criteria.length>1){ _%>
   <%_ let methodName = "existsBy";
       let parameters ="";  _%>
<%_  for(let criteria of uniqueConstraint.criteria){ _%>
      <%_ methodName = methodName+toPascalCase(criteria.fieldName)+"And"; 
        parameters = parameters+criteria.fieldType+" "+criteria.fieldName+",";  _%>
   <%_ }_%>  
  <%_   String.prototype.replaceLast = function (search, replace) {
        return this.replace(new RegExp(search+"([^"+search+"]*)$"), replace+"$1");
    } _%>  
   <%_   methodName = methodName.replaceLast("And",""); 
         parameters = parameters.replaceLast(",","")_%>
   boolean <%= methodName %>(<%= parameters %>);
<%_ }else{ _%>
<%_  for(let criteria of uniqueConstraint.criteria){ _%>
public <%= entityName %>OrmEntity findBy<%= toPascalCase(criteria.fieldName) %>(<%= criteria.fieldType %> <%= criteria.fieldName %>);
 
 <%_ }_%> 
<%_ }_%>  
<%_ }_%>  
<%_ }_%> 

     
/*
<%_ for(let uniqueConstraint of uniqueConstraints){ _%>
    <%_  for(let criteria of uniqueConstraint.criteria){ _%>
public <%= entityName %>OrmEntity findBy<%= criteria.fieldName %>(<%= criteria.fieldType %> <%= criteria.fieldName %>);

    <%_ }_%>
<%_ }_%>
*/
}
