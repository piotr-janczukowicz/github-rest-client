package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateOrganizationDefinitionsPayloadTest {

    @Test
    void constructor_shouldThrow_whenPropertiesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrUpdateOrganizationDefinitionsPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("properties");
    }

    @Test
    void constructor_shouldThrow_whenPropertiesEmpty() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrUpdateOrganizationDefinitionsPayload(List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("properties");
    }

    @Test
    void constructor_shouldThrow_whenPropertiesExceedMax() {
        final List<GitHubCustomProperty> properties = IntStream.range(0, 101)
                .mapToObj(i -> OrganizationsCatalogFixtures.customProperty())
                .toList();

        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrUpdateOrganizationDefinitionsPayload(properties));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("properties");
    }
}
