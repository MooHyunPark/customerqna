package kr.co.greenart.web.customer.qna;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface QNA_Mapper {
	// 글 작성
	// @Options 는 h2db의 last_insert_id
	@Insert({"insert into customerqna(title, content, username, password)"
		, "values (#{title}, #{content}, #{username}, #{password})"})
	@Options(useGeneratedKeys = true, keyProperty = "articleId")
	int save(QNA qna);
	
	
	
	
	// 게시글 목록(id, title, username, is_secure)
	@Select({"select article_id, title, content, username, views, is_secure, password from customerqna order by article_id desc"
		, "limit #{pageSize} offset #{offset}"
	})
	@Results(id = "qnaList"
			, value = {
					@Result(column = "article_id", property = "articleId")
					, @Result(column = "title", property = "title")
					, @Result(column = "content", property = "content")
					, @Result(column = "username", property = "username")
					, @Result(column = "views", property = "views")
					, @Result(column = "is_secure", property = "secure")
					, @Result(column = "password", property = "password")
			})
	List<QNA> findAll(int pageSize, int offset);
	
	
	
	// 게시글 조회 시, is_secure 값이 false인 행만 조회
	@Select({"select article_id, title, content, username, views, is_secure from customerqna "
		, "where is_secure = 0"
		, "order by article_id desc"
		, "limit #{pageSize} offset #{offset}"
	})
	@ResultMap("qnaList")
	List<QNA> findBySecureIsFalse(int pageSize, int offset);
	
	// TODO sql 명령문 구현
	// 게시글 조회(id로 검색, title, content, username)
	@Select("select title, content, username, is_secure from customerqna where article_id = #{article_id}")
	@ResultMap("qnaList")
	QNA findByPk(int article_id);
	// 게시글(id)의 비밀 여부 조회(is_secure)
	void findSecureByPk();
	// views count 수정(id)(1 증가)
	
	@Update("update customerqna set views = (views + 1) where article_id = #{article_id}")
	void updateCount(int article_id);
	// 글 논리 삭제(pk 및 password 일치) : is_deleted => 1로 수정
	void updateDelete();
	
	@Select("select count(*) from customerqna")
	Integer count();
}
