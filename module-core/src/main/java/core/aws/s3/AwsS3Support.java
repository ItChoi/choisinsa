package core.aws.s3;

import core.common.exception.ErrorTypeAdviceException;
import core.common.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * TODO: AWS 연동 후 로직 s3로 세팅하기
 * AWS 연동 전 로컬 이미지로 대체하기.
 */
@Slf4j
@RequiredArgsConstructor
public class AwsS3Support {

    // public static final String ITEM_FILE_PATH = "src/main/resources/static/data/csv/items.csv";
    //@Value("${upload.directory}")


    public static String uploadTest(S3FolderType folderType,
                                    Long id,
                                    String suffixUri,
                                    MultipartFile file) {
        return upload(file, S3FolderType.getS3FolderWithId(folderType, id, suffixUri));
    }

    public static String uploadTest(S3FolderType parentFolderType,
                                    Long parentId,
                                    S3FolderType subFolderType,
                                    Long subId,
                                    MultipartFile file) {
        return upload(
                file,
                S3FolderType.getDynamicSubFolderOfParentFolder(
                        parentFolderType,
                        parentId,
                        subFolderType,
                        subId
                )
        );
    }

    public static String uploadTest(S3FolderType folderType,
                                    Long id,
                                    MultipartFile file) {
        return upload(file, S3FolderType.getS3FolderWithId(folderType, id));
    }

    private static String upload(MultipartFile file,
                                 String uploadPath) {
        try {
            Path directoryPath = Paths.get(uploadPath);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // 파일 저장
            String filename = file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, filename);
            Files.write(filePath, file.getBytes());
            return uploadPath + "/" + filename;
        } catch (Exception e) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }

    public static void delete(String url) {
        File file = new File(url);
        if (file.delete()) {
            log.info("파일이 삭제 됐습니다. [{}]", url);
        } else {
            log.info("파일이 경로에 해당 파일이 존재하지 않습니다. [{}]", url);
        }
    }
}
