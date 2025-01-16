package be.dash.dashserver.external.config.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "s3")
public record S3Properties(
        String accessKey,
        String secretKey,
        String region,
        String s3BucketName,
        String s3Endpoint
) {

}
