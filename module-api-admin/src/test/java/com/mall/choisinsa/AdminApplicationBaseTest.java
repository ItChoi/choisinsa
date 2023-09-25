package com.mall.choisinsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.web.LoginUserArgResolver;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ActiveProfiles({"admin"})
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8081)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser
public class AdminApplicationBaseTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    public LoginUserArgResolver loginUserArgResolver;

    @MockBean
    public AuthenticationProvider authenticationProvider;

    @MockBean
    public UserDetailsService userDetailsService;

    public UserDetails generateAdminSecurityMemberDto() {
        return new User(
                "testAdmin",
                "1234",
                true,
                false,
                false,
                false,
                List.of(new SimpleGrantedAuthority(AuthorityType.ADMIN.getText()))
        );
    }
}
