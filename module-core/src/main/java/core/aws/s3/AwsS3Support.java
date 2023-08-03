package core.aws.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class AwsS3Support {

    public static String uploadTest(MultipartFile itemMainImageFile) {
        return "[TEST] upload s3 url";
    }
}
