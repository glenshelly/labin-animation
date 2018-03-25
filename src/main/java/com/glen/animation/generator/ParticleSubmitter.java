package com.glen.animation.generator;

import com.glen.animation.AnimateParticles;

import java.util.function.Supplier;

/**
 * Submit auto-generated chambers
 */
public class ParticleSubmitter {

    public static void main(String[] args) {

        // Set these variables for what will be submitted
        final int numOrders = 10;
        final int chamberSize = 10_000_000;
        final int numberLeft = 2_000_000;
        final int numberRight = 2_000_000;
        int speed = 2;
        //////////


        // For large chambers, calculate speed in relation to chamber size
        // Limit speed to at least 1/20th the chamber size
        final int speedToUse = (chamberSize > 1000) ? chamberSize / 20 : speed;

        Supplier<String> thisSupplier = new SampleParticleGenerator.ChamberSupplier()
                .withChamberSize(chamberSize)
                .withApproximateNumberLeft(numberLeft)
                .withApproximateNumberRight(numberRight)
                .withTotalNumberOfOrders(numOrders);

        AnimateParticles ap = new AnimateParticles();
        for (long count = numOrders; count > 0; count--) {
            long start = System.currentTimeMillis();
            String x = thisSupplier.get();
            ap.animate(speedToUse, x);
            System.out.println("\n\n" + (System.currentTimeMillis() - start) + "ms to complete. speed=" + speedToUse + "; chamberSize=" + x.length());
            if (chamberSize < 200) {
                System.out.println("chamber=" + x);
            }
        }
    }
}


