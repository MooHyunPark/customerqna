package kr.co.greenart.web.customer.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import kr.co.greenart.web.util.QNA_IsSecure;
import kr.co.greenart.web.util.QNA_NotFoundException;

// QNA_Controller만 advice 하기 위해 괄호를 아래처럼 작성.
@ControllerAdvice(assignableTypes = QNA_Controller.class)
public class QNA_ControllerAdvice {
	
	@Autowired
	private QNA_Mapper mapper;
	
	@ExceptionHandler(QNA_NotFoundException.class)
	public ModelAndView notFound(QNA_NotFoundException e) {
		
		ModelAndView mv = new ModelAndView("notFound");
		mv.setStatus(HttpStatusCode.valueOf(404));
		return mv;
	}
	
	@ExceptionHandler(QNA_IsSecure.class)
	public ModelAndView isSecure(QNA_IsSecure e) {
		ModelAndView mv = new ModelAndView("passwordcheck");
		int id = e.getArticleId();
		QNA qna = mapper.findById(id);
		mv.addObject("qna", qna);
		return mv;
	}
}
