package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetInformationForProvisionedUserPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubSetInformationForProvisionedUserPayload payload =
                ScimCatalogFixtures.setInformationForProvisionedUserPayload();

        assertThat(payload.userName()).isEqualTo("mona.octocat@okta.example.com");
        assertThat(payload.emails()).hasSize(1);
        assertThat(payload.active()).isTrue();
    }

    @Test
    void constructor_shouldKeepOptionalListsAbsent_whenSchemasAndGroupsNull() {
        final GitHubSetInformationForProvisionedUserPayload payload = new GitHubSetInformationForProvisionedUserPayload(
                null,
                null,
                null,
                null,
                null,
                "mona.octocat@okta.example.com",
                ScimCatalogFixtures.setInformationForProvisionedUserName(),
                List.of(ScimCatalogFixtures.setInformationForProvisionedUserEmail()));

        assertThat(payload.schemas()).isNull();
        assertThat(payload.groups()).isNull();
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenEmailsEmpty() {
        final GitHubSetInformationForProvisionedUserName name =
                ScimCatalogFixtures.setInformationForProvisionedUserName();

        final Throwable thrown = catchThrowable(() -> new GitHubSetInformationForProvisionedUserPayload(
                null, null, null, null, null, "mona.octocat@okta.example.com", name, List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
