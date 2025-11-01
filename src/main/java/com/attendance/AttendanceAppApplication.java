package com.attendance;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class AttendanceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceAppApplication.class, args);
	}

	@GetMapping("/")
	public String home(@RequestParam(required = false) Double total,
					   @RequestParam(required = false) Double attended) {

		StringBuilder html = new StringBuilder();
		html.append("<html><head><title>Attendance Calculator</title>");
		html.append("<style>");
		html.append("body { font-family: Arial; background: #f8f9fa; display: flex; justify-content: center; align-items: center; height: 100vh; }");
		html.append(".container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center; }");
		html.append("input { margin: 5px; padding: 8px; width: 150px; }");
		html.append("button { padding: 8px 16px; cursor: pointer; background-color: #007bff; color: white; border: none; border-radius: 5px; }");
		html.append("</style></head><body>");
		html.append("<div class='container'>");
		html.append("<h2>Attendance Percentage Calculator</h2>");
		html.append("<form method='GET' action='/'>");
		html.append("<input type='number' name='total' placeholder='Total Classes' required><br>");
		html.append("<input type='number' name='attended' placeholder='Attended Classes' required><br>");
		html.append("<button type='submit'>Calculate</button>");
		html.append("</form><br>");

		if (total != null && attended != null) {
			if (total <= 0 || attended < 0 || attended > total) {
				html.append("<p style='color:red;'>Invalid input values!</p>");
			} else {
				double percentage = (attended / total) * 100;
				html.append(String.format("<p><b>Attendance:</b> %.2f%%</p>", percentage));

				if (percentage < 85) {
					int extraClasses = (int) Math.ceil((0.85 * total - attended) / (1 - 0.85));
					html.append(String.format("<p style='color:orange;'>You need to attend <b>%d</b> more classes to reach 85%%.</p>", extraClasses));
				} else {
					html.append("<p style='color:green;'>Great! Your attendance is above 85% 🎉</p>");
				}
			}
		}

		html.append("</div></body></html>");
		return html.toString();
	}
}
