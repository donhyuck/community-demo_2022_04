package com.ldh.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ldh.exam.demo.vo.Board;

@Mapper
public interface BoardRepository {

	Board getBoardById(int id);

}
