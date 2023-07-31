package <%= packageName %>.commons.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequestDto {

	protected UUID id;

	@Value(value = "false")
	@JsonIgnore protected boolean isDeleted;
}