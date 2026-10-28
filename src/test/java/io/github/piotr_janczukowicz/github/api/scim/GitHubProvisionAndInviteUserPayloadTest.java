package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProvisionAndInviteUserPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubProvisionAndInviteUserPayload payload = ScimCatalogFixtures.provisionAndInviteUserPayload();

        assertThat(payload.userName()).isEqualTo("mona.octocat@okta.example.com");
        assertThat(payload.emails()).hasSize(1);
        assertThat(payload.active()).isTrue();
    }

    @Test
    void constructor_shouldKeepOptionalListsAbsent_whenSchemasAndGroupsNull() {
        final GitHubProvisionAndInviteUserPayload payload = new GitHubProvisionAndInviteUserPayload(
                "mona.octocat@okta.example.com",
                null,
                ScimCatalogFixtures.provisionAndInviteUserName(),
                List.of(ScimCatalogFixtures.provisionAndInviteUserEmail()),
                null,
                null,
                null,
                null);

        assertThat(payload.schemas()).isNull();
        assertThat(payload.groups()).isNull();
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenEmailsEmpty() {
        final GitHubProvisionAndInviteUserName name = ScimCatalogFixtures.provisionAndInviteUserName();

        final Throwable thrown = catchThrowable(() -> new GitHubProvisionAndInviteUserPayload(
                "mona.octocat@okta.example.com", null, name, List.of(), null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
