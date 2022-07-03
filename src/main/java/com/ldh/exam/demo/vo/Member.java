package com.ldh.exam.demo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ldh.exam.demo.service.MemberService;
import com.ldh.exam.demo.util.Ut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {

	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private int authLevel;
	private String name;
	private String nickname;
	private String cellPhoneNo;
	private String email;
	private boolean delStatus;
	private String delDate;

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

	public boolean isAdmin() {
		return this.authLevel == 7;
	}

	public String getAuthLevelName() {

		return MemberService.getAuthLevelName(this);
	}

	public String toJsonStr() {

		return Ut.toJsonStr(this);
	}

	public String getForPrintDelDate() {
		return delDate.substring(2, 16);
	}
}