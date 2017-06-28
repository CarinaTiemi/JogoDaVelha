package br.com.carinatiemiyoshida.playingwithbuttons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //0 = white button ---- 1 = black button
    int activePlayer = 0;
    boolean isactive = true;
    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessageTextView);
        LinearLayout layout = (LinearLayout) findViewById(R.id.playlinearLayout);
        GridLayout gLayout = (GridLayout) findViewById(R.id.playGridLayout);
        ImageView counter = (ImageView) view;
        String winner = "";
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && isactive) {
            gameState [tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.pinkbutton);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.purplebutton);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            for (int[] winning : winningPositions) {
                if (gameState[winning[0]] == gameState[winning[1]] &&
                        gameState[winning[1]] == gameState[winning[2]] &&
                        gameState[winning[0]] != 2){
                    isactive = false;
                    winner = "Time Roxo";
                    if (gameState[winning[0]] == 0){
                        winner = "Time Rosa";
                    }
                    winnerMessage.setText(winner + " ganhou!!");
                    layout.setVisibility(View.VISIBLE);
                    gLayout.setAlpha(0.3f);
                }else{
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) gameIsOver = false;
                    }

                    if (gameIsOver){
                        winnerMessage.setText("Ninguém ganhou!!");
                        layout.setVisibility(View.VISIBLE);
                        gLayout.setAlpha(0.3f);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        isactive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.playlinearLayout);
        layout.setVisibility(View.INVISIBLE);
        GridLayout gLayout = (GridLayout) findViewById(R.id.playGridLayout);
        gLayout.setAlpha(1f);
        for (int i = 0; i < gLayout.getChildCount(); i++) {
            ((ImageView) gLayout.getChildAt(i)).setImageResource(0);
        }
        activePlayer = 0;
        // 2 means unplayed
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
    }
}
