package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRemoveRepositoryAccessPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubRemoveRepositoryAccessPayload value =
                EnterpriseAdminCatalogFixtures.removeRepositoryAccessPayload();

        assertThat(GitHubRemoveRepositoryAccessPayloadBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRepositoriesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubRemoveRepositoryAccessPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.removeRepositoryAccessPayload())
                .repositories(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
