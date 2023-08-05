package core.aws.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public enum S3FolderType {
    //S3_IMAGE_ROOT(""),
    S3_BUCKET("/Users/csh/workspace/choisinsa/module-common/src/main/resources/static/image", null),

    ITEM(S3_BUCKET.bucket,"/item"),
    ITEM_IMAGE(S3_BUCKET.bucket, "/item-image");

    private final String bucket;
    private final String path;

    public static String getS3FolderWithId(S3FolderType folderType,
                                           Long id,
                                           String suffixUri) {

        if (folderType == null || id == null || !StringUtils.hasText(suffixUri)) {
            log.error("ERROR: 필수 데이터가 입력되지 않아서 빈 문자열을 반환합니다.");
            return "";
        }

        return folderType.bucket + folderType.path + "/" + id + "/" + suffixUri;
    }

    public static String getS3FolderWithId(S3FolderType folderType,
                                           Long id) {

        if (folderType == null || id == null) {
            log.error("ERROR: 필수 데이터가 입력되지 않아서 빈 문자열을 반환합니다.");
            return "";
        }

        return folderType.bucket + folderType.path + "/" + id;
    }

    public static String getDynamicSubFolderOfParentFolder(S3FolderType parentFolderType,
                                                           Long parentId,
                                                           S3FolderType subFolderType,
                                                           Long subId) {
        if (parentFolderType == null || parentId == null || subFolderType == null || subId == null) {
            log.error("ERROR: 필수 데이터가 입력되지 않아서 빈 문자열을 반환합니다.");
            return "";
        }

        if (!parentFolderType.bucket.equals(subFolderType.bucket)) {
            log.error("ERROR: 버킷 정보가 일치하지 않습니다.");
            return "";
        }

        return parentFolderType.bucket + parentFolderType.path + "/" + parentId
                + "/" + subFolderType.path + "/" + subId;
    }
}
