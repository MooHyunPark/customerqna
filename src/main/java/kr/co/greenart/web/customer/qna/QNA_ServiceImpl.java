package kr.co.greenart.web.customer.qna;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.greenart.web.util.QNA_IsSecure;
import kr.co.greenart.web.util.QNA_NotFoundException;

@Service
@Primary
public class QNA_ServiceImpl implements QNA_Service {

	@Autowired
	private QNA_Mapper mapper;
	
	@Autowired
	private SortQNA sortQNA;
	
	
	@Override
	public List<QNA> findAll(int page, String sort, String category, String query) {

		List<QNA> all = mapper.findAll();
		
		List<QNA> result = sortQNA.sort(page, sort, category, query, all);
		
//		for (int i = offset; i < offset+20; i++) {
//			if (sort.equals("created_at")) {
//				
//			} else if (sort.equals("views")) {
//				
//			} else if (sort.equals("comments")) {
//				
//			}
//		}
		
		//created_at views comments
		return result;
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
	public Integer pageCount() {
		
		int count = mapper.count();
		int totalPages = 1;
		while (count > 20) {
			totalPages++;
			count -= 20;
		}
		return totalPages;
	}
	

	@Override
	public QNA passwordCheckSuccess(Integer articleId) {
		QNA qna = mapper.findById(articleId);
		if (qna == null) {
			throw new QNA_NotFoundException(articleId);
		}
		int rows = mapper.updateCount(articleId);

		if (rows == 1) {
			qna.setViews(qna.getViews() + 1);
		}
		return qna;
	}

////////////////////////////////////////////////////////////////////
	@Override
	@Transactional
	public QNA findById(Integer articleId) {
		QNA qna = mapper.findById(articleId);
		if (qna == null) {
			throw new QNA_NotFoundException(articleId);
		}

		if (qna.getSecure()) {
			throw new QNA_IsSecure(articleId);
		}
		int rows = mapper.updateCount(articleId);

		if (rows == 1) {
			qna.setViews(qna.getViews() + 1);
		}
		return qna;
	}

	@Override
	public boolean passwordCheck(int articleId, String password) {
		int rows = mapper.passwordCheck(articleId, password);
		
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(int articleId) {
		mapper.updateDelete(articleId);
	}

	@Override
	public QNA findByIdSecureCheckFalse(int articleId) {
		QNA qna = mapper.findById(articleId);
		if (qna == null) {
			throw new QNA_NotFoundException(articleId);
		}

		return qna;
	}

	@Override
	public void updateQNAInfo(int articleId, String title, String username, String password, String content, boolean secure) {
		mapper.updateQNAInfo(articleId, title, username, password, content, secure);
	}


}
