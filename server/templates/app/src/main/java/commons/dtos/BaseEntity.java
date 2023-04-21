package <%= packageName %>.commons.dtos;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

	@Id @GeneratedValue
	@Column(name = "id" , columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")
	protected UUID id;

	@Column(name = "is_deleted")
	protected boolean isDeleted;
	
}