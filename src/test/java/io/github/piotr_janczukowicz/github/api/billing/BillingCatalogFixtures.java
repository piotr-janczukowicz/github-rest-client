package io.github.piotr_janczukowicz.github.api.billing;

import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public final class BillingCatalogFixtures {

    private BillingCatalogFixtures() {}

    public static GitHubAdvancedSecurityActiveCommitters advancedSecurityActiveCommitters() {
        return GitHubAdvancedSecurityActiveCommittersBuilder.builder()
                .totalAdvancedSecurityCommitters(25)
                .totalCount(2)
                .maximumAdvancedSecurityCommitters(4)
                .purchasedAdvancedSecurityCommitters(4)
                .repositories(List.of(advancedSecurityActiveCommittersRepository()))
                .build();
    }

    static GitHubAdvancedSecurityActiveCommittersRepository advancedSecurityActiveCommittersRepository() {
        return GitHubAdvancedSecurityActiveCommittersRepositoryBuilder.builder()
                .name("octocat/Hello-World")
                .advancedSecurityCommitters(25)
                .advancedSecurityCommittersBreakdown(List.of(advancedSecurityActiveCommittersUser()))
                .build();
    }

    static GitHubAdvancedSecurityActiveCommittersUser advancedSecurityActiveCommittersUser() {
        return GitHubAdvancedSecurityActiveCommittersUserBuilder.builder()
                .userLogin("octocat")
                .lastPushedDate("2021-11-03")
                .lastPushedEmail("monalisa@github.com")
                .build();
    }

    static GitHubBillingUsageReport billingUsageReport() {
        return GitHubBillingUsageReportBuilder.builder()
                .usageItems(List.of(billingUsageReportUsageItem()))
                .build();
    }

    static GitHubBillingUsageReportUsageItem billingUsageReportUsageItem() {
        return GitHubBillingUsageReportUsageItemBuilder.builder()
                .date("2026-08-01")
                .product("actions")
                .sku("actions_linux")
                .quantity(100)
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossAmount(4.0)
                .discountAmount(0.0)
                .netAmount(4.0)
                .organizationName("octo-org")
                .repositoryName("octo-org/octo-repo")
                .build();
    }

    static GitHubBillingUsageReportUser billingUsageReportUser() {
        return GitHubBillingUsageReportUserBuilder.builder()
                .usageItems(List.of(billingUsageReportUserUsageItem()))
                .build();
    }

    static GitHubBillingUsageReportUserUsageItem billingUsageReportUserUsageItem() {
        return GitHubBillingUsageReportUserUsageItemBuilder.builder()
                .date("2026-08-01")
                .product("actions")
                .sku("actions_linux")
                .quantity(100)
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossAmount(4.0)
                .discountAmount(0.0)
                .netAmount(4.0)
                .repositoryName("octo-org/octo-repo")
                .build();
    }

    static GitHubBillingUsageSummaryReportOrg billingUsageSummaryReportOrg() {
        return GitHubBillingUsageSummaryReportOrgBuilder.builder()
                .timePeriod(billingUsageSummaryReportOrgTimePeriod())
                .organization("octo-org")
                .repository("octo-org/octo-repo")
                .product("actions")
                .sku("actions_linux")
                .usageItems(List.of(billingUsageSummaryReportOrgUsageItem()))
                .build();
    }

    static GitHubBillingUsageSummaryReportOrgTimePeriod billingUsageSummaryReportOrgTimePeriod() {
        return GitHubBillingUsageSummaryReportOrgTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingUsageSummaryReportOrgUsageItem billingUsageSummaryReportOrgUsageItem() {
        return GitHubBillingUsageSummaryReportOrgUsageItemBuilder.builder()
                .product("actions")
                .sku("actions_linux")
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossQuantity(100.0)
                .grossAmount(4.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(4.0)
                .build();
    }

    static GitHubBillingUsageSummaryReportUser billingUsageSummaryReportUser() {
        return GitHubBillingUsageSummaryReportUserBuilder.builder()
                .timePeriod(billingUsageSummaryReportUserTimePeriod())
                .user("octocat")
                .repository("octo-org/octo-repo")
                .product("actions")
                .sku("actions_linux")
                .usageItems(List.of(billingUsageSummaryReportUserUsageItem()))
                .build();
    }

    static GitHubBillingUsageSummaryReportUserTimePeriod billingUsageSummaryReportUserTimePeriod() {
        return GitHubBillingUsageSummaryReportUserTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingUsageSummaryReportUserUsageItem billingUsageSummaryReportUserUsageItem() {
        return GitHubBillingUsageSummaryReportUserUsageItemBuilder.builder()
                .product("actions")
                .sku("actions_linux")
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossQuantity(100.0)
                .grossAmount(4.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(4.0)
                .build();
    }

    static GitHubBillingAiCreditUsageReportOrg billingAiCreditUsageReportOrg() {
        return GitHubBillingAiCreditUsageReportOrgBuilder.builder()
                .timePeriod(billingAiCreditUsageReportOrgTimePeriod())
                .organization("octo-org")
                .user("octocat")
                .product("actions")
                .model("Claude Sonnet")
                .usageItems(List.of(billingAiCreditUsageReportOrgUsageItem()))
                .build();
    }

    static GitHubBillingAiCreditUsageReportOrgTimePeriod billingAiCreditUsageReportOrgTimePeriod() {
        return GitHubBillingAiCreditUsageReportOrgTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingAiCreditUsageReportOrgUsageItem billingAiCreditUsageReportOrgUsageItem() {
        return GitHubBillingAiCreditUsageReportOrgUsageItemBuilder.builder()
                .product("actions")
                .sku("actions_linux")
                .model("Claude Sonnet")
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossQuantity(100.0)
                .grossAmount(4.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(4.0)
                .build();
    }

    static GitHubBillingAiCreditUsageReportUser billingAiCreditUsageReportUser() {
        return GitHubBillingAiCreditUsageReportUserBuilder.builder()
                .timePeriod(billingAiCreditUsageReportUserTimePeriod())
                .user("octocat")
                .product("actions")
                .model("Claude Sonnet")
                .usageItems(List.of(billingAiCreditUsageReportUserUsageItem()))
                .build();
    }

    static GitHubBillingAiCreditUsageReportUserTimePeriod billingAiCreditUsageReportUserTimePeriod() {
        return GitHubBillingAiCreditUsageReportUserTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingAiCreditUsageReportUserUsageItem billingAiCreditUsageReportUserUsageItem() {
        return GitHubBillingAiCreditUsageReportUserUsageItemBuilder.builder()
                .product("actions")
                .sku("actions_linux")
                .model("Claude Sonnet")
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossQuantity(100.0)
                .grossAmount(4.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(4.0)
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportOrg billingPremiumRequestUsageReportOrg() {
        return GitHubBillingPremiumRequestUsageReportOrgBuilder.builder()
                .timePeriod(billingPremiumRequestUsageReportOrgTimePeriod())
                .organization("octo-org")
                .user("octocat")
                .product("actions")
                .model("Claude Sonnet")
                .usageItems(List.of(billingPremiumRequestUsageReportOrgUsageItem()))
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportOrgTimePeriod billingPremiumRequestUsageReportOrgTimePeriod() {
        return GitHubBillingPremiumRequestUsageReportOrgTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportOrgUsageItem billingPremiumRequestUsageReportOrgUsageItem() {
        return GitHubBillingPremiumRequestUsageReportOrgUsageItemBuilder.builder()
                .product("actions")
                .sku("actions_linux")
                .model("Claude Sonnet")
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossQuantity(100.0)
                .grossAmount(4.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(4.0)
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportUser billingPremiumRequestUsageReportUser() {
        return GitHubBillingPremiumRequestUsageReportUserBuilder.builder()
                .timePeriod(billingPremiumRequestUsageReportUserTimePeriod())
                .user("octocat")
                .product("actions")
                .model("Claude Sonnet")
                .usageItems(List.of(billingPremiumRequestUsageReportUserUsageItem()))
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportUserTimePeriod billingPremiumRequestUsageReportUserTimePeriod() {
        return GitHubBillingPremiumRequestUsageReportUserTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportUserUsageItem billingPremiumRequestUsageReportUserUsageItem() {
        return GitHubBillingPremiumRequestUsageReportUserUsageItemBuilder.builder()
                .product("actions")
                .sku("actions_linux")
                .model("Claude Sonnet")
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossQuantity(100.0)
                .grossAmount(4.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(4.0)
                .build();
    }

    static GitHubBudget budget() {
        return GitHubBudgetBuilder.builder()
                .id("2066deda-923f-43f9-88d2-62395a28c0cd")
                .budgetType(GitHubBudgetBudgetType.SKU_PRICING)
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetScope(GitHubBudgetBudgetScope.ENTERPRISE)
                .budgetEntityName("octo-org")
                .user("octocat")
                .consumedAmount(120.5)
                .budgetProductSku("actions")
                .budgetAlerting(budgetBudgetAlerting())
                .expiresAt(LocalDate.of(2027, 12, 31))
                .build();
    }

    static GitHubBudgetBudgetAlerting budgetBudgetAlerting() {
        return GitHubBudgetBudgetAlertingBuilder.builder()
                .willAlert(false)
                .alertRecipients(List.of("octocat"))
                .build();
    }

    static GitHubGetBudgetBudgetAlerting getBudgetBudgetAlerting() {
        return GitHubGetBudgetBudgetAlertingBuilder.builder()
                .willAlert(false)
                .alertRecipients(List.of("octocat"))
                .build();
    }

    static GitHubBudgetBudgetType budgetBudgetType() {
        return GitHubBudgetBudgetType.SKU_PRICING;
    }

    static GitHubBudgetBudgetScope budgetBudgetScope() {
        return GitHubBudgetBudgetScope.ENTERPRISE;
    }

    static GitHubGetBudget getBudget() {
        return GitHubGetBudgetBuilder.builder()
                .id("2066deda-923f-43f9-88d2-62395a28c0cd")
                .budgetScope(GitHubGetBudgetBudgetScope.ENTERPRISE)
                .budgetEntityName("octo-org")
                .user("octocat")
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetProductSku("actions")
                .budgetType(GitHubGetBudgetBudgetType.PRODUCT_PRICING)
                .budgetAlerting(getBudgetBudgetAlerting())
                .consumedAmount(12.5)
                .build();
    }

    static GitHubGetBudgetBudgetType getBudgetBudgetType() {
        return GitHubGetBudgetBudgetType.PRODUCT_PRICING;
    }

    static GitHubGetBudgetBudgetScope getBudgetBudgetScope() {
        return GitHubGetBudgetBudgetScope.ENTERPRISE;
    }

    static GitHubGetAllBudgets getAllBudgets() {
        return GitHubGetAllBudgetsBuilder.builder()
                .budgets(List.of(budget()))
                .user("octocat")
                .effectiveBudget(getAllBudgetsEffectiveBudget())
                .hasNextPage(true)
                .totalCount(1)
                .build();
    }

    static GitHubGetAllBudgetsEffectiveBudget getAllBudgetsEffectiveBudget() {
        return GitHubGetAllBudgetsEffectiveBudgetBuilder.builder()
                .id("2066deda-923f-43f9-88d2-62395a28c0cd")
                .budgetAmount(500)
                .consumedAmount(120.5)
                .build();
    }

    static GitHubCreateBudget createBudget() {
        return GitHubCreateBudgetBuilder.builder()
                .message("Budget updated")
                .budget(createBudgetBudget())
                .build();
    }

    static GitHubCreateBudgetBudget createBudgetBudget() {
        return GitHubCreateBudgetBudgetBuilder.builder()
                .id("2066deda-923f-43f9-88d2-62395a28c0cd")
                .budgetScope(GitHubBudgetBudgetScope.ENTERPRISE)
                .budgetEntityName("octo-org")
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetProductSku("actions")
                .budgetType(GitHubBudgetBudgetType.SKU_PRICING)
                .budgetAlerting(createBudgetBudgetBudgetAlerting())
                .expiresAt(LocalDate.of(2027, 12, 31))
                .build();
    }

    static GitHubCreateBudgetBudgetBudgetAlerting createBudgetBudgetBudgetAlerting() {
        return GitHubCreateBudgetBudgetBudgetAlertingBuilder.builder()
                .willAlert(false)
                .alertRecipients(List.of("octocat"))
                .build();
    }

    static GitHubUpdateBudget updateBudget() {
        return GitHubUpdateBudgetBuilder.builder()
                .message("Budget updated")
                .budget(updateBudgetBudget())
                .build();
    }

    static GitHubUpdateBudgetBudget updateBudgetBudget() {
        return GitHubUpdateBudgetBudgetBuilder.builder()
                .id("2066deda-923f-43f9-88d2-62395a28c0cd")
                .budgetScope(GitHubBudgetBudgetScope.ENTERPRISE)
                .budgetEntityName("octo-org")
                .user("octocat")
                .consumedAmount(120.5)
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetProductSku("actions")
                .budgetType(GitHubBudgetBudgetType.SKU_PRICING)
                .budgetAlerting(updateBudgetBudgetBudgetAlerting())
                .expiresAt(LocalDate.of(2027, 12, 31))
                .build();
    }

    static GitHubUpdateBudgetBudgetBudgetAlerting updateBudgetBudgetBudgetAlerting() {
        return GitHubUpdateBudgetBudgetBudgetAlertingBuilder.builder()
                .willAlert(false)
                .alertRecipients(List.of("octocat"))
                .build();
    }

    static GitHubDeleteBudget deleteBudget() {
        return GitHubDeleteBudgetBuilder.builder()
                .message("Budget updated")
                .id("2066deda-923f-43f9-88d2-62395a28c0cd")
                .build();
    }

    static GitHubCreateOrganizationBudgetPayload createOrganizationBudgetPayload() {
        return GitHubCreateOrganizationBudgetPayloadBuilder.builder()
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetAlerting(createOrganizationBudgetBudgetAlerting())
                .budgetScope(GitHubCreateOrganizationBudgetBudgetScope.ORGANIZATION)
                .budgetEntityName("octo-org")
                .budgetType(GitHubCreateOrganizationBudgetBudgetType.BUNDLE_PRICING)
                .budgetProductSku("actions")
                .user("octocat")
                .expiresAt(LocalDate.of(2027, 12, 31))
                .build();
    }

    static GitHubCreateOrganizationBudgetBudgetAlerting createOrganizationBudgetBudgetAlerting() {
        return GitHubCreateOrganizationBudgetBudgetAlertingBuilder.builder()
                .willAlert(false)
                .alertRecipients(List.of("octocat"))
                .build();
    }

    static GitHubCreateOrganizationBudgetBudgetScope createOrganizationBudgetBudgetScope() {
        return GitHubCreateOrganizationBudgetBudgetScope.ORGANIZATION;
    }

    static GitHubCreateOrganizationBudgetBudgetType createOrganizationBudgetBudgetType() {
        return GitHubCreateOrganizationBudgetBudgetType.BUNDLE_PRICING;
    }

    static GitHubGetGithubBillingAiCreditUsageReportOrgFilter getGithubBillingAiCreditUsageReportOrgFilter() {
        return GitHubGetGithubBillingAiCreditUsageReportOrgFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .user("octocat")
                .model("Claude Sonnet")
                .product("actions")
                .build();
    }

    static GitHubGetAllBudgetsOrgFilter getAllBudgetsOrgFilter() {
        return GitHubGetAllBudgetsOrgFilterBuilder.builder()
                .scope(GitHubGetAllBudgetsOrgScope.ENTERPRISE)
                .user("octocat")
                .build();
    }

    static GitHubGetGithubBillingPremiumRequestUsageReportOrgFilter
            getGithubBillingPremiumRequestUsageReportOrgFilter() {
        return GitHubGetGithubBillingPremiumRequestUsageReportOrgFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .user("octocat")
                .model("Claude Sonnet")
                .product("actions")
                .build();
    }

    static GitHubGetGithubBillingUsageReportOrgFilter getGithubBillingUsageReportOrgFilter() {
        return GitHubGetGithubBillingUsageReportOrgFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubGetGithubBillingUsageSummaryReportOrgFilter getGithubBillingUsageSummaryReportOrgFilter() {
        return GitHubGetGithubBillingUsageSummaryReportOrgFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .repository("octo-org/octo-repo")
                .product("actions")
                .sku("actions_linux")
                .build();
    }

    static GitHubGetGithubBillingAiCreditUsageReportUserFilter getGithubBillingAiCreditUsageReportUserFilter() {
        return GitHubGetGithubBillingAiCreditUsageReportUserFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .model("Claude Sonnet")
                .product("actions")
                .build();
    }

    static GitHubGetGithubBillingPremiumRequestUsageReportUserFilter
            getGithubBillingPremiumRequestUsageReportUserFilter() {
        return GitHubGetGithubBillingPremiumRequestUsageReportUserFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .model("Claude Sonnet")
                .product("actions")
                .build();
    }

    static GitHubGetGithubBillingUsageReportUserFilter getGithubBillingUsageReportUserFilter() {
        return GitHubGetGithubBillingUsageReportUserFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubGetGithubBillingUsageSummaryReportUserFilter getGithubBillingUsageSummaryReportUserFilter() {
        return GitHubGetGithubBillingUsageSummaryReportUserFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .repository("octo-org/octo-repo")
                .product("actions")
                .sku("actions_linux")
                .build();
    }

    static GitHubGetAllBudgetsOrgScope getAllBudgetsOrgScope() {
        return GitHubGetAllBudgetsOrgScope.ENTERPRISE;
    }

    static GitHubUpdateBudgetOrgPayload updateBudgetOrgPayload() {
        return GitHubUpdateBudgetOrgPayloadBuilder.builder()
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetAlerting(createOrganizationBudgetBudgetAlerting())
                .expiresAt(OptionalNull.of(LocalDate.of(2027, 12, 31)))
                .build();
    }

    static GitHubGetAllBudgetsFilter getAllBudgetsFilter() {
        return GitHubGetAllBudgetsFilterBuilder.builder()
                .scope(GitHubGetAllBudgetsScope.ENTERPRISE)
                .user("octocat")
                .build();
    }

    static GitHubCreateBudgetPayloadBudgetAlerting createBudgetPayloadBudgetAlerting() {
        return GitHubCreateBudgetPayloadBudgetAlertingBuilder.builder()
                .willAlert(true)
                .alertRecipients(List.of("octocat"))
                .build();
    }

    static GitHubCreateBudgetPayload createBudgetPayload() {
        return GitHubCreateBudgetPayloadBuilder.builder()
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetAlerting(createBudgetPayloadBudgetAlerting())
                .budgetScope(GitHubCreateBudgetPayloadBudgetScope.ORGANIZATION)
                .budgetEntityName("octo-org")
                .budgetType(GitHubCreateBudgetPayloadBudgetType.PRODUCT_PRICING)
                .budgetProductSku("actions")
                .user("octocat")
                .expiresAt(LocalDate.of(2027, 12, 31))
                .build();
    }

    static GitHubUpdateBudgetPayloadBudgetAlerting updateBudgetPayloadBudgetAlerting() {
        return GitHubUpdateBudgetPayloadBudgetAlertingBuilder.builder()
                .willAlert(true)
                .alertRecipients(List.of("octocat"))
                .build();
    }

    static GitHubUpdateBudgetPayload updateBudgetPayload() {
        return GitHubUpdateBudgetPayloadBuilder.builder()
                .budgetAmount(500)
                .preventFurtherUsage(true)
                .budgetAlerting(updateBudgetPayloadBudgetAlerting())
                .expiresAt(OptionalNull.of(LocalDate.of(2027, 12, 31)))
                .build();
    }

    static GitHubGetBudgetUserStatesFilter getBudgetUserStatesFilter() {
        return GitHubGetBudgetUserStatesFilterBuilder.builder()
                .sortOrder(GitHubGetBudgetUserStatesSortOrder.ASCENDING)
                .user("octocat")
                .thresholdLowerBound(10)
                .thresholdUpperBound(90)
                .build();
    }

    static GitHubGetBudgetUserStatesUserState getBudgetUserStatesUserState() {
        return GitHubGetBudgetUserStatesUserStateBuilder.builder()
                .user("octocat")
                .consumedAmount(42.5)
                .targetAmount(100.0)
                .overrideBudgetId("2066deda-923f-43f9")
                .build();
    }

    static GitHubGetBudgetUserStates getBudgetUserStates() {
        return GitHubGetBudgetUserStatesBuilder.builder()
                .userStates(List.of(getBudgetUserStatesUserState()))
                .hasNextPage(false)
                .totalCount(1)
                .build();
    }

    static GitHubGetAllCostCentersFilter getAllCostCentersFilter() {
        return GitHubGetAllCostCentersFilterBuilder.builder()
                .state(GitHubGetAllCostCentersState.ACTIVE)
                .build();
    }

    static GitHubGetAllCostCentersCostCenterResource getAllCostCentersCostCenterResource() {
        return GitHubGetAllCostCentersCostCenterResourceBuilder.builder()
                .type("User")
                .name("octocat")
                .build();
    }

    static GitHubGetAllCostCentersCostCenterAiCreditPoolState getAllCostCentersCostCenterAiCreditPoolState() {
        return GitHubGetAllCostCentersCostCenterAiCreditPoolStateBuilder.builder()
                .targetAmount(1000.0)
                .currentAmount(250.5)
                .build();
    }

    static GitHubGetAllCostCentersCostCenter getAllCostCentersCostCenter() {
        return GitHubGetAllCostCentersCostCenterBuilder.builder()
                .id("cc-1a2b3c")
                .name("Octo cost center")
                .state(GitHubGetAllCostCentersCostCenterState.ACTIVE)
                .azureSubscription("00000000-0000-0000-0000-000000000000")
                .aiCreditPoolEnabled(true)
                .aiCreditPoolState(getAllCostCentersCostCenterAiCreditPoolState())
                .resources(List.of(getAllCostCentersCostCenterResource()))
                .build();
    }

    static GitHubGetAllCostCenters getAllCostCenters() {
        return GitHubGetAllCostCentersBuilder.builder()
                .costCenters(List.of(getAllCostCentersCostCenter()))
                .build();
    }

    static GitHubCreateCostCenterPayload createCostCenterPayload() {
        return GitHubCreateCostCenterPayloadBuilder.builder()
                .name("Octo cost center")
                .aiCreditPoolEnabled(true)
                .build();
    }

    static GitHubCreateCostCenterResponseResource createCostCenterResponseResource() {
        return GitHubCreateCostCenterResponseResourceBuilder.builder()
                .type("Repo")
                .name("octo-org/octo-repo")
                .build();
    }

    static GitHubCreateCostCenterResponseAiCreditPoolState createCostCenterResponseAiCreditPoolState() {
        return GitHubCreateCostCenterResponseAiCreditPoolStateBuilder.builder()
                .targetAmount(1000.0)
                .currentAmount(0.0)
                .build();
    }

    static GitHubCreateCostCenterResponse createCostCenterResponse() {
        return GitHubCreateCostCenterResponseBuilder.builder()
                .id("cc-1a2b3c")
                .name("Octo cost center")
                .azureSubscription("00000000-0000-0000-0000-000000000000")
                .state(GitHubCreateCostCenterResponseState.ACTIVE)
                .resources(List.of(createCostCenterResponseResource()))
                .aiCreditPoolEnabled(true)
                .aiCreditPoolState(createCostCenterResponseAiCreditPoolState())
                .build();
    }

    static GitHubGetCostCenterResource getCostCenterResource() {
        return GitHubGetCostCenterResourceBuilder.builder()
                .type("User")
                .name("octocat")
                .build();
    }

    static GitHubGetCostCenterAiCreditPoolState getCostCenterAiCreditPoolState() {
        return GitHubGetCostCenterAiCreditPoolStateBuilder.builder()
                .targetAmount(1000.0)
                .currentAmount(250.5)
                .build();
    }

    static GitHubGetCostCenter getCostCenter() {
        return GitHubGetCostCenterBuilder.builder()
                .id("cc-1a2b3c")
                .name("Octo cost center")
                .azureSubscription("00000000-0000-0000-0000-000000000000")
                .state(GitHubGetCostCenterState.ACTIVE)
                .resources(List.of(getCostCenterResource()))
                .hasNextPage(false)
                .aiCreditPoolEnabled(true)
                .aiCreditPoolState(getCostCenterAiCreditPoolState())
                .build();
    }

    static GitHubUpdateCostCenterPayload updateCostCenterPayload() {
        return GitHubUpdateCostCenterPayloadBuilder.builder()
                .name("Renamed cost center")
                .aiCreditPoolEnabled(false)
                .build();
    }

    static GitHubUpdateCostCenterResource updateCostCenterResource() {
        return GitHubUpdateCostCenterResourceBuilder.builder()
                .type("Org")
                .name("octo-org")
                .build();
    }

    static GitHubUpdateCostCenterAiCreditPoolState updateCostCenterAiCreditPoolState() {
        return GitHubUpdateCostCenterAiCreditPoolStateBuilder.builder()
                .targetAmount(1000.0)
                .currentAmount(250.5)
                .build();
    }

    static GitHubUpdateCostCenter updateCostCenter() {
        return GitHubUpdateCostCenterBuilder.builder()
                .id("cc-1a2b3c")
                .name("Renamed cost center")
                .azureSubscription("00000000-0000-0000-0000-000000000000")
                .state(GitHubUpdateCostCenterState.ACTIVE)
                .resources(List.of(updateCostCenterResource()))
                .aiCreditPoolEnabled(false)
                .aiCreditPoolState(updateCostCenterAiCreditPoolState())
                .build();
    }

    static GitHubDeleteCostCenter deleteCostCenter() {
        return GitHubDeleteCostCenterBuilder.builder()
                .message("Cost center deleted.")
                .id("cc-1a2b3c")
                .name("Octo cost center")
                .costCenterState(GitHubDeleteCostCenterCostCenterState.COST_CENTER_ARCHIVED)
                .build();
    }

    static GitHubAddResourceToCostCenterPayload addResourceToCostCenterPayload() {
        return GitHubAddResourceToCostCenterPayloadBuilder.builder()
                .users(List.of("octocat"))
                .organizations(List.of("octo-org"))
                .repositories(List.of("octo-org/octo-repo"))
                .enterpriseTeams(List.of("octo-team"))
                .build();
    }

    static GitHubAddResourceToCostCenterResponseReassignedResource addResourceToCostCenterResponseReassignedResource() {
        return GitHubAddResourceToCostCenterResponseReassignedResourceBuilder.builder()
                .resourceType("User")
                .name("octocat")
                .previousCostCenter("Default")
                .build();
    }

    static GitHubAddResourceToCostCenterResponse addResourceToCostCenterResponse() {
        return GitHubAddResourceToCostCenterResponseBuilder.builder()
                .message("Resources added.")
                .reassignedResources(List.of(addResourceToCostCenterResponseReassignedResource()))
                .build();
    }

    static GitHubRemoveResourceFromCostCenterPayload removeResourceFromCostCenterPayload() {
        return GitHubRemoveResourceFromCostCenterPayloadBuilder.builder()
                .users(List.of("octocat"))
                .organizations(List.of("octo-org"))
                .repositories(List.of("octo-org/octo-repo"))
                .enterpriseTeams(List.of("octo-team"))
                .build();
    }

    static GitHubRemoveResourceFromCostCenterResponse removeResourceFromCostCenterResponse() {
        return GitHubRemoveResourceFromCostCenterResponseBuilder.builder()
                .message("Resources removed.")
                .build();
    }

    static GitHubGetGithubBillingUsageReportGheFilter getGithubBillingUsageReportGheFilter() {
        return GitHubGetGithubBillingUsageReportGheFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .costCenterId("cc-1a2b3c")
                .build();
    }

    static GitHubGetGithubBillingAiCreditUsageReportGheFilter getGithubBillingAiCreditUsageReportGheFilter() {
        return GitHubGetGithubBillingAiCreditUsageReportGheFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .organization("octo-org")
                .user("octocat")
                .model("gpt-5")
                .product("copilot")
                .costCenterId("cc-1a2b3c")
                .build();
    }

    static GitHubGetGithubBillingPremiumRequestUsageReportGheFilter
            getGithubBillingPremiumRequestUsageReportGheFilter() {
        return GitHubGetGithubBillingPremiumRequestUsageReportGheFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .organization("octo-org")
                .user("octocat")
                .model("gpt-5")
                .product("copilot")
                .costCenterId("cc-1a2b3c")
                .build();
    }

    static GitHubGetGithubBillingUsageSummaryReportGheFilter getGithubBillingUsageSummaryReportGheFilter() {
        return GitHubGetGithubBillingUsageSummaryReportGheFilterBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .organization("octo-org")
                .repository("octo-org/octo-repo")
                .product("actions")
                .sku("actions_linux")
                .costCenterId("cc-1a2b3c")
                .build();
    }

    static GitHubBillingAiCreditUsageReportGheTimePeriod billingAiCreditUsageReportGheTimePeriod() {
        return GitHubBillingAiCreditUsageReportGheTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingAiCreditUsageReportGheCostCenter billingAiCreditUsageReportGheCostCenter() {
        return GitHubBillingAiCreditUsageReportGheCostCenterBuilder.builder()
                .id("cc-1a2b3c")
                .name("Octo cost center")
                .build();
    }

    static GitHubBillingAiCreditUsageReportGheUsageItem billingAiCreditUsageReportGheUsageItem() {
        return GitHubBillingAiCreditUsageReportGheUsageItemBuilder.builder()
                .product("copilot")
                .sku("copilot_ai_credit")
                .model("gpt-5")
                .unitType("ai-credits")
                .pricePerUnit(0.01)
                .grossQuantity(100.0)
                .grossAmount(1.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(1.0)
                .build();
    }

    static GitHubBillingAiCreditUsageReportGhe billingAiCreditUsageReportGhe() {
        return GitHubBillingAiCreditUsageReportGheBuilder.builder()
                .timePeriod(billingAiCreditUsageReportGheTimePeriod())
                .enterprise("octo-enterprise")
                .user("octocat")
                .organization("octo-org")
                .product("copilot")
                .model("gpt-5")
                .costCenter(billingAiCreditUsageReportGheCostCenter())
                .usageItems(List.of(billingAiCreditUsageReportGheUsageItem()))
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportGheTimePeriod billingPremiumRequestUsageReportGheTimePeriod() {
        return GitHubBillingPremiumRequestUsageReportGheTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportGheCostCenter billingPremiumRequestUsageReportGheCostCenter() {
        return GitHubBillingPremiumRequestUsageReportGheCostCenterBuilder.builder()
                .id("cc-1a2b3c")
                .name("Octo cost center")
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportGheUsageItem billingPremiumRequestUsageReportGheUsageItem() {
        return GitHubBillingPremiumRequestUsageReportGheUsageItemBuilder.builder()
                .product("copilot")
                .sku("premium_request")
                .model("gpt-5")
                .unitType("ai-credits")
                .pricePerUnit(0.01)
                .grossQuantity(100.0)
                .grossAmount(1.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(1.0)
                .build();
    }

    static GitHubBillingPremiumRequestUsageReportGhe billingPremiumRequestUsageReportGhe() {
        return GitHubBillingPremiumRequestUsageReportGheBuilder.builder()
                .timePeriod(billingPremiumRequestUsageReportGheTimePeriod())
                .enterprise("octo-enterprise")
                .user("octocat")
                .organization("octo-org")
                .product("copilot")
                .model("gpt-5")
                .costCenter(billingPremiumRequestUsageReportGheCostCenter())
                .usageItems(List.of(billingPremiumRequestUsageReportGheUsageItem()))
                .build();
    }

    static GitHubBillingUsageSummaryReportGheTimePeriod billingUsageSummaryReportGheTimePeriod() {
        return GitHubBillingUsageSummaryReportGheTimePeriodBuilder.builder()
                .year(2026)
                .month(8)
                .day(1)
                .build();
    }

    static GitHubBillingUsageSummaryReportGheCostCenter billingUsageSummaryReportGheCostCenter() {
        return GitHubBillingUsageSummaryReportGheCostCenterBuilder.builder()
                .id("cc-1a2b3c")
                .name("Octo cost center")
                .build();
    }

    static GitHubBillingUsageSummaryReportGheUsageItem billingUsageSummaryReportGheUsageItem() {
        return GitHubBillingUsageSummaryReportGheUsageItemBuilder.builder()
                .product("actions")
                .sku("actions_linux")
                .unitType("minutes")
                .pricePerUnit(0.04)
                .grossQuantity(100.0)
                .grossAmount(4.0)
                .discountQuantity(0.0)
                .discountAmount(0.0)
                .netQuantity(100.0)
                .netAmount(4.0)
                .build();
    }

    static GitHubBillingUsageSummaryReportGhe billingUsageSummaryReportGhe() {
        return GitHubBillingUsageSummaryReportGheBuilder.builder()
                .timePeriod(billingUsageSummaryReportGheTimePeriod())
                .enterprise("octo-enterprise")
                .organization("octo-org")
                .repository("octo-org/octo-repo")
                .product("actions")
                .sku("actions_linux")
                .costCenter(billingUsageSummaryReportGheCostCenter())
                .usageItems(List.of(billingUsageSummaryReportGheUsageItem()))
                .build();
    }

    static GitHubUsageReportExport usageReportExport() {
        return GitHubUsageReportExportBuilder.builder()
                .id("5c7b1f0e-1a2b-4c3d-8e9f-0a1b2c3d4e5f")
                .reportType(GitHubUsageReportExportReportType.DETAILED)
                .startDate(LocalDate.of(2026, 8, 1))
                .endDate(LocalDate.of(2026, 8, 31))
                .status(GitHubUsageReportExportStatus.COMPLETED)
                .downloadUrls(List.of("https://example.com/usage-report.csv"))
                .createdAt(Instant.parse("2026-09-01T10:15:30Z"))
                .actor("octocat")
                .build();
    }

    static GitHubUsageReportExportList usageReportExportList() {
        return GitHubUsageReportExportListBuilder.builder()
                .usageReportExports(List.of(usageReportExport()))
                .build();
    }

    static GitHubCreateUsageReportExportPayload createUsageReportExportPayload() {
        return GitHubCreateUsageReportExportPayloadBuilder.builder()
                .reportType(GitHubCreateUsageReportExportPayloadReportType.DETAILED)
                .startDate(LocalDate.of(2026, 8, 1))
                .endDate(LocalDate.of(2026, 8, 31))
                .sendEmail(true)
                .build();
    }

    static GitHubGetGithubAdvancedSecurityBillingOrgFilter getGithubAdvancedSecurityBillingOrgFilter() {
        return GitHubGetGithubAdvancedSecurityBillingOrgFilterBuilder.builder()
                .advancedSecurityProduct(GitHubGetAdvancedSecurityBillingOrgProduct.CODE_SECURITY)
                .build();
    }
}
