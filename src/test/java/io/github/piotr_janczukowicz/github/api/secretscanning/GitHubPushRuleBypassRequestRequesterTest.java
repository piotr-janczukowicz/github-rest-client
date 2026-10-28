package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPushRuleBypassRequestRequesterTest {

    @Test
    void constructor_shouldKeepActorName_whenBuilt() {
        final GitHubPushRuleBypassRequestRequester sut = SecretScanningCatalogFixtures.pushRuleBypassRequestRequester();

        assertThat(sut.actorName()).isEqualTo("monalisa");
    }
}
