package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAttachEnterpriseConfigurationPayloadTest {

    @Test
    void constructor_shouldThrow_whenScopeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAttachEnterpriseConfigurationPayloadBuilder.builder(
                        CodeSecurityCatalogFixtures.attachEnterpriseConfigurationPayload())
                .scope(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("scope");
    }
}
