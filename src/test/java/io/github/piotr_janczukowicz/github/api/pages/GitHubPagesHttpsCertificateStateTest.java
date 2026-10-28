package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubPagesHttpsCertificateStateTest {

    @ParameterizedTest
    @CsvSource({
        "new,NEW",
        "authorization_created,AUTHORIZATION_CREATED",
        "authorization_pending,AUTHORIZATION_PENDING",
        "authorized,AUTHORIZED",
        "authorization_revoked,AUTHORIZATION_REVOKED",
        "issued,ISSUED",
        "uploaded,UPLOADED",
        "approved,APPROVED",
        "errored,ERRORED",
        "bad_authz,BAD_AUTHZ",
        "destroy_pending,DESTROY_PENDING",
        "dns_changed,DNS_CHANGED"
    })
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubPagesHttpsCertificateState value) {
        final GitHubPagesHttpsCertificateState decoded = GitHubPagesHttpsCertificateState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubPagesHttpsCertificateState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubPagesHttpsCertificateState decoded = GitHubPagesHttpsCertificateState.fromWire("brand_new_value");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubPagesHttpsCertificateState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
