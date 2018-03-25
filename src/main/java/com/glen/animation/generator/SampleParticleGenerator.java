package com.glen.animation.generator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Generate a chamber based on the given criteria
 */
public class SampleParticleGenerator {

    private static final byte ASCII_L_INDICATOR = 76;
    private static final byte ASCII_R_INDICATOR = 82;
    private static final byte ASCII_PERIOD = 46;

    public static class ChamberSupplier implements Supplier<String>, Serializable {
        private int chambersGenerated;
        private int totalNumberOfOrders = 1;
        private int chamberSize = 10, numberLeft = 1, numberRight = 1;

        public ChamberSupplier withTotalNumberOfOrders(int totalOrders) {
            this.totalNumberOfOrders = totalOrders;
            return this;
        }

        public ChamberSupplier withChamberSize(int chamberSize) {
            this.chamberSize = chamberSize;
            return this;
        }

        public ChamberSupplier withApproximateNumberLeft(int numberLeft) {
            this.numberLeft = numberLeft;
            return this;
        }

        public ChamberSupplier withApproximateNumberRight(int numberRight) {
            this.numberRight = numberRight;
            return this;
        }


        @Override
        public String get() {

            if (++chambersGenerated <= totalNumberOfOrders) {

                byte[] chamber = new byte[this.chamberSize];
                Arrays.fill(chamber, ASCII_PERIOD);

                // Approximation only
                int placedCount = 0;
                for (int i = chamberSize - 1; i >= 0 && placedCount < numberLeft; i = i - 3) {
                    chamber[i] = ASCII_L_INDICATOR;
                    placedCount++;
                }

                // Approximation only
                placedCount = 0;
                for (int i = 0; i < chamberSize && placedCount < numberRight; i = i + 4) {
                    chamber[i] = ASCII_R_INDICATOR;
                }

                return new String(chamber);
            } else {
                return null;
            }
        }
    }
}