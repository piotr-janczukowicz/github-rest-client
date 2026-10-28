package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateEnterpriseDefinitionsPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubCreateOrUpdateEnterpriseDefinitionsPayload value =
                EnterpriseAdminCatalogFixtures.createOrUpdateEnterpriseDefinitionsPayload();

        assertThat(GitHubCreateOrUpdateEnterpriseDefinitionsPayloadBuilder.builder(value)
                        .build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenPropertiesEmpty() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateOrUpdateEnterpriseDefinitionsPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createOrUpdateEnterpriseDefinitionsPayload())
                .properties(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
