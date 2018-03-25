package com.glen.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Determine and render periodic location of moving particles in a linear chamber
 */
public class AnimateParticles {


    private static final byte ASCII_L_INDICATOR = 76;
    private static final byte ASCII_R_INDICATOR = 82;
    private static final byte ASCII_X_INDICATOR = 88;
    private static final byte ASCII_PERIOD = 46;
    private static final int OUT_OF_CHAMBER = Integer.MIN_VALUE;

    /**
     * Required two arguments: speed, and String to animate
     *
     * @param args two arguments expected
     */
    public static void main(String args[]) {
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("two arguments expected: speed, and String to animate");
        }
        AnimateParticles ml = new AnimateParticles();

        int speed = Integer.valueOf(args[0]);
        String input = args[1];
        String[] results = ml.animate(speed, input);

        renderMessage("results of speed=" + speed + " and input=" + input);
        for (String snapshot : results) {
            renderMessage(snapshot);
        }
    }


    /**
     * Given a description of a chamber with left- and right-moving particles (provided by the input String),
     *  determine the location at fixed intervals (provided by the given speed)
     *
     * @param speed the amount each particle moves, per 'tick'
     * @param inputString a depiction of the particle chamber at the start
     * @return a String array, with the various snapshots of the particle locations after each tick o' the clock
     */
    public String[] animate(final int speed, final String inputString) {

        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be greater than 0");
        }
        if (inputString == null) {
            throw new IllegalArgumentException("input String must not be null");
        }


        // Initialize the Left-moving-array and Right-moving-array
        // Leave space in the two arrays for the possibility of *all* of them being left moving, or right moving
        final int inputStringLength = inputString.length();
        final int[] leftMovingArray = new int[inputStringLength];
        int activeLeftMovingParticles = 0;
        final int[] rightMovingArray = new int[inputStringLength];
        int activeRightMovingParticles = 0;
        for (int i = 0; i < inputStringLength; i++) {
            final byte foundByte = (byte) inputString.charAt(i);
            switch (foundByte) {
                case ASCII_L_INDICATOR:
                    leftMovingArray[activeLeftMovingParticles++] = i;
                    break;
                case ASCII_R_INDICATOR:
                    rightMovingArray[activeRightMovingParticles++] = i;
            }
        }


        // Prepare for the main loop
        int skipableLeftMovingParticles = 0;  // no skipable left particles, at start
        int skipableRightMovingParticles = 0; // no skipable right particles, at start
        final List<String> resultsAtEachSnapShot = new ArrayList<>();
        // We'll re-use this same snapshot array each time - we'll just reset it
        final byte[] currentParticleLocationSnapshot = new byte[inputStringLength];


        // While there are still particles in the chamber, record their position and then move them
        while (skipableLeftMovingParticles < activeLeftMovingParticles || skipableRightMovingParticles < activeRightMovingParticles) {

            /*
                Inside the loop, we'll do two things:
                    1. Record the current position of the particles, to be reported out
                    2. Move the particles
                We'll do these two things first for the left particles, and then for the right particles

                We've optimized the initialization and termination expressions of the for-loop
                so that we examine particles still in the chamber
            */


            // Initialize this current snapshot to be all periods; we'll overwrite certain spots within the loops, as appropriate
            Arrays.fill(currentParticleLocationSnapshot, ASCII_PERIOD);

            // LEFT Particles
            // Record a snapshot of the left-movers still in the chamber
            // Then shift them
            final boolean processLeftMoving = skipableLeftMovingParticles < activeLeftMovingParticles;
            if (processLeftMoving) {
                for (int i = skipableLeftMovingParticles; i < activeLeftMovingParticles; i++) {

                    // Record the location for the snapshot
                    final int particleLocation = leftMovingArray[i];
                    currentParticleLocationSnapshot[particleLocation] = ASCII_X_INDICATOR;

                    // Shift the  particle, to the left (subtract speed value)
                    // If it has left the chamber, mark it as such, and increment our 'skipped' counter
                    int newPosition = particleLocation - speed;
                    if (newPosition < 0) {
                        newPosition = OUT_OF_CHAMBER;
                        skipableLeftMovingParticles++;
                    }
                    leftMovingArray[i] = newPosition;
                }
            }

            // RIGHT Particles
            // Record a snapshot of the right-movers still in the chamber
            // Then shift them
            final boolean processRightMoving = skipableRightMovingParticles < activeRightMovingParticles;
            if (processRightMoving) {
                final int numLeftToProcess = activeRightMovingParticles - skipableRightMovingParticles;
                for (int i = 0; i < numLeftToProcess; i++) {

                    // Record the location for the snapshot
                    final int particleLocation = rightMovingArray[i];
                    currentParticleLocationSnapshot[particleLocation] = ASCII_X_INDICATOR;

                    // Shift the particle, to the right (add speed value)
                    // If it has left the chamber, mark it as such, and increment our 'skipped' counter
                    int newPosition = particleLocation + speed;
                    if (newPosition >= inputStringLength) {
                        newPosition = OUT_OF_CHAMBER;
                        skipableRightMovingParticles++;
                    }
                    rightMovingArray[i] = newPosition;
                }
            }

            resultsAtEachSnapShot.add(new String(currentParticleLocationSnapshot));
        }

        // At the very end here, Add a a final "all done" snapshot
        Arrays.fill(currentParticleLocationSnapshot, ASCII_PERIOD);
        resultsAtEachSnapShot.add(new String(currentParticleLocationSnapshot));


        // Return the results
        return resultsAtEachSnapShot.toArray(new String[resultsAtEachSnapShot.size()]);
    }


    private static void renderMessage(final String msg) {
        // swap out for log4j, or some such logging mechanism....
        System.out.println(msg);
    }


}
