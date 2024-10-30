package kr.co.greenart.web.customer.qna;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Question {
	private int question_id;
	private String question_title;
	private String question_detail;
}
