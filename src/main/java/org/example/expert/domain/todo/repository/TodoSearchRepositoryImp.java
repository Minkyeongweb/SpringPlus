package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoSearchRepositoryImp implements TodoSearchRepository{
    private final JPAQueryFactory q;

    @Override
    public Optional<Todo> findByIdWithUserFromQueryDsl(long todoId) {
        Todo getTodo = q
                .select(todo)
                .from(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(
                        todoIdEq(todoId)
                ).fetchOne();
        return Optional.ofNullable(getTodo);
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) : null;
    }
}
