package de.gebelcl.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button playAgainButton;
    private TextView winnerTextView;
    private GridLayout gridLayout;
    private boolean won = false;

    // 0 = player 1 clicked that field
    // 1 = player 2 clicked that field
    // 2 = unclicked field
    private int[] gameState = {
            2, 2, 2,
            2, 2, 2,
            2, 2, 2
    };

    private int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    private int activePlayer = 0;

    private boolean gameIsActive = true;

    public void onViewClicked(View view) {
        ImageView imageView = (ImageView) view;

        int clickedImageViewNumber = Integer.parseInt(imageView.getTag().toString());

        if (gameIsActive && gameState[clickedImageViewNumber] == 2) {
            gameState[clickedImageViewNumber] = activePlayer;
            if (activePlayer == 0) {
                imageView.setImageResource(R.drawable.ring);
                activePlayer = 1;
            } else {
                imageView.setImageResource(R.drawable.cross);
                activePlayer = 0;
            }
            imageView.animate().alpha(1).setDuration(1000);

            for (int[] winPosition : winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2) {
                    gameIsActive = false;
                    // deactivate clickability of imageViews
                    clickBehaviorOfImageViews(false);
                    String winner = "";
                    if (activePlayer == 1) {
                        won = true;
                        winner = "Ring";
                        activePlayer = 0; // change active player so that the winner will begin in the next game
                    } else {
                        won = true;
                        winner = "Kreuz";
                        activePlayer = 1; // change active player so that the winner will begin in the next game
                    }

                    winnerTextView.setText(winner + " hat gewonnen!");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);

                } else {
                    gameIsActive = false;
                    for (int imageViewState: gameState) {
                        if (imageViewState == 2) {
                            gameIsActive = true;
                        }
                    }

                    if (!gameIsActive) {
                        winnerTextView.setText("Unentschieden!");
                        playAgainButton.setVisibility(View.VISIBLE);
                        winnerTextView.setVisibility(View.VISIBLE);
                        // Anklickbarkeit der ImageViews deaktivieren
                        clickBehaviorOfImageViews(false);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);
        won = false;
        // Anklickbarkeit der ImageViews deaktivieren
        clickBehaviorOfImageViews(true);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        gameIsActive = true;
    }

    // if parameter is true -> imageViews are clickable
    // if parameter is false -> imageViews are not clickable
    protected void clickBehaviorOfImageViews(boolean clickable) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView iv = (ImageView) gridLayout.getChildAt(i);
            iv.setClickable(clickable);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        gridLayout = (GridLayout) findViewById((R.id.gridlayout));
    }
}