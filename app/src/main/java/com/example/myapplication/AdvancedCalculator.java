package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdvancedCalculator extends AppCompatActivity {

    private TextView results;
    private TextView calculationText;
    private double first_num;
    private double second_num;
    private char lastOperation;
    private boolean operationBegun;
    private boolean equalSuppVariable;

    private boolean clearClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_calculator);
        results = findViewById(R.id.adinput);
        calculationText = findViewById(R.id.CalculationText);
        calculationText.setGravity(Gravity.END);
        lastOperation = '0';
        operationBegun = false;
        equalSuppVariable = false;


        if(savedInstanceState != null) { // if the bundle is not null, then restore the saved value
            String savedNumber = savedInstanceState.getString("number");
            results.setText(savedNumber);
            String savedCalcText = savedInstanceState.getString("calcT");
            calculationText.setText(savedCalcText);

            this.first_num = savedInstanceState.getDouble("firstN");
            this.second_num = savedInstanceState.getDouble("secondN");
            this.lastOperation = savedInstanceState.getChar("lastO");
            this.operationBegun = savedInstanceState.getBoolean("operationB");
            this.equalSuppVariable = savedInstanceState.getBoolean("equalSV");
        }
    }




    private double calculate(char sign,double number)
    {
        switch(sign){
            case '^':
                return Math.pow(first_num,number);
            case '+':
                return first_num + number;
            case'-':
                return first_num - number;
            case '*':
                return first_num * number;
            case '/':
                if(number!=0) {
                    return first_num / number;
                    //todo, w sumie można dodać jakieś divide exception
                }
        }
        Toast.makeText(getApplicationContext(),"Never ever divide by zero", Toast.LENGTH_SHORT).show();
        return first_num;
    }
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void writeText(String text)
    {
        clearClicked = false;
        String oldText = results.getText().toString();
        if(operationBegun) {
            oldText = "";
            operationBegun = false;
        }
        if(oldText.length()>25) {
            Toast.makeText(getApplicationContext(),"Number is too long", Toast.LENGTH_SHORT).show();
            return;
        }
        String newText = String.format("%s%s",oldText,text);
        if(newText.startsWith("0")&& newText.length()>1 && !newText.startsWith("0."))
        {
            newText= newText.substring(1);
        }
        try {
            double d = Double.parseDouble(newText);
            if(d ==0 && newText.length()>2 && !newText.startsWith("0.")) {
                System.out.println("H1");
                return;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("H2");
            return;
        }

        results.setText(newText);
    }

    public void zeroButton(View view){
        writeText("0");
    }
    public void oneButton(View view){
        writeText("1");
    }
    public void twoButton(View view){
        writeText("2");
    }
    public void threeButton(View view){
        writeText("3");
    }
    public void fourButton(View view){
        writeText("4");
    }
    public void fiveButton(View view){
        writeText("5");
    }
    public void sixButton(View view){writeText("6");}
    public void sevenButton(View view){
        writeText("7");
    }
    public void eightButton(View view){
        writeText("8");
    }
    public void nineButton(View view){
        writeText("9");
    }
    public void dotButton(View view){
        writeText(".");
    }

    public void addButton(View view){

        makeCalculation('+');
    }

    public void minusButton(View view){

        makeCalculation('-');
    }
    public void multiplyButton(View view){

        makeCalculation('*');
    }
    public void divideButton(View view){

        makeCalculation('/');
    }


    private void makeCalculation(char operation) {
        equalSuppVariable = false;
        clearClicked = false;
        if(operationBegun) return;

        if(lastOperation=='0')
        {
            String displayedText = results.getText().toString();
            first_num = Double.parseDouble(displayedText);
            setCalculationText(displayedText, operation);
            return;
        }
        if(lastOperation!= operation)
        {
            String displayedText = Double.toString(first_num);
            setCalculationText(displayedText, operation);
            return;
        }
        String actualNum = results.getText().toString();
        double ourNewNum = Double.parseDouble(actualNum);
        //^To się powtarza ale nie mam pomysły, żeby to jakoś ładnie zrefaktorować
        double result = calculate(lastOperation,ourNewNum);
        first_num = result;
        String displayedText = Double.toString(result);
        results.setText(displayedText);

        setCalculationText(displayedText, operation);
    }

    private void setCalculationText(String displayedText, char operation) {
        lastOperation =operation;
        String textToDisplay = displayedText +lastOperation; //todo, zdecydować, czy nie chcę pozbyć się zer po kropce
        calculationText.setText(textToDisplay);
        operationBegun = true;
    }

    public void delButton(View view){
        equalSuppVariable =false;
        lastOperation ='0';
        String text = results.getText().toString();
        if(text.length()<1) return;

        String newtext = text.substring(0,text.length()-1);
        if(newtext.length() ==0)
        {
            newtext ="0";
        }
        results.setText(newtext);
    }
    public void equalButton(View view){
        if(lastOperation =='0')
        {
            String num = results.getText().toString();
            first_num = Double.parseDouble(num);
            String cal = num+'=';
            calculationText.setText(cal);
            return;
        }
        if(!equalSuppVariable)
        {
            String actualNum = results.getText().toString();
            second_num = Double.parseDouble(actualNum);
            equalSuppVariable = true;
        }

        double result = calculate(lastOperation,second_num);

        String calculations = Double.toString(first_num) + lastOperation + second_num;
        calculationText.setText(calculations);

        first_num = result;

        String resultString = Double.toString(result);
        results.setText(resultString);
    }
    public void clearButton(View view){
        results.setText("0");
        if(clearClicked)
        {
            calculationText.setText("");
            first_num = second_num = 0;
            lastOperation ='0';
            return;
        }
        clearClicked = true;
    }
    public void allClearButton(View view){
        results.setText("0");
        calculationText.setText("");
        first_num = second_num = 0;
        lastOperation ='0';
    }
    public void plusMinusButton(View view)
    {
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        numberToChange *=-1;
        numberText = Double.toString(numberToChange);
        results.setText(numberText);

        first_num *=-1;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String number = results.getText().toString(); // get the current value of the EditText
        String calcT = calculationText.getText().toString();
        outState.putString("number", number); // save the current value of the EditText in the Bundle
        outState.putString("calcT",calcT);
        outState.putDouble("firstN",this.first_num);
        outState.putDouble("secondN",this.second_num);
        outState.putChar("lastO",this.lastOperation);
        outState.putBoolean("operationB",this.operationBegun);
        outState.putBoolean("equalSV",this.equalSuppVariable);
    }

//Advanced Calculator Func
//todo, nie wiem, czy nie ma jakiegoś małego buga, przetestuj potem, jak coś to dodaj lastOperation ='0' przy jednoargumentowych operacjach
    public void percentButton(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        numberToChange /=100;
        numberText = Double.toString(numberToChange);
        results.setText(numberText);

        first_num =numberToChange;

    }

    public void sinButton(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        numberToChange = Math.sin(numberToChange);
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = "sin(" + numberText +")=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }

    public void cosButton(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        numberToChange = Math.cos(numberToChange);
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = "cos(" + numberText +")=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }
    public void tanButton(View view)
    {

        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        if(numberToChange ==Math.PI/2)
        {
            calculationText.setText("Not defined");
            Toast.makeText(getApplicationContext(),"U know, something's wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        numberToChange = Math.tan(numberToChange);
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = "tg(" + numberText +")=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }
    public void ctanButton(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        if(numberToChange ==0)
        {
            calculationText.setText("Not defined");
            Toast.makeText(getApplicationContext(),"Change number from zero lol", Toast.LENGTH_SHORT).show();
            return;
        }
        numberToChange = 1/Math.tan(numberToChange);
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = "ctg(" + numberText +")=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }
    public void logButton(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        if(numberToChange <=0)
        {
            calculationText.setText("Not defined");
            Toast.makeText(getApplicationContext(),"Can't get log from this number moron", Toast.LENGTH_SHORT).show();
            return;
        }
        numberToChange = Math.log10(numberToChange);
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = "log(" + numberText +")=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }

    public void lnButton(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        if(numberToChange <=0)
        {
            calculationText.setText("Not defined");
            Toast.makeText(getApplicationContext(),"Wtf Are u doin'", Toast.LENGTH_SHORT).show();
            return;
        }
        numberToChange = Math.log(numberToChange);
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = "ln(" + numberText +")=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }

    public void sqrtButton(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);
        if(numberToChange <0)
        {
            calculationText.setText("Not defined");
            Toast.makeText(getApplicationContext(),"Liczba FUUUU", Toast.LENGTH_SHORT).show();
            return;
        }
        numberToChange = Math.sqrt(numberToChange);
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = "√(" + numberText +")=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }


    public void xPow2Button(View view)
    {
        lastOperation='0';
        String numberText = results.getText().toString();
        double numberToChange = Double.parseDouble(numberText);

        numberToChange = numberToChange * numberToChange;
        String resultText = Double.toString(numberToChange);
        results.setText(resultText);
        String calculateText = numberToChange + "^2=";
        calculationText.setText(calculateText);

        first_num =numberToChange;

    }
    public void xPowYButton(View view)
    {

        makeCalculation('^');

    }


}