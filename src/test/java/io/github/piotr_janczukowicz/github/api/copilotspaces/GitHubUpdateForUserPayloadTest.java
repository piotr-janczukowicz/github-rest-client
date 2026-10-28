package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateForUserPayloadTest {

    @Test
    void constructor_shouldCopyResourcesAttributes_whenMutableList() {
        final GitHubUpdateForUserPayload source = CopilotSpacesCatalogFixtures.updateForUserPayload();
        final int size = source.resourcesAttributes().size();
        final List<GitHubCopilotSpaceResourceAttributeInput> mutable = new ArrayList<>(source.resourcesAttributes());
        final GitHubUpdateForUserPayload sut = GitHubUpdateForUserPayloadBuilder.builder(source)
                .resourcesAttributes(mutable)
                .build();
        mutable.clear();

        assertThat(sut.resourcesAttributes()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveResourcesAttributesNull_whenNull() {
        final GitHubUpdateForUserPayloadBuilder builder = GitHubUpdateForUserPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.updateForUserPayload())
                .resourcesAttributes(null);

        final GitHubUpdateForUserPayload sut = builder.build();

        assertThat(sut.resourcesAttributes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenGeneralInstructionsTooLong() {
        final GitHubUpdateForUserPayloadBuilder builder = GitHubUpdateForUserPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.updateForUserPayload())
                .generalInstructions("x".repeat(4001));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("generalInstructions");
    }

    @Test
    void constructor_shouldAcceptGeneralInstructions_whenAtLimit() {
        final GitHubUpdateForUserPayloadBuilder builder = GitHubUpdateForUserPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.updateForUserPayload())
                .generalInstructions("x".repeat(4000));

        final GitHubUpdateForUserPayload sut = builder.build();

        assertThat(sut.generalInstructions()).hasSize(4000);
    }
}
