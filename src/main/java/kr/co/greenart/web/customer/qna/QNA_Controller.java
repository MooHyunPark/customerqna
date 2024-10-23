package kr.co.greenart.web.customer.qna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class QNA_Controller {
	
	@Autowired
	private QNA_Service service;
	
	@GetMapping("/qna")
	public String boardList(@RequestParam(defaultValue = "1") int page, Model model) {
	    int totalPages = 1;
	    int count = service.count();
	    while (count > 5) {
	        totalPages++;
	        count -= 5;
	    }
	    List<QNA> qnaList = service.findAll(page);
	    
	    
	    model.addAttribute("qnaList", qnaList);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
	    
	    return "qna";
	}

	
	@GetMapping("/qna/{articleId}")
	public String findByPk(@PathVariable int articleId, Model model) {
		QNA qnaDetail = service.qnaDetail(articleId);
		service.updateCount(articleId);
		model.addAttribute("qna", qnaDetail);
//		if (qnaDetail.getSecure()) {
//			return "passwordcheck";
//		}
		return "detail";
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
	
	
	
	@GetMapping("/q")
	public String findBoard(Model model) {
		model.addAttribute("qnaList", new ArrayList<QNA>());
		return "qna";
	}
	
}
