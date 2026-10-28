package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGrantRepositoryAccessPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGrantRepositoryAccessPayload value = EnterpriseAdminCatalogFixtures.grantRepositoryAccessPayload();

        assertThat(GitHubGrantRepositoryAccessPayloadBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRepositoriesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGrantRepositoryAccessPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.grantRepositoryAccessPayload())
                .repositories(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
