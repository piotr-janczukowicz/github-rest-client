package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrgPayloadTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubCreateOrgPayloadBuilder builder = GitHubCreateOrgPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createOrgPayload())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldThrow_whenAdminNull() {
        final GitHubCreateOrgPayloadBuilder builder = GitHubCreateOrgPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createOrgPayload())
                .admin(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("admin");
    }
}
