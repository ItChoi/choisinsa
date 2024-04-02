package com.mall.choisinsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.web.resolver.LoginUserArgResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

@ActiveProfiles({"client"})
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
//@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ExtendWith(RestDocumentationExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser
@WebMvcTest
public abstract class ClientApplicationBaseTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    public LoginUserArgResolver loginUserArgResolver;

    @MockBean
    public AuthenticationProvider authenticationProvider;

    @MockBean
    public UserDetailsService userDetailsService;

    /*@MockBean
    public Oauth2UserService oauth2UserService;*/

    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        final String SCHEME = "http";
        final String HOST = "localhost";
        final int PORT = 8080;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(
                        MockMvcRestDocumentation.documentationConfiguration(provider)
                                .operationPreprocessors()
                                .withRequestDefaults(Preprocessors.modifyUris().scheme(SCHEME).host(HOST).port(PORT), Preprocessors.prettyPrint())
                                .withResponseDefaults(Preprocessors.modifyUris().scheme(SCHEME).host(HOST).port(PORT), Preprocessors.prettyPrint())
                )
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    public UserDetails generateMemberSecurityMemberDto() {
        return new User(
                "testMember",
                "1234",
                true,
                false,
                false,
                false,
                List.of(new SimpleGrantedAuthority(AuthorityType.MEMBER.getText()))
        );
    }
}
