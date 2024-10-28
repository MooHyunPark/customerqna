package kr.co.greenart.web.customer.qna;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QNA {
	private Integer articleId;
	private String title;
	private String content;
	private String username;
	private String password;
	private Integer views;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean secure;
	private Boolean deleted;
	private Integer comments;
	private Boolean adminComment;
}

/*


3. 게시글 수정/삭제

수정 이력 저장


6. 공유

게시글 공유 링크 생성

7. 관리자 댓글 기능
7.3 답변이 완료되지 않은 게시글 목록 조회

etc. 기타 사용자 편의 기능

첨부파일 업로드 가능 (이미지 파일만 허용, 파일당 최대 5MB)
자주 묻는 질문 / 답변
태그 추가, 검색
게시글 추천
메일 전송

*/
	