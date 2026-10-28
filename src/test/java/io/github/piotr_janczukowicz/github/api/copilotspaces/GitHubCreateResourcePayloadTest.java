package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateResourcePayloadTest {

    @Test
    void constructor_shouldThrow_whenResourceTypeNull() {
        final GitHubCreateResourcePayloadBuilder builder = GitHubCreateResourcePayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.createResourcePayload())
                .resourceType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("resourceType");
    }

    @Test
    void constructor_shouldThrow_whenMetadataNull() {
        final GitHubCreateResourcePayloadBuilder builder = GitHubCreateResourcePayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.createResourcePayload())
                .metadata(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("metadata");
    }

    @Test
    void constructor_shouldCopyMetadata_whenMutableMap() {
        final GitHubCreateResourcePayload source = CopilotSpacesCatalogFixtures.createResourcePayload();
        final int size = source.metadata().size();
        final Map<String, Object> mutable = new HashMap<>(source.metadata());
        final GitHubCreateResourcePayload sut = GitHubCreateResourcePayloadBuilder.builder(source)
                .metadata(mutable)
                .build();
        mutable.clear();

        assertThat(sut.metadata()).hasSize(size);
    }
}
