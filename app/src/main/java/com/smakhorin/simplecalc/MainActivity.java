package com.smakhorin.simplecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smakhorin.simplecalc.ViewModel.CalculatorViewModel;

public class MainActivity extends AppCompatActivity {

    //Displays
    private TextView mOperations;
    private EditText mResult;
    Button mEquals;
    //Extra
    String display, history;
    CalculatorViewModel cvm = CalculatorViewModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOperations = (TextView)findViewById(R.id.tv_operations);
        mResult = (EditText) findViewById(R.id.et_result);
        mEquals = (Button)findViewById(R.id.btn_equals);
        mEquals.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                cvm.Equal();
                updateDisplay();
            }

            @Override
            public void onDoubleClick(View v) {
                if(!cvm.isRestored()) {
                    cvm.restore();
                    history = cvm.getLastHistory();
                    mOperations.setText(history);
                    display = cvm.getFormattedDisplay();
                    mResult.setText(display);
                    cvm.clearHistory();
                }
            }
        });
    }

    private void updateDisplay() {
        history = cvm.getHistory();
        mOperations.setText(history);
        display = cvm.getFormattedDisplay();
        mResult.setText(display);
    }

    public void onAllClear(View view) {
        cvm.AllClear();
        updateDisplay();
    }

    public void onChangeSign(View view) {
        cvm.Sign();
        updateDisplay();
    }

    public void onPercent(View view) {
        cvm.Percent();
        updateDisplay();
    }

    public void onNumber0(View view) {
        cvm.Number0();
        updateDisplay();
    }

    public void onNumber1(View view) {
        cvm.Number1();
        updateDisplay();
    }

    public void onNumber2(View view) {
        cvm.Number2();
        updateDisplay();
    }

    public void onNumber3(View view) {
        cvm.Number3();
        updateDisplay();
    }

    public void onNumber4(View view) {
        cvm.Number4();
        updateDisplay();
    }

    public void onNumber5(View view) {
        cvm.Number5();
        updateDisplay();
    }

    public void onNumber6(View view) {
        cvm.Number6();
        updateDisplay();
    }

    public void onNumber7(View view) {
        cvm.Number7();
        updateDisplay();
    }

    public void onNumber8(View view) {
        cvm.Number8();
        updateDisplay();
    }

    public void onNumber9(View view) {
        cvm.Number9();
        updateDisplay();
    }

    public void onDot(View view) {
        cvm.Dot();
        updateDisplay();
    }

    public void onEquals(View view) {
        cvm.Equal();
        updateDisplay();
    }

    public void onPlus(View view) {
        cvm.Plus();
        updateDisplay();
    }

    public void onMinus(View view) {
        cvm.Minus();
        updateDisplay();
    }

    public void onMultiply(View view) {
        cvm.Multiply();
        updateDisplay();
    }

    public void onDivide(View view) {
        cvm.Divide();
        updateDisplay();
    }

    @Override
    protected void onDestroy() {
        cvm.clearHistory();
        super.onDestroy();
    }
}
