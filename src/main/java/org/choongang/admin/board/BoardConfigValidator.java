package org.choongang.admin.board;

import lombok.RequiredArgsConstructor;
import org.choongang.board.repository.BoardRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardConfigValidator {

    private final BoardRepository boardRepository;
}
