package org.choongang.file.repositorys;

import org.choongang.file.entitys.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> ,
        QuerydslPredicateExecutor<FileInfo> {





}
