package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateForOrgPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateForOrgPayloadBuilder builder = GitHubCreateForOrgPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.createForOrgPayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldCopyResourcesAttributes_whenMutableList() {
        final GitHubCreateForOrgPayload source = CopilotSpacesCatalogFixtures.createForOrgPayload();
        final int size = source.resourcesAttributes().size();
        final List<GitHubCopilotSpaceResourceAttributeInput> mutable = new ArrayList<>(source.resourcesAttributes());
        final GitHubCreateForOrgPayload sut = GitHubCreateForOrgPayloadBuilder.builder(source)
                .resourcesAttributes(mutable)
                .build();
        mutable.clear();

        assertThat(sut.resourcesAttributes()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveResourcesAttributesNull_whenNull() {
        final GitHubCreateForOrgPayloadBuilder builder = GitHubCreateForOrgPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.createForOrgPayload())
                .resourcesAttributes(null);

        final GitHubCreateForOrgPayload sut = builder.build();

        assertThat(sut.resourcesAttributes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenGeneralInstructionsTooLong() {
        final GitHubCreateForOrgPayloadBuilder builder = GitHubCreateForOrgPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.createForOrgPayload())
                .generalInstructions("x".repeat(4001));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("generalInstructions");
    }

    @Test
    void constructor_shouldAcceptGeneralInstructions_whenAtLimit() {
        final GitHubCreateForOrgPayloadBuilder builder = GitHubCreateForOrgPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.createForOrgPayload())
                .generalInstructions("x".repeat(4000));

        final GitHubCreateForOrgPayload sut = builder.build();

        assertThat(sut.generalInstructions()).hasSize(4000);
    }
}
