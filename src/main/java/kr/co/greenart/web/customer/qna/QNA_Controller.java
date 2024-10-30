package kr.co.greenart.web.customer.qna;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class QNA_Controller {

	@Autowired
	private QNA_Service service;

	private static final String UPLOAD_DIR = "D:/uploads/";
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
		List<FileData> fileDataList = service.getFileDataList(id);
		if (fileDataList != null) {
			model.addAttribute("fileDataList", fileDataList);
		}
		model.addAttribute("comments", comments);
		model.addAttribute("qna", qna);
		return "article";
	}
	
	@GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String fileName) throws IOException {
        Path filePath = Paths.get("D:/uploads/" + fileName);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                .body(resource);
    }

	@GetMapping("/create")
	public String createBoard(Model model) {
		model.addAttribute("qnaList", new ArrayList<QNA>());
		return "createqna";
	}

	@PostMapping("/create")
	public ResponseEntity<?> insert(@RequestParam String title, @RequestParam String content,
			@RequestParam String username, @RequestParam String password, @RequestParam Boolean secure,
			@RequestParam MultipartFile[] file) {

		QNA qna = QNA.builder().title(title).content(content).username(username).password(password).secure(secure)
				.build();

		boolean result = service.create(qna);

		Map<String, Object> resultMessage = new HashMap<>();
		if (result) {
			if (file != null) {
				
				for (int i = 0; i < file.length; i++) {
					FileData fileData = FileData.builder().article_id(qna.getArticleId()).fileName(file[i].getOriginalFilename()).build();
					service.insertFileData(fileData);
					
					try {
						File destFile = new File(UPLOAD_DIR + file[i].getOriginalFilename());
						file[i].transferTo(destFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
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
	
	// 댓글 수정
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
	
	// 댓글 삭제
	@PostMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(@RequestParam(defaultValue = "-1") Integer articleId,
			@RequestParam(defaultValue = "") Integer commentId, @RequestParam(defaultValue = "") String password) {

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
	
	@GetMapping("/question")
	public String getQuestion(Model model) {
		
		List<Question> list = service.getQuestion();
		model.addAttribute("questionList", list);
		return "question";
	}
	
	@GetMapping("/questionCreate")
	public String createQuestion(Model model) {
		return "createQuestion";
	}
	
	// 자주 묻는 질문 추가
	@PostMapping("/submitFaq")
    public ResponseEntity<?> submitFaq(@RequestParam String questionTitle,
                                       @RequestParam String questionDetail,
                                       RedirectAttributes redirectAttributes) {
        // 서비스 계층 호출하여 FAQ 저장 로직
        FAQ faq = FAQ.builder()
                .question_title(questionTitle)
                .question_detail(questionDetail)
                .build();
        
        // FAQ 저장 성공 여부
        boolean result = service.saveFaq(faq);
        
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "FAQ가 성공적으로 제출되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "FAQ 제출에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	// 자주 묻는 질문 삭제
	@PostMapping("/deleteQuestion")
    public ResponseEntity<Map<String, Boolean>> deleteQuestion(@RequestParam(defaultValue = "-1") int id) {
        Map<String, Boolean> response = new HashMap<>();
        if (id == -1) {
            response.put("message", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            boolean result = service.deleteFaqById(id);
            response.put("success", result);
            if (result) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
