package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateLdapMappingForTeamPayloadTest {

    @Test
    void constructor_shouldThrow_whenLdapDnNull() {
        final GitHubUpdateLdapMappingForTeamPayloadBuilder builder =
                GitHubUpdateLdapMappingForTeamPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateLdapMappingForTeamPayload())
                        .ldapDn(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ldapDn");
    }
}
