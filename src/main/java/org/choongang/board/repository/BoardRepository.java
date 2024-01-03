package org.choongang.board.repository;

import org.choongang.board.entitys.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardData, Long> {



}
