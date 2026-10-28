package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateResourcePayloadTest {

    @Test
    void constructor_shouldCopyMetadata_whenMutableMap() {
        final GitHubUpdateResourcePayload source = CopilotSpacesCatalogFixtures.updateResourcePayload();
        final int size = source.metadata().size();
        final Map<String, Object> mutable = new HashMap<>(source.metadata());
        final GitHubUpdateResourcePayload sut = GitHubUpdateResourcePayloadBuilder.builder(source)
                .metadata(mutable)
                .build();
        mutable.clear();

        assertThat(sut.metadata()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveMetadataNull_whenNull() {
        final GitHubUpdateResourcePayloadBuilder builder = GitHubUpdateResourcePayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.updateResourcePayload())
                .metadata(null);

        final GitHubUpdateResourcePayload sut = builder.build();

        assertThat(sut.metadata()).isNull();
    }
}
