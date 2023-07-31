package <%= packageName %>.modules.<%= moduleName %>.infrastructure;

import java.util.List;
import <%= packageName %>.modules.<%= moduleName %>.commands.*;
import <%= packageName %>.modules.<%= moduleName %>.queries.*;
import java.util.UUID;

public interface I<%= entityName %>PersistancyPort {

public List<<%= entityName %>Query> getAll<%= entityName %>s();

public <%= entityName %>Command save<%= entityName %>(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity);

public <%= entityName %>Query get<%= entityName %>By<%= entityName %>Id(UUID id);

public void delete<%= entityName %>By<%= entityName %>Id(UUID id);

public <%= entityName %>Command edit<%= entityName %>(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity);

<%_ if(addHistoryTable===true){ _%>
public List<<%= entityName %>HistoryResponseDto> get<%= entityName %>History(UUID parentId);
<%_ } _%>
//public <%= entityName %>Entity save(<%= entityName %>OrmEntity <%= entityVarName %>OrmEntity);

}