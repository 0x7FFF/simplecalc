package com.smakhorin.simplecalc.ViewModel;

public abstract class Operator {
        private final int mLevel;
        private final String mSymbol;
        private final boolean mUnary;
        public Operator(int level, String symbol, boolean unary){
            mLevel = level;
            mSymbol = symbol;
            mUnary = unary;
        }
        public boolean isUnary(){
            return mUnary;
        }
        public int getLevel(){
            return mLevel;
        }
        public String getSymbol(){
            return mSymbol;
        }

        public abstract double calculate(double operandA, double operandB);
}
