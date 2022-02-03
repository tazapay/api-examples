package com.core;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientConfig {
    private final String baseUrl;
    @Builder.Default
    private final Long readTimeOutInMilliSecs = 50000L;
    @Builder.Default
    private final Long writeTimeOutInMilliSecs = 50000L;
    @Builder.Default
    private final Long connectTimeoutInMilliSecs = 50000L;
}
