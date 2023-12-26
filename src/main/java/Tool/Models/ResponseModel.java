package Tool.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
	private Boolean status;
	private String message;
	private Object body;
	private String time;
}
