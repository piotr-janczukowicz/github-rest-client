package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGrantCredentialAuthorizationsPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGrantCredentialAuthorizationsPayload value =
                EnterpriseAdminCatalogFixtures.grantCredentialAuthorizationsPayload();

        assertThat(value.credentialId()).isEqualTo("123");
        assertThat(value.credentialType()).isEqualTo(GitHubGrantCredentialAuthorizationsCredentialType.CLASSIC_PAT);
        assertThat(value.organizations()).containsExactly("octo-org");
    }

    @Test
    void constructor_shouldRejectOrganizations_whenEmpty() {
        final Throwable thrown = catchThrowable(() -> GitHubGrantCredentialAuthorizationsPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.grantCredentialAuthorizationsPayload())
                .organizations(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
