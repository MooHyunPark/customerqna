package kr.co.greenart.web.customer.qna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.greenart.web.util.QNA_IsSecure;
import kr.co.greenart.web.util.QNA_NotFoundException;

@Controller
public class QNA_Controller {

	@Autowired
	private QNA_Service service;

	private String sortSave = null;

	@GetMapping("/qna")
	public String qna(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "none") String sort,
			Model model) {

		if (!sort.equals("none")) {
			sortSave = sort;
		}

		int totalPages = service.pageCount();
		List<QNA> qnaList = service.findAll(page, sortSave);

		model.addAttribute("qnaList", qnaList);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("sort", sortSave);

		return "qna";

	}

	@GetMapping("/qna/{id}")
	public String readArticle(@PathVariable int id, Model model) {
		QNA qna = service.findById(id);
		model.addAttribute("qna", qna);
		return "article";
	}

	@GetMapping("/create")
	public String createBoard(Model model) {
		model.addAttribute("qnaList", new ArrayList<QNA>());
		return "createqna";
	}

	@PostMapping("/create")
	public ResponseEntity<?> insert(@RequestBody QNA qna) {
		boolean result = service.create(qna);

		Map<String, Object> resultMessage = new HashMap<>();
		if (result) {
			resultMessage.put("message", "success");
			resultMessage.put("created", qna);
			return ResponseEntity.ok(resultMessage);
		} else {
			resultMessage.put("message", "fail");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMessage);
		}
	}

	@PostMapping("/success")
	public String passwordSuccess(@RequestParam(defaultValue = "0") int articleId, Model model) {
		if (articleId != 0) {
			QNA qna = service.passwordCheckSuccess(articleId);
			model.addAttribute("qna", qna);
			return "article";
		}
		return "qna";
	}

	@PostMapping("/checkPassword")
	public ResponseEntity<?> checkPassword(@RequestBody PasswordRequest request) {
		boolean success = service.passwordCheck(request.getArticleId(), request.getPassword());

		if (success) {
			return ResponseEntity.ok(Collections.singletonMap("success", true));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("success", false));
		}
	}

	@GetMapping("/delete")
	public void deleteArticle(@RequestParam int articleId, HttpServletResponse response) throws IOException {
	    service.delete(articleId);
	    response.sendRedirect("/qna");
	}
	
	@GetMapping("/change")
	public String changeArticle(@RequestParam int articleId, Model model) {
		QNA qna = service.findByIdSecureCheckFalse(articleId);
		model.addAttribute("qna", qna);
		return "changeArticle"; // JSP 파일명
	}
	
	@PostMapping("/change")
	public String changeArticlePost(@RequestParam int articleId, @RequestParam String title, 
			@RequestParam String username, @RequestParam String password, @RequestParam String content, @RequestParam boolean secure, Model model) {
		service.updateQNAInfo(articleId, title, password, username, content, secure);
		
		QNA qna = service.findByIdSecureCheckFalse(articleId);
		model.addAttribute("qna", qna);
		return "article";
	}


}
