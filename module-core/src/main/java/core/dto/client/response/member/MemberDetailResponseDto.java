package core.dto.client.response.member;

import com.mall.choisinsa.enumeration.member.GenderType;
import core.domain.member.MemberDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDetailResponseDto {
    private Long id;
    private String birthday;
    private GenderType gender;
    private String height;
    private String weight;
    private Boolean isAcceptMarketing;
    private Boolean isAuthenticateEmail;
    private Boolean isAuthenticatePhone;
    private String selfIntroduce;
    private Long recommenderMemberId;

    public MemberDetailResponseDto(MemberDetail memberDetail) {
        this.id = memberDetail.getId();
        this.birthday = memberDetail.getBirthday();
        this.gender = memberDetail.getGender();
        this.height = memberDetail.getHeight();
        this.weight = memberDetail.getWeight();
        this.isAcceptMarketing = memberDetail.getIsAcceptMarketing();
        this.isAuthenticateEmail = memberDetail.getIsAuthenticateEmail();
        this.isAuthenticatePhone = memberDetail.getIsAuthenticatePhone();
        this.selfIntroduce = memberDetail.getSelfIntroduce();
        this.recommenderMemberId = memberDetail.getRecommenderMemberId();
    }
}
