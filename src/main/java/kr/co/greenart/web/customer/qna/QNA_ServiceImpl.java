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
	
	@Override
	public List<QNA> findAll(int page, String sort, String category, String query, Integer size) {
		int offset = 0;
		offset += (page - 1) * size;
		List<QNA> result = mapper.findAll(size, offset, sort, category, query);
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
		int rows = mapper.updateCount(articleId);

		if (rows == 1) {
			qna.setViews(qna.getViews() + 1);
		}
		return qna;
	}

	@Override
	public void updateQNAInfo(int articleId, String title, String username, String password, String content, boolean secure) {
		mapper.updateQNAInfo(articleId, title, username, password, content, secure);
	}

	@Override
	public int pageCount(int page, String sortSave, String category, String query, Integer size) {
		int count = mapper.count(category, query);
		int totalPages = 1;
		while (count > size) {
			totalPages++;
			count -= size;
		}
		return totalPages;
	}

	@Override
	public boolean addComment(int articleId, String username, String password, String content) {
		int result = mapper.addComment(articleId, username, password, content);
		if (result == 1) {
			mapper.updateCommentCount(articleId, 1);
			if (username.equals("관리자") && password.equals("관리자")) {
				mapper.updateAdminComment(articleId, true);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<Comment> getCommentsByArticleId(int articleId) {
		List<Comment> list = mapper.GetCommentsByArticleId(articleId);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).formatDateTime(list.get(i).getCreatedAt(), list.get(i).getUpdatedAt());
		}
		return list;
	}

	@Override
	public boolean editComment(Integer commentId, String content) {
		
		Integer result = mapper.editComment(commentId, content);
		
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editCommentWithPassword(Integer commentId, String content, String password) {
		Integer result = mapper.editCommentWithPassword(commentId, password, content);
		
		if (result != null && result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteComment(Integer commentId, Integer articleId) {
		Integer result = mapper.updateCommentToDelete(commentId);
		if (result == 1) {
			mapper.updateCommentCount(articleId, -1);
			adminCommentCheck(articleId);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteCommentWithPassword(Integer commentId, String password, Integer articleId) {
		Integer result = mapper.updateCommentToDeleteWithPassword(commentId, password);
		if (result == 1) {
			mapper.updateCommentCount(articleId, -1);
			adminCommentCheck(articleId);
			return true;
		}
		return false;
	}

	public void adminCommentCheck(Integer articleId) {
		List<Comment> list = mapper.GetCommentsByArticleId(articleId);
		int count = 0;
		for (Comment comment : list) {
			if (comment.getPassword().equals("관리자")) {
				count++;
			}
		}
		if (count == 0) {
			mapper.updateAdminComment(articleId, false);
		}
	}


}
