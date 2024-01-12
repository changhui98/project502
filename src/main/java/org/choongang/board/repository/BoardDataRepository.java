package org.choongang.board.repository;

import org.choongang.board.entitys.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDataRepository extends JpaRepository<BoardData, Long> {

}
