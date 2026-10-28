package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Gateway orchestration phase. One of: `running`, `completed`, `failed`. */
@CatalogSchema(
        "components/schemas/ghes-get-manage-upgrade-download-status-response/properties/orchestration/properties/phase")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public enum GitHubManageUpgradeDownloadStatusOrchestrationPhase {
    RUNNING("running"),
    COMPLETED("completed"),
    FAILED("failed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubManageUpgradeDownloadStatusOrchestrationPhase> WIRE_INDEX =
            EnumUtils.index(
                    GitHubManageUpgradeDownloadStatusOrchestrationPhase.class,
                    GitHubManageUpgradeDownloadStatusOrchestrationPhase::wireValue);

    private final String wireValue;

    GitHubManageUpgradeDownloadStatusOrchestrationPhase(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * phase from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubManageUpgradeDownloadStatusOrchestrationPhase fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
