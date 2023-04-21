package <%= packageName %>.commons.converters;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import <%= packageName %>.modules.<%= moduleName %>.commands.*;
import <%= packageName %>.modules.<%= moduleName %>.queries.*;
import <%= packageName %>.modules.<%= moduleName %>.infrastructure.*;
<%_ if(typeof withDomainLayer !== 'undefined' && withDomainLayer ===true){ _%>
<%_ for(const field of aggregateRoot.fields) { _%>
  <%_ if(typeof field.isValueObject !== 'undefined' && field.isValueObject === true){ _%>
import <%= packageName %>.commons.valueobjects.*;
<%_ }} _%> 
import <%= packageName %>.commons.dtos.*;
import <%= packageName %>.modules.<%= moduleName %>.domain.*;
<%_ } _%>

<%_ function camelize(str) {
  return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index) {
    return index === 0 ? word.toLowerCase() : word.toUpperCase();
  }).replace(/\s+/g, '');
} _%>

 <%_ String.prototype.replaceLast = function (search, replace) {
        return this.replace(new RegExp(search+"([^"+search+"]*)$"), replace+"$1");
       } _%> 

<%_  const toPascalCase = str => (str.match(/[a-zA-Z0-9]+/g) || []).map(w => `${w.charAt(0).toUpperCase()}${w.slice(1)}`).join(''); _%>
 

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface <%= entityName %>Mapper {

    <%= entityName %>Mapper INSTANCE = Mappers.getMapper(<%= entityName %>Mapper.class);

    <%_ if(typeof withDomainLayer !== 'undefined' && withDomainLayer ===false){ _%>

    <%= entityName %>Command <%= entityVarName %>CommandRequestDtoTo<%= entityName %>Command(<%= entityName %>CommandRequestDto <%= entityVarName %>CommandRequestDto);
	
    <%= entityName %>CommandResponseDto <%= entityVarName %>CommandTo<%= entityName %>CommandResponseDto(<%= entityName %>Command <%= entityVarName %>Command);
	
    <%= entityName %>OrmEntity <%= entityVarName %>CommandTo<%= entityName %>OrmEntity(<%= entityName %>Command <%= entityVarName %>Command);

    <%= entityName %>Command <%= entityVarName %>OrmEntityTo<%= entityName %>Command(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity);
    
    <%= entityName %>Query <%= entityVarName %>OrmEntityTo<%= entityName %>Query(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity);
    
    <%= entityName %>QueryResponseDto <%= entityVarName %>QueryTo<%= entityName %>QueryResponseDto(<%= entityName %>Query <%= entityVarName %>Query);
    
    List<<%= entityName %>Query> <%= entityVarName %>OrmEntityListTo<%= entityName %>QueryList(List<<%= entityName %>OrmEntity> <%= entityVarName %>OrmEntity);
    
    List<<%= entityName %>QueryResponseDto> <%= entityVarName %>QueryListTo<%= entityName %>QueryResponseDtoList(List<<%= entityName %>Query> <%= entityVarName %>Query);

    <%_ if(typeof addHistoryTable !== 'undefined' && addHistoryTable===true){ _%>
    @Mappings({
		@Mapping(source = "id", target = "parentId"),
		@Mapping(target = "sequence", ignore = true),
		@Mapping(target = "operation", ignore = true)
		})
    <%= entityName %>HistoryOrmEntity <%= entityVarName %>OrmEntityTo<%= entityName %>HistoryOrmEntity(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity);

     List<<%= entityName %>HistoryResponseDto> <%= entityVarName %>HistoryOrmEntityListTo<%= entityName %>HistoryResponseDtoList(List<<%= entityName %>HistoryOrmEntity> <%= entityVarName %>HistoryOrmEntity);

    <%_ } _%>
    <%_ } _%>
    <%_ if(typeof withDomainLayer !== 'undefined' && withDomainLayer ===true){ _%>
    <%_ for(const method of infrastructureLayerAdapterMethods) { _%>
    <%_ if(typeof method.template !== 'undefined' && method.template.name !== 'undefined' && method.template.name == 'get'){ _%>
    <%= entityName %>Query <%= camelize(method.template.mappingEntity.name) %>To<%= entityName %>Query(<%= method.template.mappingEntity.name %> <%= camelize(method.template.mappingEntity.name) %>);
    <%_ }} _%>

    <%= entityName %>Query <%= entityVarName %>QueryRequestDtoTo<%= entityName %>Query(<%= entityName %>QueryRequestDto <%= entityVarName %>QueryRequestDto);

    <%= entityName %>Command <%= entityVarName %>CommandRequestDtoTo<%= entityName %>Command(<%= entityName %>CommandRequestDto <%= entityVarName %>CommandRequestDto);
	
    <%= entityName %>CommandResponseDto <%= entityVarName %>CommandTo<%= entityName %>CommandResponseDto(<%= entityName %>Command <%= entityVarName %>Command);
	
    <%= entityName %>QueryResponseDto <%= entityVarName %>QueryTo<%= entityName %>QueryResponseDto(<%= entityName %>Query <%= entityVarName %>Query);
    
    <%= entityName %>AggregateRoot <%= entityVarName %>CommandTo<%= entityName %>AggregateRoot(<%= entityName %>Command <%= entityVarName %>command);

    List<<%= entityName %>QueryResponseDto> <%= entityVarName %>QueryListTo<%= entityName %>QueryResponseDtoList(List<<%= entityName %>Query> <%= entityVarName %>Query);

    <%_ for(const field of aggregateRoot.fields) { _%> 
    <%_ if(typeof field.isValueObject !== 'undefined' && field.isValueObject === true){ _%>
    <%_ if(field.targetDto){ _%>
      default <%- field.valueObjectName %> <%- field.targetDto %>To<%- field.valueObjectName %>(<%- field.targetDto %> <%- field.fieldNameInDto %>){
        if(<%- field.fieldNameInDto %> ==null)
          return null;
    <%_ var param ="";
    for(const dto of importModuleDtos){
        if(dto.name == field.targetDto){
          for(const field_ of dto.fields){ 
            param=param+field.fieldNameInDto+'.get'+toPascalCase(field_.fieldName)+'(),';
           }
           param=param.replaceLast("(),","");
        }
    } _%>
          return new <%- field.valueObjectName %>(<%- param %>);	
    }
    
    <%_ }else{ _%>
    default <%- field.valueObjectName %> <%- field.fieldNameInDto %><%- field.fieldTypeInDto %>To<%- field.valueObjectName %> (<%- field.fieldTypeInDto %> <%- field.fieldNameInDto %>) {
      if(<%- field.fieldNameInDto %> ==null)
        return null;
      return new <%- field.valueObjectName %>(<%- field.fieldNameInDto %>);
    }

    <%_ } _%>
    <%_ } _%>
    <%_ } _%>
    <%_ } _%>
}