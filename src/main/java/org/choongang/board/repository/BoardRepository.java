package org.choongang.board.repository;

import org.choongang.board.entitys.Board;
import org.choongang.board.entitys.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<BoardData, String>, QuerydslPredicateExecutor<Board> {


}
