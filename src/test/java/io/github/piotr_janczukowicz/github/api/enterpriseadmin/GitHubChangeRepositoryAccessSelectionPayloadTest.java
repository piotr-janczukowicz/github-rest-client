package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubChangeRepositoryAccessSelectionPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubChangeRepositoryAccessSelectionPayload value =
                EnterpriseAdminCatalogFixtures.changeRepositoryAccessSelectionPayload();

        assertThat(GitHubChangeRepositoryAccessSelectionPayloadBuilder.builder(value)
                        .build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveRepositoriesNull_whenNull() {
        final GitHubChangeRepositoryAccessSelectionPayload value =
                GitHubChangeRepositoryAccessSelectionPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.changeRepositoryAccessSelectionPayload())
                        .repositories(null)
                        .build();

        assertThat(value.repositories()).isNull();
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRepositorySelectionNull() {
        final Throwable thrown = catchThrowable(() -> GitHubChangeRepositoryAccessSelectionPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.changeRepositoryAccessSelectionPayload())
                .repositorySelection(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
