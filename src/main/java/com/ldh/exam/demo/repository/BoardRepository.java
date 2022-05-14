package com.ldh.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldh.exam.demo.vo.Board;

@Mapper
public interface BoardRepository {

	@Select("""
			SELECT *
			FROM board AS b
			WHERE b.id = #{id}
			AND b.delStatus = 0
			""")
	Board getBoardById(@Param("id") int id);

}
