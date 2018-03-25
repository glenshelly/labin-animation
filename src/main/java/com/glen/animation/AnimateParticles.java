package com.glen.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Determine and report on periodic location of moving particles in a linear chamber
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
     * Given a description of a chamber with left- and right-moving particles (provided by the input string),
     *  determine the location at fixed intervals (provided by the given speed)
     *
     * @param speed the amount each particle moves, per 'tick'
     * @param init a depiction of the particle chamber at the start
     * @return a String array, with the various snapshots of the particle locations after each tick o' the clock
     */
    public String[] animate(final int speed, final String init) {

        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be greater than 0");
        }
        if (init == null) {
            throw new IllegalArgumentException("input String must not be null");
        }


        // Initialize the Left-moving-array and Right-moving-array
        // Leave space in the two arrays for the possibility of *all* of them being left moving, or right moving
        final int inputStringLength = init.length();
        final int[] leftMovingArray = new int[inputStringLength];
        int activeLeftMovingParticles = 0;
        final int[] rightMovingArray = new int[inputStringLength];
        int activeRightMovingParticles = 0;
        for (int i = 0; i < inputStringLength; i++) {
            final byte foundByte = (byte) init.charAt(i);
            switch (foundByte) {
                case ASCII_L_INDICATOR:
                    leftMovingArray[activeLeftMovingParticles++] = i;
                    break;
                case ASCII_R_INDICATOR:
                    rightMovingArray[activeRightMovingParticles++] = i;
            }
        }


        // Prepare for the main loop
        int skipableLeftMovingParticles = 0;  // no skipable-left-particles, at start
        int skipableRightMovingParticles = 0; // no skipable-right-particles, at start
        final List<String> resultsAtEachSnapShot = new ArrayList<>();
        // We'll re-use this same snapshot array each time through the loop, simply resetting the values rather than
        // creating and initializing a new object
        final byte[] currentParticleLocationSnapshot = new byte[inputStringLength];


        // Main Loop
        // While there are still particles in the chamber, record their position and then move them
        while (skipableLeftMovingParticles < activeLeftMovingParticles
                || skipableRightMovingParticles < activeRightMovingParticles) {

            /*
                Inside this loop, we do two things:

                    1. Record the current position of the particles, to be reported out
                    2. Move the particles to their next position

                We'll do these two things first for the left particles, and then for the right particles

                We've optimized the initialization and termination expressions of the for-loops below
                so that we only record and shift particles still in the chamber.

                The logic for handling left and right moving particles is very similar, but differs just enough to
                not warrant trying to consolidate the two sections into a single method.
            */


            // Initialize this current snapshot to be all periods ('.');
            // We'll subsequently overwrite certain elements of the array with 'X'
            Arrays.fill(currentParticleLocationSnapshot, ASCII_PERIOD);

            // LEFT Particles
            // Record a snapshot of the left-movers still in the chamber
            // Then shift them
            final boolean doProcessLeftMoving = skipableLeftMovingParticles < activeLeftMovingParticles;
            if (doProcessLeftMoving) {
                for (int i = skipableLeftMovingParticles; i < activeLeftMovingParticles; i++) {

                    // Record the location for the snapshot
                    final int particleLocation = leftMovingArray[i];
                    currentParticleLocationSnapshot[particleLocation] = ASCII_X_INDICATOR;

                    // Shift the  particle, to the left (by *subtracting* speed value)
                    // IF after the move the particle is no longer in the chamber,
                    // THEN mark it as such, and increment the 'skipped' counter
                    // In any event, record the new location of the particle
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
            //    (These recordings may coincide with left-moving particles, recorded above.)
            // Then shift them
            final boolean doProcessRightMoving = skipableRightMovingParticles < activeRightMovingParticles;
            if (doProcessRightMoving) {
                final int numberRemainingToProcess = activeRightMovingParticles - skipableRightMovingParticles;
                for (int i = 0; i < numberRemainingToProcess; i++) {

                    // Record the location for the snapshot
                    final int particleLocation = rightMovingArray[i];
                    currentParticleLocationSnapshot[particleLocation] = ASCII_X_INDICATOR;

                    // Shift the particle, to the right (by *adding* speed value)
                    // IF after the move the particle is no longer in the chamber,
                    // THEN mark it as such, and increment the 'skipped' counter
                    // In any event, record the new location of the particle
                    int newPosition = particleLocation + speed;
                    if (newPosition >= inputStringLength) {
                        newPosition = OUT_OF_CHAMBER;
                        skipableRightMovingParticles++;
                    }
                    rightMovingArray[i] = newPosition;
                }
            }

            // Add the new snapshot to running list of snapshots
            resultsAtEachSnapShot.add(new String(currentParticleLocationSnapshot));
        }

        // At the very end here, add a final "all done" snapshot to the running list of snapshots
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
