package com.diary.inn.InnDiary.work.repository.info;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier("SearchToDoRepositoryImpl")
@Repository
public class SearchToDoRepositoryImpl implements SearchToDoRepository {
}
