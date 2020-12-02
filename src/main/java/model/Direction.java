package model;

public enum Direction {
    LEFT(1), UP(2), RIGHT(3),  DOWN(4);

        private int numVal;

    Direction(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }
    }
