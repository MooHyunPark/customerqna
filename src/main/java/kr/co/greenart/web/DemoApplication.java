package kr.co.greenart.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kr.co.greenart.web.customer.qna.QNA;
import kr.co.greenart.web.customer.qna.QNA_Mapper;

@SpringBootApplication // Component scan이 포함되어 있다.
public class DemoApplication implements CommandLineRunner{
	
	@Autowired
	private QNA_Mapper mapper;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 2; i < 25; i++) {
			mapper.save(QNA.builder()
					.title("title" + i)
					.content("content" + i)
					.username("username" + i)
					.password("password" + i)
					.secure(false)
					.deleted(false)
					.build());
		}
	}
	
	

}
