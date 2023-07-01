package core.repository.authority;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorityRepositoryCustomImpl implements AuthorityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}
