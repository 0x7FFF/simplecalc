package com.smakhorin.simplecalc.ViewModel;


import java.text.DecimalFormat;
import java.util.Stack;

public class CalculatorViewModel {
    private static CalculatorViewModel instance = null;
    public static CalculatorViewModel getInstance() {
        if(instance == null) {
            instance = new CalculatorViewModel();
        }
        return instance;
    }

    private String current = "0";
    public String getCurrent() {
        return current;
    }

    private String history = "";
    public String getHistory() {
        return history;
    }

    private double mDisplay;
    public double getDisplay(){
        return mDisplay;
    }

    private void setDisplay(double value){
        mDisplay = value;
    }

    public String getFormattedDisplay(){
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("#.######");
        String output = format.format(mDisplay);
        if (output.length() <= MAXLENGTH) {
            return output;
        }
        format.applyPattern("0.########E00");
        return format.format(mDisplay);
    }

    public void Number9(){
        addNumber(9);
    }
    public void Number8(){
        addNumber(8);
    }
    public void Number7(){
        addNumber(7);
    }
    public void Number6(){
        addNumber(6);
    }
    public void Number5(){
        addNumber(5);
    }
    public void Number4(){
        addNumber(4);
    }
    public void Number3(){
        addNumber(3);
    }
    public void Number2(){
        addNumber(2);
    }
    public void Number1(){
        addNumber(1);
    }
    public void Number0(){
        addNumber(0);
    }
    public void Dot(){
        putDot();
    }

    public void Plus(){
        operate(plusOperator);
    }
    public void Minus(){
        operate(minusOperator);
    }
    public void Multiply(){
        operate(multiplyOperator);
    }
    public void Divide(){
        operate(divideOperator);
    }
    public void Equal(){
        operate(equalOperator);
    }
    public void AllClear(){
        onAllClear();
    }
    public void Sign() {
        operate(signOperator);
    }
    public void Percent() {
        operate(percentOperator);
    }

    private final Operator percentOperator = new Operator(1,"%",false) {
        @Override
        public double calculate(double operandA, double operandB) {
            return (operandA * 100)/operandB;
        }
    };
    private final Operator plusOperator = new Operator(1, "+", false){
        @Override
        public double calculate(double operandA, double operandB) {
            return operandA + operandB;
        }
    };
    private final Operator minusOperator = new Operator(1, "-", false){
        @Override
        public double calculate(double operandA, double operandB) {
            return operandA - operandB;
        }
    };
    private final Operator multiplyOperator = new Operator(2, "x", false){
        @Override
        public double calculate(double operandA, double operandB) {
            return operandA * operandB;
        }
    };
    private final Operator divideOperator = new Operator(2, "/", false){
        @Override
        public double calculate(double operandA, double operandB) {
            return operandA / operandB;
        }
    };
    private final Operator signOperator = new Operator(3,"+/-",true) {
        @Override
        public double calculate(double operandA, double operandB) {
            return -operandA;
        }
    };
    private final Operator equalOperator = new Operator(0, "=", false){
        @Override
        public double calculate(double operandA, double operandB) {
            return operandA;
        }
    };

    private Stack<Double> operands = new Stack<Double>();
    private Stack<Operator> operators = new Stack<Operator>();

    private final int MAXLENGTH = 10;

    private boolean lastCommandIsOperator = false;
    private boolean lastOperatorIsUnary = false;

    //not using pair
    private String lastHistory = "";
    private String lastResult = "";


    public String getLastHistory() {
        return lastHistory;
    }
    public String getLastResult() {
        return lastResult;
    }

    //restored can be set only with double tapping '=' and single tapping AC
    private boolean restored = false;

    public boolean isRestored() {
        return restored;
    }
    public void restore() {
        if(!restored) {
            current = lastResult;
            lastCommandIsOperator = false; // drop the flag
            //prevents crashing
            if(current.equals("∞") || current.equals("NaN")) {
                current = "0";
            }
            setDisplay(Double.parseDouble(current));
            restored = true;
        }
    }

    public void clearHistory() {
        lastHistory = "";
        lastResult = "";
    }

    private void onAllClear(){
        lastResult = getFormattedDisplay();
        lastHistory = history;
        restored = false;
        current = "0";
        history = "";
        operands.clear();
        operators.clear();
        setDisplay(0d);
    }

    private void addNumber(int number){
        lastCommandIsOperator = false;
        String temp = current;
        if (temp.length() >= MAXLENGTH)
            return;
        if (temp.equals("0") && (temp.indexOf(".") <0)){
            current = Integer.toString(number);
        }else{
            current = temp + number;
        }
        setDisplay(Double.parseDouble(current));
    }

    private void putDot(){
        String temp = current;
        if (temp.equals("0"))
            current = "0.";
        else{
            if (temp.indexOf(".") <0)
                current = temp + ".";
        }
        setDisplay(Double.parseDouble(current));
    }

    private void operate(Operator operator){
        if (!lastCommandIsOperator){
            // Push the current number to stack
            operands.push(Double.parseDouble(current));
            history += current;
            lastCommandIsOperator = true;
        }else{
            if (!operators.empty() && !lastOperatorIsUnary)
                operators.pop();
        }

        Operator lastOperator = null;
        while (!operators.empty()){
            lastOperator = operators.peek();
            if (lastOperator.getLevel()>=operator.getLevel()){
                // Calculate
                double result;
                if (lastOperator.isUnary()) {
                    result = lastOperator.calculate(operands.pop(), 0d);
                    lastOperatorIsUnary = false;
                }
                else{
                    double b = operands.pop();
                    double a = operands.pop();
                    result = lastOperator.calculate(a, b);
                }
                setDisplay(result);
                operands.push(result);
                operators.pop();
            }
            else{
                break;
            }
        }
        // Special case for level 0 (=)
        if (operator.getLevel()>0) {
            //to prevent popping while using +\- operation
            lastOperatorIsUnary = operator.isUnary();
            //to prevent adding +\- to history
            if(!operator.isUnary()) {
                String symbol = operator.getSymbol();
                if(symbol.equals("/")) {
                    symbol = "÷";
                }
                history += symbol;
            }
            operators.push(operator);

        }

        current = "0";
    }

}