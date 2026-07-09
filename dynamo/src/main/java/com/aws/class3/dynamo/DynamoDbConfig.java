package com.aws.class3.dynamo;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbConfig {

    private static final Region REGION = Region.US_EAST_2;

    public static DynamoDbClient getClient() {
        return DynamoDbClient.builder()
                .region(REGION)
                .build();
    }
}