package com.ldh.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.AttrRepository;

@Service
public class AttrService {

	@Autowired
	private AttrRepository attrRepository;

	public void setValue(String string, int memberId, String string2, String string3, String authKeyForMemberModify,
			String dateStrLater) {
		// 추가예정
	}

}
