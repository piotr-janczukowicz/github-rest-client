module io.github.piotr_janczukowicz.githubapi {
    requires transitive okhttp3;
    requires okio;
    requires transitive tools.jackson.databind;
    requires org.slf4j;
    requires org.jspecify;
    requires common.util;
    requires transitive common.http;
    requires transitive common.json;
    requires static io.soabase.recordbuilder.core;
    requires static java.compiler;

    exports io.github.piotr_janczukowicz.github.api;
    exports io.github.piotr_janczukowicz.github.api.actions;
    exports io.github.piotr_janczukowicz.github.api.activity;
    exports io.github.piotr_janczukowicz.github.api.agentautomations;
    exports io.github.piotr_janczukowicz.github.api.agents;
    exports io.github.piotr_janczukowicz.github.api.agenttasks;
    exports io.github.piotr_janczukowicz.github.api.announcementbanners;
    exports io.github.piotr_janczukowicz.github.api.apps;
    exports io.github.piotr_janczukowicz.github.api.billing;
    exports io.github.piotr_janczukowicz.github.api.branches;
    exports io.github.piotr_janczukowicz.github.api.campaigns;
    exports io.github.piotr_janczukowicz.github.api.checks;
    exports io.github.piotr_janczukowicz.github.api.classroom;
    exports io.github.piotr_janczukowicz.github.api.codequality;
    exports io.github.piotr_janczukowicz.github.api.codescanning;
    exports io.github.piotr_janczukowicz.github.api.codesecurity;
    exports io.github.piotr_janczukowicz.github.api.codesofconduct;
    exports io.github.piotr_janczukowicz.github.api.codespaces;
    exports io.github.piotr_janczukowicz.github.api.collaborators;
    exports io.github.piotr_janczukowicz.github.api.commits;
    exports io.github.piotr_janczukowicz.github.api.copilot;
    exports io.github.piotr_janczukowicz.github.api.copilotspaces;
    exports io.github.piotr_janczukowicz.github.api.credentials;
    exports io.github.piotr_janczukowicz.github.api.dependabot;
    exports io.github.piotr_janczukowicz.github.api.dependencygraph;
    exports io.github.piotr_janczukowicz.github.api.deploykeys;
    exports io.github.piotr_janczukowicz.github.api.deployments;
    exports io.github.piotr_janczukowicz.github.api.emojis;
    exports io.github.piotr_janczukowicz.github.api.enterpriseadmin;
    exports io.github.piotr_janczukowicz.github.api.enterpriseteams;
    exports io.github.piotr_janczukowicz.github.api.gists;
    exports io.github.piotr_janczukowicz.github.api.gitdatabase;
    exports io.github.piotr_janczukowicz.github.api.gitignore;
    exports io.github.piotr_janczukowicz.github.api.interactions;
    exports io.github.piotr_janczukowicz.github.api.issues;
    exports io.github.piotr_janczukowicz.github.api.licenses;
    exports io.github.piotr_janczukowicz.github.api.markdown;
    exports io.github.piotr_janczukowicz.github.api.meta;
    exports io.github.piotr_janczukowicz.github.api.metrics;
    exports io.github.piotr_janczukowicz.github.api.migrations;
    exports io.github.piotr_janczukowicz.github.api.oauthauthorizations;
    exports io.github.piotr_janczukowicz.github.api.organizations;
    exports io.github.piotr_janczukowicz.github.api.packages;
    exports io.github.piotr_janczukowicz.github.api.pages;
    exports io.github.piotr_janczukowicz.github.api.privateregistries;
    exports io.github.piotr_janczukowicz.github.api.projects;
    exports io.github.piotr_janczukowicz.github.api.pullrequests;
    exports io.github.piotr_janczukowicz.github.api.ratelimit;
    exports io.github.piotr_janczukowicz.github.api.reactions;
    exports io.github.piotr_janczukowicz.github.api.releases;
    exports io.github.piotr_janczukowicz.github.api.repositories;
    exports io.github.piotr_janczukowicz.github.api.scim;
    exports io.github.piotr_janczukowicz.github.api.search;
    exports io.github.piotr_janczukowicz.github.api.secretscanning;
    exports io.github.piotr_janczukowicz.github.api.securityadvisories;
    exports io.github.piotr_janczukowicz.github.api.teams;
    exports io.github.piotr_janczukowicz.github.api.users;
    exports io.github.piotr_janczukowicz.github.client;
    exports io.github.piotr_janczukowicz.github.client.app;
    exports io.github.piotr_janczukowicz.github.client.exception;
    exports io.github.piotr_janczukowicz.github.client.pagination;
    exports io.github.piotr_janczukowicz.github.client.ratelimit;
    exports io.github.piotr_janczukowicz.github.json;
    exports io.github.piotr_janczukowicz.github.pagination;

    opens io.github.piotr_janczukowicz.github.api to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.actions to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.activity to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.agentautomations to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.agents to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.agenttasks to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.announcementbanners to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.apps to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.billing to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.branches to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.campaigns to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.checks to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.classroom to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.codequality to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.codescanning to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.codesecurity to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.codesofconduct to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.codespaces to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.collaborators to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.commits to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.copilot to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.copilotspaces to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.credentials to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.dependabot to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.dependencygraph to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.deploykeys to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.deployments to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.emojis to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.enterpriseadmin to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.enterpriseteams to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.gists to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.gitdatabase to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.gitignore to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.interactions to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.issues to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.licenses to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.markdown to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.meta to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.metrics to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.migrations to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.oauthauthorizations to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.organizations to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.packages to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.pages to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.privateregistries to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.projects to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.pullrequests to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.ratelimit to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.reactions to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.releases to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.repositories to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.scim to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.search to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.secretscanning to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.securityadvisories to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.teams to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.api.users to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.client to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.client.app to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.client.exception to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.client.pagination to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.client.ratelimit to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.json to
            tools.jackson.databind,
            common.util;
    opens io.github.piotr_janczukowicz.github.pagination to
            tools.jackson.databind,
            common.util;
}
