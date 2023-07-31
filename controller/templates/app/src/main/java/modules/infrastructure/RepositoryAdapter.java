package <%= packageName %>.modules.<%= moduleName %>.infrastructure;

import java.util.List;
import <%= packageName %>.modules.<%= moduleName %>.commands.<%= entityName %>Command;
import <%= packageName %>.modules.<%= moduleName %>.queries.<%= entityName %>Query;
import <%= packageName %>.commons.converters.<%= entityName %>Mapper;
import <%= packageName %>.modules.<%= moduleName %>.infrastructure.exceptions.*;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import java.util.ArrayList;
<%_ if(addHistoryTable===true){ _%>
import <%= packageName %>.modules.<%= moduleName %>.queries.<%= entityName %>HistoryResponseDto;
<%_ } _%>

@Repository
public class <%= entityName %>RepositoryAdapter implements I<%= entityName %>PersistancyPort{

    @Autowired
    public  <%= entityName %>Repository <%= entityVarName %>Repository;

    <%_ if(addHistoryTable===true){ _%>
    @Autowired
    public  <%= entityName %>HistoryRepository <%= entityVarName %>HistoryRepository;
    <%_ } _%>

    @Override
    public List<<%= entityName %>Query> getAll<%= entityName %>s(){
      <%_ if(isHardDeletion === false){ _%>
      return <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityListTo<%= entityName %>QueryList(<%= entityVarName %>Repository.findByIsDeleted(false));
      <%_ }else{ _%>
	    return <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityListTo<%= entityName %>QueryList(<%= entityVarName %>Repository.findAll());
      <%_ }_%>
    }
    
    @Override
    public <%= entityName %>Query get<%= entityName %>By<%= entityName %>Id(UUID id){
      if(!this.<%= entityVarName %>Repository.existsById(id)) {
        throw new ResourceNotFoundException("<%= entityName %> not found");
		}
    <%_ if(isHardDeletion === false){ _%>
    if(this.<%= entityVarName %>Repository.getById(id).isDeleted() == true) {
        throw new ResourceNotFoundException("<%= entityName %> not found");
		}
    <%_ }_%>
		  return <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityTo<%= entityName %>Query(<%= entityVarName %>Repository.getById(id));
    }

    @Override
	  public void delete<%= entityName %>By<%= entityName %>Id(UUID id) {
      if(id==null) {
       throw new FieldNotFoundException("id is missing");
		}
      if(!this.<%= entityVarName %>Repository.existsById(id)) {
       throw new ResourceNotFoundException("<%= entityName %> not found");
		}
    <%_ if(isHardDeletion === false){ _%>
    if(this.<%= entityVarName %>Repository.getById(id).isDeleted() == true) {
        throw new ResourceNotFoundException("<%= entityName %> not found");
		}
    <%_ }_%>
      <%_ if(isHardDeletion === true){ _%>
      <%= entityName %>OrmEntity entity = this.<%= entityVarName %>Repository.getById(id);
		  this.<%= entityVarName %>Repository.deleteById(id);
      <%_ }else{_%>
      <%= entityName %>OrmEntity entity = this.<%= entityVarName %>Repository.getById(id);
      entity.setDeleted(true);
      this.<%= entityVarName %>Repository.save(entity);
      <%_ }_%>
      <%_ if(addHistoryTable===true){ _%>
      <%= entityName %>HistoryOrmEntity <%= entityVarName %>HistoryOrmEntity = <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityTo<%= entityName %>HistoryOrmEntity(entity);
	    <%= entityVarName %>HistoryOrmEntity.setOperation("deleted");
	    <%= entityVarName %>HistoryOrmEntity.setSequence(<%= entityVarName %>HistoryRepository.findByParentIdOrderBySequenceDesc(entity.getId()).size()+1);
	    <%= entityVarName %>HistoryRepository.save(<%= entityVarName %>HistoryOrmEntity);
      <%_ } _%>
    }

    @Override
    public <%= entityName %>Command save<%= entityName %>(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity) {
      if(checkIfExist(<%= entityVarName %>OrmEntity) == true) {
          throw new DuplicateEntryException("<%= entityName %> already exists");
      }
      <%= entityName %>OrmEntity savedentity = <%= entityVarName %>Repository.save(<%= entityVarName %>OrmEntity);
      <%_ if(addHistoryTable===true){ _%>
      <%= entityName %>HistoryOrmEntity <%= entityVarName %>HistoryOrmEntity = <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityTo<%= entityName %>HistoryOrmEntity(savedentity);
	    <%= entityVarName %>HistoryOrmEntity.setOperation("created");
	    <%= entityVarName %>HistoryOrmEntity.setSequence(<%= entityVarName %>HistoryRepository.findByParentIdOrderBySequenceDesc(savedentity.getId()).size()+1);
	    <%= entityVarName %>HistoryRepository.save(<%= entityVarName %>HistoryOrmEntity);
      <%_ } _%>
      return <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityTo<%= entityName %>Command(savedentity);
    }

    @Override
    public <%= entityName %>Command edit<%= entityName %>(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity) {
      if(!this.<%= entityVarName %>Repository.existsById(<%= entityVarName %>OrmEntity.getId())) {
       throw new ResourceNotFoundException("<%= entityName %> not found");
		}  
      <%_ if(isHardDeletion === false){ _%>
    if(this.<%= entityVarName %>Repository.getById(<%= entityVarName %>OrmEntity.getId()).isDeleted() == true) {
        throw new ResourceNotFoundException("<%= entityName %> not found");
		}
    <%_ }_%>
      <%= entityName %>OrmEntity savedentity = <%= entityVarName %>Repository.save(<%= entityVarName %>OrmEntity);
      <%_ if(addHistoryTable===true){ _%>
      <%= entityName %>HistoryOrmEntity <%= entityVarName %>HistoryOrmEntity = <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityTo<%= entityName %>HistoryOrmEntity(savedentity);
	    <%= entityVarName %>HistoryOrmEntity.setOperation("edited");
	    <%= entityVarName %>HistoryOrmEntity.setSequence(<%= entityVarName %>HistoryRepository.findByParentIdOrderBySequenceDesc(savedentity.getId()).size()+1);
	    <%= entityVarName %>HistoryRepository.save(<%= entityVarName %>HistoryOrmEntity);
      <%_ } _%>
      return <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>OrmEntityTo<%= entityName %>Command(savedentity);
    }

    <%_ if(addHistoryTable===true){ _%>
     @Override
    public List<<%= entityName %>HistoryResponseDto> get<%= entityName %>History(UUID parentId){
      if(this.<%= entityVarName %>HistoryRepository.findByParentIdOrderBySequenceDesc(parentId).isEmpty()) {
        throw new ResourceNotFoundException("<%= entityName %> not found");
		}
		  return <%= entityName %>Mapper.INSTANCE.<%= entityVarName %>HistoryOrmEntityListTo<%= entityName %>HistoryResponseDtoList(<%= entityVarName %>HistoryRepository.findByParentIdOrderBySequenceDesc(parentId));
    }
    <%_ } _%>

    <%_  const toPascalCase = str => (str.match(/[a-zA-Z0-9]+/g) || []).map(w => `${w.charAt(0).toUpperCase()}${w.slice(1)}`).join(''); _%>
   
    public boolean checkIfExist(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity){
      List<Boolean> results = new ArrayList<>();
      <%_  if(uniqueConstraints.length > 0 && typeof uniqueConstraints !== 'undefined'){
            let methodName ="";
            let parameters="";
             _%>
      <%_ for(let uniqueConstraint of uniqueConstraints){ _%>
      <%_  if(uniqueConstraint.criteria.length>1){ _%>
      <%_ methodName = "existsBy";
          parameters =entityVarName+"OrmEntity.get"; _%>
      <%_ for(let criteria of uniqueConstraint.criteria){ _%>
      <%_  methodName = methodName+toPascalCase(criteria.fieldName)+"And";
           parameters = parameters+toPascalCase(criteria.fieldName)+"(), "+entityVarName+"OrmEntity.get"; _%>
          <%_ }_%>  
      <%_   String.prototype.replaceLast = function (search, replace) {
        return this.replace(new RegExp(search+"([^"+search+"]*)$"), replace+"$1");
       } _%>  
      <%_ methodName = methodName.replaceLast("And","");
          parameters = parameters.replaceLast("(), "+entityVarName+"OrmEntity.get","") _%>
      if(this.<%= entityVarName %>Repository.<%= methodName %>(<%= parameters %>) == false){
          results.add(false);
        }else {
      	results.add(true);
        }
      <%_ }else{ _%>
      <%_  for(let criteria of uniqueConstraint.criteria){ _%>
      <%_ methodName = "findBy"+toPascalCase(criteria.fieldName) 
          parameters =entityVarName+"OrmEntity.get"+toPascalCase(criteria.fieldName)+"()";  _%>
      if(this.<%= entityVarName %>Repository.<%= methodName %>(<%= parameters %>) == null){
        results.add(false);
        }else {
      	results.add(true);
        }
      <%_ }_%> 
      <%_ }}}_%> 
      return results.contains(false)? false: true;
    }   
/*
  <%_  if(typeof uniqueConstraints !== 'undefined'){ _%>
  public boolean checkIfExist(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity){
  <%_  let count=1; _%> 
    List<Boolean> results = new ArrayList<>();
    boolean result=true;  
  <%_ for(let uniqueConstraint of uniqueConstraints){ _%>
   <%_  if(uniqueConstraint.criteria.length>1){ _%>
    List<<%= entityName %>OrmEntity> fetched<%= entityName %>OrmEntity = new ArrayList<<%= entityName %>OrmEntity>();
      <%_  for(let criteria of uniqueConstraint.criteria){ _%>
    <%= entityName %>OrmEntity <%= entityVarName %>OrmEntity<%= count %>=this.<%= entityVarName %>Repository.findBy<%= criteria.fieldName %>(<%= entityVarName %>OrmEntity.get<%= criteria.fieldName %>());
    fetched<%= entityName %>OrmEntity.add(<%= entityVarName %>OrmEntity<%= count++ %>);
      <%_ }_%>
    for(<%= entityName %>OrmEntity obj : fetched<%= entityName %>OrmEntity){
			if(obj == null)
         result= false;
			}
    for(int i=0 ; i<fetched<%= entityName %>OrmEntity.size()-1 ; i++){
			if(fetched<%= entityName %>OrmEntity.get(i).getId() != fetched<%= entityName %>OrmEntity.get(i+1).getId())
				 result= false;
			}
      results.add(result);
   <%_ }else{ _%> 
   <%_  if(uniqueConstraint.criteria.length===1){ _%>
   <%= entityName %>OrmEntity <%= entityVarName %>OrmEntity<%= count %>=this.<%= entityVarName %>Repository.findBy<%= uniqueConstraint.criteria[0].fieldName %>(<%= entityVarName %>OrmEntity.get<%= uniqueConstraint.criteria[0].fieldName %>());
   if(<%= entityVarName %>OrmEntity<%= count++ %> == null){
      result= false;
    }
     results.add(result);
   <%_ } _%>
   <%_ } _%>
 <%_ }_%>
  for(int i=0 ; i<results.size() ; i++){
			if(results.get(i)==false){
        return false;
      }
  }
  return true;
	}
<%_ }_%>
*/
}