package core.repository.authority;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.domain.authority.AuthorityMenu;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static core.domain.authority.QAuthority.authority1;
import static core.domain.authority.QAuthorityMenu.authorityMenu;
import static core.domain.authority.QMenuDetailAuthority.menuDetailAuthority;
import static core.domain.menu.QMenu.menu;
import static core.domain.menu.QMenuIncludeDetailApiUrl.menuIncludeDetailApiUrl;

@RequiredArgsConstructor
public class AuthorityRepositoryCustomImpl implements AuthorityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}
