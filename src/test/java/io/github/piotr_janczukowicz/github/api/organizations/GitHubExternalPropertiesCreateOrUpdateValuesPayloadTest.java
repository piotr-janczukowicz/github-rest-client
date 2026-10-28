package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalPropertiesCreateOrUpdateValuesPayloadTest {

    @Test
    void constructor_shouldThrow_whenRepositoryNamesNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesCreateOrUpdateValuesPayload(null, List.of()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryNames");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNamesEmpty() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesCreateOrUpdateValuesPayload(List.of(), List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryNames");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNamesExceedMax() {
        final List<String> repositoryNames =
                IntStream.range(0, 31).mapToObj(i -> "repo-" + i).toList();

        final Throwable thrown = catchThrowable(
                () -> new GitHubExternalPropertiesCreateOrUpdateValuesPayload(repositoryNames, List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryNames");
    }

    @Test
    void constructor_shouldThrow_whenPropertiesNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubExternalPropertiesCreateOrUpdateValuesPayload(List.of("Hello-World"), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("properties");
    }
}
