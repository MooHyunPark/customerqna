package kr.co.greenart.web.customer.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class QNA_ServiceImpl implements QNA_Service {
	
	@Autowired
	private QNA_Mapper mapper;
	
	@Override
	public List<QNA> findAll(int page) {
		
		int offset = 0;
		offset += (page - 1) * 5;
		List<QNA> all = mapper.findAll(5, offset);
		return all;
	}

	@Override
	public boolean create(QNA qna) {
		Integer result = mapper.save(qna);
		
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public QNA qnaDetail(int articleId) {
		QNA byPk = mapper.findByPk(articleId);
		return byPk;
	}

	@Override
	public void updateCount(int article_id) {
		mapper.updateCount(article_id);
	}

	@Override
	public Integer count() {
		return mapper.count();
	}
}
