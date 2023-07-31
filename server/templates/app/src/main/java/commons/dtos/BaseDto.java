package <%= packageName %>.commons.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto {

	protected UUID id;

	@Value(value = "false")
	protected boolean isDeleted;
	
}