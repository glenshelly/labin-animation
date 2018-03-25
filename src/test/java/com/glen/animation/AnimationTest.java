package com.glen.animation;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


public class AnimationTest {


    private AnimateParticles animation = new AnimateParticles();

    @Test
    public void singleRMoveBy2() {

        int inputSpeed = 2;
        String inputString = "..R....";
        String[] expectedOutput = {"..X....", "....X..", "......X", "......."};
        assertArrayEquals(expectedOutput, animation.animate(inputSpeed, inputString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullString() {

        int inputSpeed = 2;
        String inputString = null;
        animation.animate(inputSpeed, inputString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badSpeed() {

        int inputSpeed = 0;
        String inputString = "..R....";
        animation.animate(inputSpeed, inputString);
    }

    @Test
    public void emptyInput() {

        int inputSpeed = 1;
        String inputString = "";
        String[] expectedOutput = {inputString};
        assertArrayEquals(expectedOutput, animation.animate(inputSpeed, inputString));
    }


    @Test
    public void noParticles() {

        int inputSpeed = 1;
        String inputString = "......";
        String[] expectedOutput = {inputString};
        assertArrayEquals(expectedOutput, animation.animate(inputSpeed, inputString));
    }


    @Test
    public void pdfExample1() {

        // Corresponds to example #1 in PDF
        // Contrary to what's stated in the PDF, the 3rd snapshot should have a period in the first (0th) position, not an X
        int inputSpeed = 3;
        String inputString = "RR..LRL";
        String[] expectedOutput = {"XX..XXX", ".X.XX..", "X.....X", "......."};
        assertArrayEquals(expectedOutput, animation.animate(inputSpeed, inputString));
    }

    @Test
    public void pdfExample2() {

        // Corresponds to example #2 in PDF
        int inputSpeed = 2;
        String inputString = "LRLR.LRLR";
        String[] expectedOutput = {"XXXX.XXXX", "X..X.X..X", ".X.X.X.X.", ".X.....X.", "........."};
        assertArrayEquals(expectedOutput, animation.animate(inputSpeed, inputString));
    }

    @Test
    public void pdfExample3() {

        // Corresponds to example #2 in PDF
        int inputSpeed = 10;
        String inputString = "RLRLRLRLRL";
        String[] expectedOutput = {"XXXXXXXXXX", ".........."};
        assertArrayEquals(expectedOutput, animation.animate(inputSpeed, inputString));
    }

    @Test
    public void pdfExample5() {

        // Corresponds to example #2 in PDF
        int inputSpeed = 1;
        String inputString = "LRRL.LR.LRR.R.LRRL.";
        String[] expectedOutput = {
                "XXXX.XX.XXX.X.XXXX.", "..XXX..X..XX.X..XX.", ".X.XX.X.X..XX.XX.XX", "X.X.XX...X.XXXXX..X",
                ".X..XXX...X..XX.X..", "X..X..XX.X.XX.XX.X.", "..X....XX..XX..XX.X", ".X.....XXXX..X..XX.",
                "X.....X..XX...X..XX", ".....X..X.XX...X..X", "....X..X...XX...X..", "...X..X.....XX...X.",
                "..X..X.......XX...X", ".X..X.........XX...", "X..X...........XX..", "..X.............XX.",
                ".X...............XX", "X.................X", "..................." };
        assertArrayEquals(expectedOutput, animation.animate(inputSpeed, inputString));
    }

}
