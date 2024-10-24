package kr.co.greenart.web.customer.qna;

import java.time.LocalDateTime;
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
	@Insert({"insert into customerqna(title, content, username, password, is_secure)"
		, "values (#{title}, #{content}, #{username}, #{password}, #{secure})"})
	@Options(useGeneratedKeys = true, keyProperty = "articleId")
	int save(QNA qna);
	
	
	
	
	// 게시글 목록(id, title, username, is_secure)
	@Select({"select article_id, title, content, username, views, is_secure, password, comments from customerqna "
			+ "where is_deleted = false order by created_at desc, article_id desc"})
	@Results(id = "qnaList"
			, value = {
					@Result(column = "article_id", property = "articleId")
					, @Result(column = "title", property = "title")
					, @Result(column = "content", property = "content")
					, @Result(column = "username", property = "username")
					, @Result(column = "views", property = "views")
					, @Result(column = "is_secure", property = "secure")
					, @Result(column = "password", property = "password")
					, @Result(column = "comments", property = "comments")
			})
	List<QNA> findAll();
	
	
	@Select({"select article_id, title, content, username, views, is_secure, password from customerqna where is_deleted = false order by views desc, article_id desc"
		, "limit #{pageSize} offset #{offset}"
	})
	@ResultMap("qnaList")
	List<QNA> viewFindAll(int pageSize, int offset);
	
	@Select({"select article_id, title, content, username, views, is_secure, password from customerqna where is_deleted = false order by comments desc, article_id desc"
		, "limit #{pageSize} offset #{offset}"
	})
	@ResultMap("qnaList")
	List<QNA> commentsFindAll(int pageSize, int offset);
	
	
	
	// 게시글 조회 시, is_secure 값이 false인 행만 조회
	@Select({"select article_id, title, content, username, views, is_secure from customerqna "
		, "where is_secure = 0"
		, "order by article_id desc"
		, "limit #{pageSize} offset #{offset}"
	})
	@ResultMap("qnaList")
	List<QNA> findBySecureIsFalse(int pageSize, int offset);
	
	// 게시글 조회
	@Select("select * from customerqna where article_id = #{article_id}")
	@Results(
			id = "qnaMapping"
			, value = {
					@Result(column = "article_id", property = "articleId")
					, @Result(column = "title", property = "title")
					, @Result(column = "content", property = "content")
					, @Result(column = "username", property = "username")
					, @Result(column = "views", property = "views")
					, @Result(column = "is_secure", property = "secure")
					, @Result(column = "password", property = "password")
					, @Result(column = "created_at", property = "createdAt")
					, @Result(column = "updated_at", property = "updatedAt")
					, @Result(column = "is_deleted", property = "deleted")
			}
	)
	QNA findById(int article_id);
	
	// views count 수정(id)(1 증가)
	@Update("update customerqna set views = (views + 1) where article_id = #{article_id}")
	int updateCount(int article_id);
	
	
	// 모든 게시글의 수를 반환
	@Select("select count(*) from customerqna")
	Integer count();


	// 패스워드 체크
	@Select("select count(*) from customerqna where article_id = #{articleId} and password = #{password}")
	int passwordCheck(int articleId, String password);
	
	// 게시글 논리 삭제
	@Update("update customerqna set is_deleted = true where article_id = #{article_id}")
	void updateDelete(int article_id);

	// 게시글 수정
	@Update("update customerqna set title = #{title}, username = #{username}, password = #{password}, content = #{content}, is_secure = #{secure} where article_id = #{articleId}")
	void updateQNAInfo(int articleId, String title, String username, String password, String content, boolean secure);
}
