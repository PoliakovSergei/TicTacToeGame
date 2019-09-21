package com.sergo.tictactoe.enums;

public enum CellState {
    NOUGHT {
        @Override
        public String toString() {
            return "Noughts";
        }
    },
    CROSS {
        @Override
        public String toString() {
            return "Crosses";
        }
    },
    EMPTY {
        @Override
        public String toString() {
            return "Empty";
        }
    }
}