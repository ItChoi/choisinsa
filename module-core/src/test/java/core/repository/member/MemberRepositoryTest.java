//package core.repository.member;
//
//import com.mall.choisinsa.enumeration.member.MemberStatus;
//import com.mall.choisinsa.enumeration.member.MemberType;
//import com.mall.choisinsa.security.service.SecurityMemberService;
//import core.BaseSpringTest;
//import core.domain.member.Member;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//public class MemberRepositoryTest extends BaseSpringTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    SecurityMemberService securityMemberService;
//
//    @DisplayName("h2 테스트")
//    @Test
//    void test() throws Exception {
//        Member member = memberRepository.save(
//                new Member(
//                        2L,
//                        "member",
//                        "$2a$10$Qlt3GW74dA2ePYbV7jvLTeHZsSNhd6puS16vwNS8shgc7zYPTUbmy",
//                        MemberStatus.NORMAL,
//                        null,
//                        "WQcPal8TzpYvJq7t5F7dT/a+cD1kJ/bZpDlenvLYTjE=",
//                        null,
//                        null,
//                        null,
//                        MemberType.MEMBER
//                )
//        );
//        System.out.println("member = " + member);
//    }
//
//}