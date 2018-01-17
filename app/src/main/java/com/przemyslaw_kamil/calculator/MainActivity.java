package com.przemyslaw_kamil.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;
    private final String STATE_PENDING_OPERATION = "operation";
    private final String STATE_OPERAND1 = "operand";

    //Variables to hold the operands and type of calculations
    private Double operand = null;
    private String pendingOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);
        Button buttonC = (Button) findViewById(R.id.buttonC);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                if (b.getText().toString().equals("0")) {
                    if (newNumber.getText().toString().length() < 3) {
                        String nmb = newNumber.getText().toString();
                        if (nmb.startsWith("0") && nmb.length() == 1) return;
                        if (nmb.startsWith("-") && (nmb.length() == 2))
                            if (nmb.charAt(1) == '0') return;
                    }
                }
                newNumber.append(b.getText().toString());

            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newNumber.getText().toString().isEmpty()) {
                    if (!newNumber.getText().toString().startsWith("-")) {
                        newNumber.setText(("-" + newNumber.getText().toString()));
                    } else {
                        newNumber.setText(newNumber.getText().toString().subSequence(1, newNumber.getText().toString().length()));
                    }

                } else if (operand != null) {
                    operand = -operand;
                    result.setText(operand.toString());
                } else {
                    newNumber.setText("-");
                }


            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newNumber.getText().toString().isEmpty()) {
                    newNumber.setText("");
                } else {
                    operand = null;
                    result.setText("");
                }
            }
        });


    }

    private void performOperation(Double value, String operation) {
        if (null == operand) {
            operand = value;
        } else {
//            if (pendingOperation.equals("=")) {
////                pendingOperation = operation;
//            }
            switch (pendingOperation) {
                case "=":
                    operand = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand = 0.0;
                    } else {
                        operand /= value;
                    }
                    break;
                case "*":
                    operand *= value;
                    break;
                case "-":
                    operand -= value;
                    break;
                case "+":
                    operand += value;
            }

        }
        result.setText(operand.toString());
        newNumber.setText("");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand != null) {
            outState.putDouble(STATE_OPERAND1, operand);
        }
        super.onSaveInstanceState(outState);

    }
}


























