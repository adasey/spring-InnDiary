package com.diary.inn.InnDiary.work.repository.fro;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier("SearchTodoRepositoryImpl")
@Repository
public class SearchTodoRepositoryImpl implements SearchTodoRepository {
}
