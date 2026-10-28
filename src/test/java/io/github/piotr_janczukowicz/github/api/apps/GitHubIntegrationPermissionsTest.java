package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIntegrationPermissionsTest {

    @Test
    void additionalPropertiesForJson_shouldExposeAdditionalProperties_whenPresent() {
        final GitHubIntegrationPermissions permissions = GitHubIntegrationPermissionsBuilder.builder()
                .issues("read")
                .additionalProperties(Map.of("single_file", "write"))
                .build();

        assertThat(permissions.additionalPropertiesForJson()).containsExactly(Map.entry("single_file", "write"));
    }
}
