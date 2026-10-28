package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateConfigurationPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.createConfigurationPayload())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionTooLong() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.createConfigurationPayload())
                .description("x".repeat(256))
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }
}
