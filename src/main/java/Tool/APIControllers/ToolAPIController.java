package Tool.APIControllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Tool.Models.ResponseModel;

@RestController
@RequestMapping(path = "/api-tool")
public class ToolAPIController {

	@PostMapping(path = "/register")
	public ResponseEntity<?> register(
			@Validated @RequestBody String jsonData,
			@Validated @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) String authorizationHeader) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
		String formattedDateTime = now.format(formatter);

		try {
			String url = "http://localhost:8181/api-tool/test";
			String token = authorizationHeader.substring(7);

			URL apiUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			connection.setDoOutput(true);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			int responseCode = connection.getResponseCode();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

			StringBuilder responseStringBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				responseStringBuilder.append(line);
			}

			String responseBody = responseStringBuilder.toString();
			connection.disconnect();
			
			return new ResponseEntity<>(new ResponseModel(true, "Success", responseBody, formattedDateTime),
					HttpStatus.resolve(responseCode));
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseModel(false, "Error", "{}", formattedDateTime),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/test")
	public String test(@RequestBody String jsonData) {
		return "Respone Test Brooo!";
	}
}
