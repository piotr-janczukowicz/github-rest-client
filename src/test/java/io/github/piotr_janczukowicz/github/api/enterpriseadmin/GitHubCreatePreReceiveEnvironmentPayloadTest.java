package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePreReceiveEnvironmentPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreatePreReceiveEnvironmentPayloadBuilder builder =
                GitHubCreatePreReceiveEnvironmentPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.createPreReceiveEnvironmentPayload())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenImageUrlNull() {
        final GitHubCreatePreReceiveEnvironmentPayloadBuilder builder =
                GitHubCreatePreReceiveEnvironmentPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.createPreReceiveEnvironmentPayload())
                        .imageUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("imageUrl");
    }
}
