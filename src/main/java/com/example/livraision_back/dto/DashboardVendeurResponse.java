package com.example.livraision_back.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardVendeurResponse {

    private Long totalCommandes;
    private Long today;
    private Long week;

    private Map<String, Long> status;

    private Double revenueTotal;
    private Double revenueToday;
    private Double revenueWeek;
    private Double avgBasket;

    private PaymentStatsDTO paymentStats;

    @Data
    public static class PaymentStatsDTO {
        private Double cash; // en %
        private Double card; // en %
    }
}
