package kr.co.greenart.web.customer.qna;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SortQNA {

	public List<QNA> sort(int page, String sort, String category, String query, List<QNA> all) {
		List<QNA> result = new ArrayList<>();
		int offset = 0;
		offset += (page - 1) * 20;

		if (sort.equals("views")) {
			Collections.sort(all, new Comparator<QNA>() {
				@Override
				public int compare(QNA qna1, QNA qna2) {
					return Integer.compare(qna2.getViews(), qna1.getViews());
				}
			});
		} else if (sort.equals("comments")) {
			Collections.sort(all, new Comparator<QNA>() {
				@Override
				public int compare(QNA qna1, QNA qna2) {
					return Integer.compare(qna2.getComments(), qna1.getComments());
				}
			});
		}
		
		System.out.println(query);
		if (!query.equals("none")) {
			// Title에 query 문자열이 포함된 QNA 객체만 남기기
			if (category.equals("title")) {
				all.removeIf(qna -> !qna.getTitle().contains(query));
			} else if (category.equals("content")) {
				all.removeIf(qna -> !qna.getContent().contains(query));
			} else if (category.endsWith("username")) {
				all.removeIf(qna -> !qna.getUsername().contains(query));
			}
		}
		

		for (int i = offset; i < (offset + 20); i++) {
			if (i >= all.size()) {
				break;
			}
			result.add(all.get(i));
		}
		return result;
	}

}
