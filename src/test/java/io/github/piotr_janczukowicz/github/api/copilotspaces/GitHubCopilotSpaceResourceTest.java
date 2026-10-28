package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpaceResourceTest {

    @Test
    void constructor_shouldThrow_whenResourceTypeNull() {
        final GitHubCopilotSpaceResourceBuilder builder = GitHubCopilotSpaceResourceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceResource())
                .resourceType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("resourceType");
    }

    @Test
    void constructor_shouldThrow_whenMetadataNull() {
        final GitHubCopilotSpaceResourceBuilder builder = GitHubCopilotSpaceResourceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceResource())
                .metadata(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("metadata");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubCopilotSpaceResourceBuilder builder = GitHubCopilotSpaceResourceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceResource())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubCopilotSpaceResourceBuilder builder = GitHubCopilotSpaceResourceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceResource())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }
}
