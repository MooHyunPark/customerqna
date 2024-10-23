package kr.co.greenart.web.qna;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface QnAMapper {
	@Insert({"insert into customerqna(title, content, username, password, is_secure)"
		, "values (#{title}, #{content}, #{username}, #{password}, #{is_secure})"})
	void createBoard(String title, String content, String username, String password, int is_secure);
	
	@Select("select article_id, title, username, is_secure from customerqna where is_secure = false")
	List<QNA> findBoardList();
	
	@Select("select title, content, username from customerqna where article_id = #{article_id} and is_secure = false and is_deleted = false")
	QNA findBoard(int article_id);
	
	@Select("select is_secure from customerqna where article_id = #{article_id}")
	Integer fasdksfdak(int article_id);
	
	@Update("update customerqna set views = (views + 1) where article_id = #{article_id}")
	void updateViewCount(int article_id);
	
	@Update("update customerqna set is_deleted = true where article_id = #{article_id} and password = #{password}")
	void deleteBoard(int article_id, String password);
//	-- 1. 익명 고객센터 문의게시판 테이블을 생성하는 쿼리문을 작성해주세요
//	-- 2. 글 작성
//	-- 3. 게시글 목록(id, title, username, is_secure)
//	-- 3-1. 게시글 조회 시, is_secure 값이 false인 행만 조회
//	-- 4. 게시글 조회(id로 검색, title, content, username)
//	-- 5. 게시글(id)의 비밀 여부 조회 (is_secure)?
//	-- 6. views count 수정(1 증가)
//	-- 7. 글 논리 삭제(pk 및 password 일치) : is_deleted => 1로 수정
}
