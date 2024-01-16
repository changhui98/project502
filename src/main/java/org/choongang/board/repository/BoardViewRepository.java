package org.choongang.board.repository;

import org.choongang.board.entitys.BoardView;
import org.choongang.board.entitys.BoardViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardViewRepository extends JpaRepository<BoardView, BoardViewId> , QuerydslPredicateExecutor<BoardView> {

}
