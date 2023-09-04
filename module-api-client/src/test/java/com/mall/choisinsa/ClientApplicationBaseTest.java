package com.mall.choisinsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import com.mall.choisinsa.security.service.Oauth2UserService;
import com.mall.choisinsa.security.service.SecurityUserDetailsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ActiveProfiles({"client", "database", "aws", "oauth", "jwt"})
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser
public class ClientApplicationBaseTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    public AuthenticationProvider authenticationProvider;

    @MockBean
    public SecurityUserDetailsService securityUserDetailsService;

    @MockBean
    public JwtTokenProvider jwtTokenProvider;

    @MockBean
    public Oauth2UserService oauth2UserService;

    public SecurityMemberDto generateMemberSecurityMemberDto() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(AuthorityType.MEMBER.getText());
        return new SecurityMemberDto(
                1L,
                "testMember",
                "test",
                List.of(simpleGrantedAuthority)
        );
    }
}
