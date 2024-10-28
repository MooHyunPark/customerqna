package kr.co.greenart.web.customer.qna;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface QNA_Mapper {
	// 글 작성
	// @Options 는 h2db의 last_insert_id
	@Insert({"insert into customerqna(title, content, username, password, is_secure, fileName, fileData)"
		, "values (#{title}, #{content}, #{username}, #{password}, #{secure}, #{fileName}, #{fileData})"})
	@Options(useGeneratedKeys = true, keyProperty = "articleId")
	int save(QNA qna);
	
	
	
	
	@Select({"select article_id, title, content, username, views, is_secure, password from customerqna where is_deleted = false order by views desc, article_id desc"
		, "limit #{pageSize} offset #{offset}"
	})
	@ResultMap("qnaList")
	List<QNA> viewFindAll(int pageSize, int offset);
	
//	@Select({"select article_id, title, content, username, views, is_secure, password from customerqna where is_deleted = false order by comments desc, article_id desc"
//		, "limit #{pageSize} offset #{offset}"
//	})
//	@ResultMap("qnaList")
//	List<QNA> commentsFindAll(int pageSize, int offset);
	
	
	
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
	
	// 패스워드 체크
	@Select("select count(*) from customerqna where article_id = #{articleId} and password = #{password}")
	int passwordCheck(int articleId, String password);
	
	// 게시글 논리 삭제
	@Update("update customerqna set is_deleted = true where article_id = #{article_id}")
	void updateDelete(int article_id);

	// 게시글 수정
	@Update("update customerqna set title = #{title}, username = #{username}, password = #{password}, content = #{content}, is_secure = #{secure} where article_id = #{articleId}")
	void updateQNAInfo(int articleId, String title, String username, String password, String content, boolean secure);
	
	
	
	
	// 게시글 목록(id, title, username, is_secure)
	@SelectProvider(type = SQLProvider.class, method = "findAll")
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
					, @Result(column = "adminComment", property = "adminComment")
			})
	List<QNA> findAll(Integer limit, Integer offset, String sort, String category, String query);
	

	// 모든 게시글의 수를 반환
	@SelectProvider(type = SQLProvider.class, method = "count")
	Integer count(String category, String query);
	
	class SQLProvider {
		public static String findAll(@Param("limit") Integer limit, @Param("offset") Integer offset, @Param("sort") String sort, @Param("category") String category, @Param("query") String query) {
		    return new SQL() {{
		        SELECT("article_id, title, content, username, views, is_secure, password, comments, adminComment");
		        FROM("customerqna");
		        WHERE("is_deleted = false");
		        if (category != null && query != null && !category.equals("none") && !query.equals("none")) {
		            if ("title".equals(category)) {
		                WHERE("title LIKE '%' || #{query} || '%'");
		            } else if ("content".equals(category)) {
		                WHERE("content LIKE '%' || #{query} || '%'");
		            } else if ("username".equals(category)) {
		                WHERE("username LIKE '%' || #{query} || '%'");
		            }
		        }
		        
		        if ("adminComment".equals(sort)) {
		        	System.out.println("asdfasdfasdfasdf");
	            	WHERE("adminComment = false");
	            }

		        if ("views".equals(sort)) {
		            ORDER_BY("views desc, article_id desc");
		        } else if ("comments".equals(sort)) {
		            ORDER_BY("comments desc, article_id desc");
		        } else if ("old_order".equals(sort)){
		            ORDER_BY("article_id asc");
		        } else {
		        	ORDER_BY("article_id desc");
		        }
		        LIMIT("#{limit}");
		        OFFSET("#{offset}");
		    }}.toString();
		}
		
		public static String count(@Param("category") String category, @Param("query") String query) {
		    return new SQL() {{
		        SELECT("count(*)");
		        FROM("customerqna");
		        WHERE("is_deleted = false"); // 기본 조건 추가

		        if (category != null && query != null && !category.equals("none") && !query.equals("none")) {
		            if ("title".equals(category)) {
		                WHERE("title LIKE '%' || #{query} || '%'");
		            } else if ("content".equals(category)) {
		                WHERE("content LIKE '%' || #{query} || '%'");
		            } else if ("username".equals(category)) {
		                WHERE("username LIKE '%' || #{query} || '%'");
		            }
		        }
		    }}.toString();
		}

	}

	// 댓글 추가
	@Insert("insert into comment(article_id, username, password, content) values (#{article_id}, #{username}, #{password}, #{content})")
	Integer addComment(int article_id, String username, String password, String content);
	
	@Update("update customerqna set comments = comments + #{number} where article_id = #{article_id}")
	Integer updateCommentCount(int article_id, int number);
	
	// 관리자가 댓글 달았을 시 adminComment를 변경
	@Update("update customerqna set adminComment = #{adminComment} where article_id = #{article_id}")
	Integer updateAdminComment(int article_id, boolean adminComment);
	
	// 댓글 가져오기
	@Select("select * from comment where article_id = #{article_id} and is_deleted = false")
	List<Comment> GetCommentsByArticleId(int article_id);

	@Update("update comment set content = #{content} where comment_id = #{commentId}")
	Integer editComment(Integer commentId, String content);


	@Update("update comment set is_deleted = true where comment_id = #{commentId}")
	Integer updateCommentToDelete(Integer commentId);

	@Update("update comment set is_deleted = true where comment_id = #{commentId} and password = #{password}")
	Integer updateCommentToDeleteWithPassword(Integer commentId, String password);

	@Update("update comment set content = #{content} where comment_id = #{commentId} and password = #{password}")
	Integer editCommentWithPassword(Integer commentId, String password, String content);
	
	
}









