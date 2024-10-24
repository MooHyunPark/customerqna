package kr.co.greenart.web.customer.qna;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


public interface QNA_Service {
	
	Integer pageCount();
	
	List<QNA> findAll(int page, String sort, String category, String query);
	boolean create(QNA qna);
	
	
	QNA findById(Integer articleId);
	QNA passwordCheckSuccess(Integer articleId);

	boolean passwordCheck(int articleId, String password);

	void delete(int articleId);
	
	QNA findByIdSecureCheckFalse(int articleId);

	void updateQNAInfo(int articleId, String title, String username, String password, String content, boolean secure);

}
