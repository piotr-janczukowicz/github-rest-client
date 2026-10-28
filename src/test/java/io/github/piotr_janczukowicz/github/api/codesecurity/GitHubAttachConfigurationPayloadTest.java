package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAttachConfigurationPayloadTest {

    @Test
    void constructor_shouldThrow_whenScopeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAttachConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.attachConfigurationPayload())
                .scope(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("scope");
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubAttachConfigurationPayload payload = GitHubAttachConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.attachConfigurationPayload())
                .selectedRepositoryIds(null)
                .build();

        assertThat(payload.selectedRepositoryIds()).isNull();
    }
}
