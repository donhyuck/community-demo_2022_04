package com.ldh.exam.demo.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int boardId;
	private String title;
	private String body;
	private int hitCount;
	private String goodReactionPoint;
	private String badReactionPoint;

	private Map<String, Object> extra;

	private String extra__writerName;
	private String extra__writerRealName;
	private boolean extra__actorCanModify;
	private boolean extra__actorCanDelete;

	public String getForPrintType1RegDate() {
		return regDate.substring(2, 16).replace(" ", "<br>");
	}

	public String getForPrintType1UpdateDate() {
		return updateDate.substring(2, 16).replace(" ", "<br>");
	}

	public String getForPrintType2RegDate() {
		return regDate.substring(2, 16);
	}

	public String getForPrintType2UpateDate() {
		return updateDate.substring(2, 16);
	}

	public String getForPrintBody() {
		return body.replaceAll("\n", "</br>");
	}
}
