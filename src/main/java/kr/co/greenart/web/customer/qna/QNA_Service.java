package kr.co.greenart.web.customer.qna;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


public interface QNA_Service {
	
	
	List<QNA> findAll(int page, String sort, String category, String query, Integer size);
	boolean create(QNA qna);
	
	
	QNA findById(Integer articleId);
	QNA passwordCheckSuccess(Integer articleId);

	boolean passwordCheck(int articleId, String password);

	// 게시글 삭제
	void delete(int articleId);
	
	QNA findByIdSecureCheckFalse(int articleId);

	void updateQNAInfo(int articleId, String title, String username, String password, String content, boolean secure);

	int pageCount(int page, String sortSave, String category, String query, Integer size);
	
	//댓글 추가
	boolean addComment(int articleId, String username, String password, String content);
	
	//댓글 가져오기
	List<Comment> getCommentsByArticleId(int articleId);
	
	boolean editComment(Integer commentId, String content);
	boolean editCommentWithPassword(Integer commentId, String content, String password);
	boolean deleteComment(Integer commentId, Integer articleId);
	boolean deleteCommentWithPassword(Integer commentId, String password, Integer articleId);
	void insertFileData(FileData fileData);
	List<FileData> getFileDataList(int id);
	List<Question> getQuestion();
	boolean saveFaq(FAQ faq);
	boolean deleteFaqById(int id);

}
