package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateConfigurationForEnterprisePayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateConfigurationForEnterprisePayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.createConfigurationForEnterprisePayload())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionTooLong() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateConfigurationForEnterprisePayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.createConfigurationForEnterprisePayload())
                .description("x".repeat(256))
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }
}
