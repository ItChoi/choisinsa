package core.dto.client.request.kakao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoAuthorizeCodeRequestDto {
    private String client_id;
    private String redirect_uri;
    private String response_type;
    private boolean profile_nickname;
    private boolean profile_image;
    private boolean gender;
    private boolean age_range;
    private boolean birthday;
    private boolean user_oauth_approval;

    //stsc: bJ_cLLD14TupIVlf9q99ozWEiztWf_0vEdD7zFkhQyW835FDJWWijngsOoXqoUw_SpjEF1D3qRzYiunz-Wk1Tggw99oHz_mYriZ0_OiRJF74Eve2fOAsnaQh9Eo3YW0ogMui45ibVWKAc5VldGKzhrIYVTwxNVMeUBz7axFPj3Lzl52WAAvBbuiH07FnwQElaMf6OxFGVF63D3uFFzFAbI3ZzLCSDuANYifu6MoLmO8ceO1DJo1X9agr3shc305gIfWMRi-QSmFBT4XPvzjUTPdWE6gHOo-_U8wuah8sqnWfLJfoL4UDrtKQwQkM6rrKC4Dshk9EtqMBgYD0dp2AHMMCQg4WZKENhsGyZZTFqhW8fKzAaYovz23kiPZ99fdO7xfgba2tlnT4OKQJpZEM7g
    //csts: -wVp73b09bPCD5OF2nUcs_Y3_GzuJ0Knuw4LlteIErtVRbSd7_JFBfpnNOesQenaeGMahWhr9l5xFY6a591q8yYSf-cso3uQMTgvct0986fQn6kigORWBeHh5N4vxXgFcDYKXzzvfr_mRU9VbISXhPtxUWd8qrfgh02S7H06qXHqrsf-xsMGR7CmCvO8yByjZP4CBEpli7TpFhfKJkcN1wGeYvVmBy_BC0aOsWS2RickEFASMwkYGUodKGT0Q_PpvVq990efzMsnHCoGld8khnrsZw_uMkaBqcHdnJmoWIH5jZVO8m67BeBmp5eXGoM23RNKA5t-YEro5CSH7LWstsaQQVjLvNf-SmwjZ4pBsgY4y_ErNeXTuyL9EuM0sgu5blrZudabtZz7L7tdAzIKvTMDD1QdlMJNGZFlniFTSivVyttCGH8AMo56-V4IEutWZ7TGswYxaYcCRXFeg83MoJcmX7Jq9stGyL0UgymmGYbejcoWGM6SJs_XlQaioE8sw2dnzgdVe-_Gw0FTbiQ_8zJKal_gLO1AVt4c3Fr8fPBgBryqJs4UR3B40I8oVym2qtwUkFuKpjn8kAm70uOWsseWVEqIxKdVitmbWgXRfN8
    //profile_nickname: true
    //profile_image: true
    //gender: true
    //age_range: true
    //birthday: true
    //user_oauth_approval: true
}
