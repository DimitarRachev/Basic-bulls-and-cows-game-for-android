package com.example.BullsAndCows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int number;
    int winNumber;
    EditText numberInput;
    Random random = new Random();
    Button button;

    ArrayDeque<String> queue = new ArrayDeque<>();
    TextView guess1Field;
    TextView guess2Field;
    TextView guess3Field;
    TextView guess4Field;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        winNumber = setWinNumber(random);

        guess1Field = (TextView) findViewById(R.id.guess1);
        guess2Field = (TextView) findViewById(R.id.guess2);
        guess3Field = (TextView) findViewById(R.id.guess3);
        guess4Field = (TextView) findViewById(R.id.guess4);

        numberInput = (EditText) findViewById(R.id.numberInput);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = Integer.parseInt(numberInput.getText().toString());
                String text = "";
                numberInput.setText("");
                if (number < 1000 || number >= 10000) {
                    text ="Invalid number";
                    queue.offer("Invalid number");
                } else {
                    int bulls = calculateBulls(number, winNumber);
                    int cows = calculateCows(number, winNumber);

                    if (bulls == 4) {
                        text = "Correct! Let's play again!";
                        queue.offer("Correct! Let's play again!");
                        winNumber = random.nextInt();
                    } else {
                        text = String.format("%d Bull(s) и %d Cow(s)", bulls, cows);
                        queue.offer(""+ number + "   "+ text);
                    }
                }
                showToast(text);
                drawQueue(queue);
            }
        });
    }

    private void drawQueue(ArrayDeque<String> queue) {
        guess4Field.setText(guess3Field.getText());
        guess3Field.setText(guess2Field.getText());
        guess2Field.setText(guess1Field.getText());
        guess1Field.setText(queue.poll());
    }

    private int calculateCows(int number, int winNumber) {
        int counter = 0;
        int n1 = number % 10;
        number /= 10;
        int n2 = number % 10;
        number /= 10;
        int n3 = number % 10;
        int n4 = number /= 10;
        int w1 = winNumber % 10;
        winNumber /= 10;
        int w2 = winNumber % 10;
        winNumber /= 10;
        int w3 = winNumber % 10;
        int w4 = winNumber / 10;

        if (n1 == w2 || n1 == w3 || n1 == w4) {
            counter++;
        }
        if (n2 == w1 || n2 == w3 || n2 == w4) {
            counter++;
        }
        if (n3 == w1 || n3 == w2 || n3 == w4) {
            counter++;
        }
        if (n4 == w1 || n4 == w2 || n4 == w3) {
            counter++;
        }
        return counter;
    }

    private int calculateBulls(int number, int winNumber) {
        String num = String.valueOf(number);
        String winNum = String.valueOf(winNumber);
        int counter = 0;
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) == winNum.charAt(i)) {
                counter++;
            }
        }
        return counter;
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();

    }

    private int setWinNumber(Random random) {
        int num = random.nextInt(9000) + 1000;
        while (digitsAreEqual(num)) {
            num = random.nextInt(9000) + 1000;
        }
        return num;
    }

    private boolean digitsAreEqual(int num) {
        int n1 = num % 10;
        num /= 10;
        int n2 = num % 10;
        num /= 10;
        int n3 = num % 10;
        int n4 = num * 10;
        return n1 == n2 || n1 == n3 || n1 == n4 || n2 == n3 || n2 == n4 || n3 == n4;
    }
}