package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDetachConfigurationPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedRepositoryIdsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubDetachConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.detachConfigurationPayload())
                .selectedRepositoryIds(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedRepositoryIds");
    }

    @Test
    void constructor_shouldThrow_whenSelectedRepositoryIdsEmpty() {
        final Throwable thrown = catchThrowable(() -> GitHubDetachConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.detachConfigurationPayload())
                .selectedRepositoryIds(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selectedRepositoryIds");
    }

    @Test
    void constructor_shouldThrow_whenSelectedRepositoryIdsExceedMax() {
        final Throwable thrown = catchThrowable(() -> GitHubDetachConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.detachConfigurationPayload())
                .selectedRepositoryIds(
                        IntStream.range(0, 251).mapToObj(Long::valueOf).toList())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selectedRepositoryIds");
    }
}
