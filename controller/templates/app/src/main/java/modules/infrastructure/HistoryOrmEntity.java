package <%= packageName %>.modules.<%= moduleName %>.infrastructure;

import javax.persistence.Column;
import <%= packageName %>.commons.dtos.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
<%_ if (supportDatabaseSequences) { _%>
import javax.persistence.SequenceGenerator;
<%_ } _%>
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import org.hibernate.annotations.Type;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.Setter;
<%_ let count=0;
 for(const field of fields) { 
  if (field.validation.isClob===true) 
  { count++; }
  } 
  if(count>0){ _%>
  import javax.persistence.Lob;
<%_ } _%>


@Entity()
@Table(name = "<%= entityVarName %>_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class <%= entityName %>HistoryOrmEntity extends BaseEntity {

    <%_ for(const field of fields) { _%>
    <%_ if (field.validation) { _%>
    <%_ if (field.validation.isClob===true) { _%>
    @Lob
    <%_ } _%>
    <%_ } _%>
    @Column(name = "<%= field.fieldName %>")
    private <%= field.fieldType %> <%= field.fieldName %>;

    <%_ } _%>
    @Column(name = "sequence")
    private int sequence;

    @Column(name = "operation")
    private String operation;

    @Column(name = "parent_id" , columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")
    private UUID parentId;
    
    <%_ for(const relationship of relationships) { _%>
    <%_ if (relationship.relationshipType === "one-to-many") { _%>
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "<%= entityVarName %>_<%= relationship.relationshipName %>_mapping",
		joinColumns = @JoinColumn(name = "<%= entityVarName %>_id",referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "<%= relationship.relationshipName %>_id",referencedColumnName = "id"))
      private List<<%= relationship.otherEntityName %>OrmEntity> <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-one") { _%>
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinTable(name = "<%= entityVarName %>_<%= relationship.relationshipName %>_mapping",
		joinColumns = @JoinColumn(name = "<%= entityVarName %>_id",referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "<%= relationship.relationshipName %>_id",referencedColumnName = "id"))
      private <%= relationship.otherEntityName %>OrmEntity <%= relationship.relationshipName %> ;
    <%_ } _%>
    <%_ if (relationship.relationshipType === "many-to-many") { _%>
    @ManyToMany(cascade=CascadeType.ALL)
		@JoinTable(name = "<%= entityVarName %>_<%= relationship.relationshipName %>_mapping", 
		joinColumns = @JoinColumn(name = "<%= entityVarName %>_id",referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "<%= relationship.relationshipName %>_id",referencedColumnName = "id"))
      private List<<%= relationship.otherEntityName %>OrmEntity> <%= relationship.relationshipName %> ; 
    <%_ } _%>
    <%_ if (relationship.relationshipType === "one-to-one") { _%>
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "<%= relationship.relationshipName %>_id",referencedColumnName = "id")
    private <%= relationship.otherEntityName %>OrmEntity <%= relationship.relationshipName %> ;
    <%_ } _%>
    
    <%_ } _%>
}


