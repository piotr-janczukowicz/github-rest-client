package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayloadTest {

    @Test
    void constructor_shouldThrow_whenRepositoryValuesNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryValues");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryValuesEmpty() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload(List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryValues");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryValuesExceedMax() {
        final List<GitHubRepositoryExternalPropertyPayload> values = IntStream.range(0, 101)
                .mapToObj(i -> new GitHubRepositoryExternalPropertyPayload("repo-" + i, null))
                .toList();

        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload(values));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryValues");
    }
}
