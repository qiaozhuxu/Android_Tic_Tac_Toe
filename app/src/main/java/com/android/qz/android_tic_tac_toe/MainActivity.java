package com.android.qz.android_tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // 0 is green and 1 is red
    int activePlayer = 0;
    boolean gameIsActive = true;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int count = 0;
    String winner = "draw";


    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int status = Integer.parseInt(counter.getTag().toString());

        if (gameState[status] == 2 && gameIsActive) {
            gameState[status] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.green);
                counter.animate().translationYBy(1000f).setDuration(300);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                counter.animate().translationYBy(1000f).setDuration(300);
                activePlayer = 0;
            }
        }
        count++;
        if(shouldStopGame(count)) {
            //print out the winner and show the gridLayout
            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
            if("draw".equals(winner)){
                winnerMessage.setText("It is a draw! ");
            } else {
                winnerMessage.setText("Winner is " + winner);
            }
            LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
            playAgainLayout.setVisibility(View.VISIBLE);
        }
    }
    public boolean shouldStopGame(int count) {
        //check if someone wins
        for (int[] winningposition : winningPositions) {
            //someone has won, pass the winner name and game status to outer layer
            if (gameState[winningposition[0]] == gameState[winningposition[1]]
                    && gameState[winningposition[1]] == gameState[winningposition[2]]
                    && gameState[winningposition[0]] != 2) {
                gameIsActive = false;

                if (gameState[winningposition[0]] == 0) {
                    winner = "Green";
                } else {
                    winner = "Red";
                }

                return true;
            }
        }
        //check if it is a draw
        if (count >= 9) {
            return true;
        }
        //no one wins and it is not draw
        return false;
    }


    public void playAgain(View view) {
        Log.i("playAgain ", "is clicked");
        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);

        count = 0;
        winner = "draw";
        activePlayer = 0;
        gameIsActive = true;

        for(int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
