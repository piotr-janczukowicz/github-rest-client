package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBulkRemoveEnterpriseTeamMembershipsPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubBulkRemoveEnterpriseTeamMembershipsPayload payload =
                EnterpriseTeamsCatalogFixtures.bulkRemoveEnterpriseTeamMembershipsPayload();

        assertThat(payload.usernames()).containsExactly("monalisa", "octocat");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUsernamesNull() {
        final GitHubBulkRemoveEnterpriseTeamMembershipsPayloadBuilder builder =
                GitHubBulkRemoveEnterpriseTeamMembershipsPayloadBuilder.builder(
                                EnterpriseTeamsCatalogFixtures.bulkRemoveEnterpriseTeamMembershipsPayload())
                        .usernames(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("usernames");
    }
}
