package kr.co.greenart.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.greenart.web.customer.qna.QNA;
import kr.co.greenart.web.customer.qna.QNA_Mapper;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoApplicationTests {
	
	@Autowired
	private QNA_Mapper mapper;
	
	@Test
	@Order(1)
	void testInsert() {
		QNA qna = QNA.builder().title("제목").content("내용").username("유저네임").password("비밀번호").build();
		int rows = mapper.save(qna);
		
		assertEquals(1, rows);
		assertNotNull(qna.getArticleId());
	}
	
	@Test
	@Order(2)
	void testSelect() {
		List<QNA> all = mapper.findAll();
		assertNotEquals(0, all.size());
	}

}






