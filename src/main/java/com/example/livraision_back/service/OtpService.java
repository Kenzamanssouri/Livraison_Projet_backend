package com.example.livraision_back.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    // Store OTPs in memory
    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();

    // OTP validity time: 5 minutes
    private static final long OTP_VALID_DURATION = 5 * 60; // seconds

    public void saveOtp(String phoneNumber, String otp) {
        otpStore.put(phoneNumber, new OtpData(otp, Instant.now()));
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        OtpData otpData = otpStore.get(phoneNumber);
        if (otpData == null) {
            return false;
        }
        boolean isValid = otpData.getOtp().equals(otp) &&
            Instant.now().isBefore(otpData.getCreatedAt().plusSeconds(OTP_VALID_DURATION));

        // Remove OTP after verification attempt
        otpStore.remove(phoneNumber);
        return isValid;
    }

    // Scheduled task: clean expired OTPs every 1 minute
    @Scheduled(fixedRate = 60000)
    public void cleanExpiredOtps() {
        Instant now = Instant.now();
        otpStore.entrySet().removeIf(entry ->
            now.isAfter(entry.getValue().getCreatedAt().plusSeconds(OTP_VALID_DURATION))
        );
    }

    private static class OtpData {
        private final String otp;
        private final Instant createdAt;

        public OtpData(String otp, Instant createdAt) {
            this.otp = otp;
            this.createdAt = createdAt;
        }

        public String getOtp() {
            return otp;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }
    }
}
