package kr.co.greenart.web.customer.qna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class QNA_Controller {

	@Autowired
	private QNA_Service service;

	private String sortSave = null;

	@GetMapping("/qna")
	public String qna(Pageable pages, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "none") String sort, @RequestParam(defaultValue = "none") String category,
			@RequestParam(defaultValue = "none") String query, @RequestParam(defaultValue = "20") Integer size,
			Model model) {
//		log.info("size=" + pages.getpa);
//		log.info("size=" + page.getPageSize());
//		log.info("size=" + page.getPageSize());
//		log.info("size=" + page.getPageSize());
		if (!sort.equals("none")) {
			sortSave = sort;
		} else {
			sortSave = "created_at";
		}

		List<QNA> qnaList = service.findAll(page, sortSave, category, query, size);
		int totalPages = service.pageCount(page, sortSave, category, query, size);
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("sort", sortSave);
		model.addAttribute("category", category);
		model.addAttribute("query", query);

		return "qna";

	}

	@GetMapping("/qna/{id}")
	public String readArticle(@PathVariable int id, Model model, HttpSession session) {
		String sessionValue = (String) session.getAttribute("admin");
		
		QNA qna;
		if (sessionValue != null && sessionValue.equals("admin")) {
			qna = service.findByIdSecureCheckFalse(id);
		} else {
			qna = service.findById(id);
		}
		
		List<Comment> comments = service.getCommentsByArticleId(id);

		model.addAttribute("comments", comments);
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
			List<Comment> comments = service.getCommentsByArticleId(articleId);
			model.addAttribute("comments", comments);
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
			@RequestParam String username, @RequestParam String password, @RequestParam String content,
			@RequestParam boolean secure, Model model) {
		service.updateQNAInfo(articleId, title, username, password, content, secure);

		QNA qna = service.findByIdSecureCheckFalse(articleId);
		model.addAttribute("qna", qna);
		return "article";
	}

	@PostMapping("/submitComment")
	public ResponseEntity<?> submitComment(@RequestBody CommentRequest request, Model model) {

		boolean success = service.addComment(request.getArticleId(), request.getUsername(), request.getPassword(),
				request.getContent());
		List<Comment> comments = service.getCommentsByArticleId(request.getArticleId());
		model.addAttribute("comments", comments);
		if (success) {
			return ResponseEntity.ok(Map.of("success", true, "comments", comments));
		} else {
			return ResponseEntity.ok(Map.of("success", false));
		}
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String loginPost(@RequestParam(defaultValue = "") String username,
			@RequestParam(defaultValue = "") String password, HttpSession session) {

		if (username.equals("admin") && password.equals("admin")) {
			session.setAttribute("admin", "admin");
			return "redirect:/qna";
		}

		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/qna";
	}

	@GetMapping("adminDelete")
	public String getMethodName(@RequestParam int articleId) {

		service.delete(articleId);

		return "redirect:/qna";
	}

	@PostMapping("/editComment")
	public ResponseEntity<?> postMethodName(@RequestParam(defaultValue = "") Integer commentId,
			@RequestParam(defaultValue = "") String content, @RequestParam(defaultValue = "") String password) {

		boolean success;
		if (password.equals("")) {
			success = service.editComment(commentId, content);
		} else {
			success = service.editCommentWithPassword(commentId, content, password);
		}
		
		if (success) {
			return ResponseEntity.ok(Map.of("success", true));
		} else {
			return ResponseEntity.ok(Map.of("success", false));
		}

	}

	@PostMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(@RequestParam(defaultValue = "-1") Integer articleId,
			@RequestParam(defaultValue = "") Integer commentId,
			@RequestParam(defaultValue = "") String password) {
		
		boolean deleteSuccess;
		if (password.equals("")) {
			deleteSuccess = service.deleteComment(commentId, articleId);
		} else {
			deleteSuccess = service.deleteCommentWithPassword(commentId, password, articleId);
		}

		if (deleteSuccess) {
			return ResponseEntity.ok(Map.of("success", true));
		} else {
			return ResponseEntity.ok(Map.of("success", false));
		}
	}

}
