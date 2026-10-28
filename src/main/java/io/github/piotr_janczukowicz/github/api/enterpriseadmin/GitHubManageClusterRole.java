package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHubManageClusterRole */
@CatalogSchema("components/schemas/ghes-cluster-roles")
@Flavors(GitHubFlavor.GHES)
public enum GitHubManageClusterRole {
    BLANK("Blank"),
    ACTIONSSERVER("ActionsServer"),
    CONSULSERVER("ConsulServer"),
    ELASTICSEARCHSERVER("ElasticsearchServer"),
    GITSERVER("GitServer"),
    JOBSERVER("JobServer"),
    LAUNCHSERVER("LaunchServer"),
    MEMCACHESERVER("MemcacheServer"),
    METRICSSERVER("MetricsServer"),
    MSSQLSERVER("MssqlServer"),
    MYSQLSERVER("MysqlServer"),
    PAGESSERVER("PagesServer"),
    REDISSERVER("RedisServer"),
    STORAGESERVER("StorageServer"),
    WEBSERVER("WebServer"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubManageClusterRole> WIRE_INDEX =
            EnumUtils.index(GitHubManageClusterRole.class, GitHubManageClusterRole::wireValue);

    private final String wireValue;

    GitHubManageClusterRole(final String wireValue) {
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
     * ManageClusterRole from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubManageClusterRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
