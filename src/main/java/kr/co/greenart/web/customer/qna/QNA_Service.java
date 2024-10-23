package kr.co.greenart.web.customer.qna;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


public interface QNA_Service {
	
	Integer count();
	
	List<QNA> findAll(int page);
	boolean create(QNA qna);
	QNA qnaDetail(int articleId);
	void updateCount(int article_id);
}
