package kr.co.greenart.web.customer.qna;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
	private int comment_id;
	private int article_id;
	private String username;
	private String password;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private String formatCreatedAt;
	private String formatUpdatedAt;


	public void formatDateTime(LocalDateTime createdAt, LocalDateTime updatedAt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (createdAt != null) {
			formatCreatedAt = createdAt.format(formatter);
		}
		
		if (updatedAt != null) {
			formatUpdatedAt = updatedAt.format(formatter);
		}
		
	}
}
