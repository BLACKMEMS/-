package kz.example.tictactoe;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private final TicTacToeGame game = new TicTacToeGame();
    private final Button[] cells = new Button[9];

    private TextView tvStatus;
    private TextView tvScore;

    private int scoreX = 0;
    private int scoreO = 0;
    private int draws = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        tvScore = findViewById(R.id.tvScore);
        GridLayout grid = findViewById(R.id.grid);

        // 3x3 тақтаның батырмаларын кодпен құру
        for (int i = 0; i < 9; i++) {
            Button cell = new Button(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(i % 3, 1f);
            params.rowSpec = GridLayout.spec(i / 3, 1f);
            params.setMargins(6, 6, 6, 6);
            cell.setLayoutParams(params);
            cell.setTextSize(40);
            cell.setGravity(Gravity.CENTER);
            cell.setBackgroundColor(ContextCompat.getColor(this, R.color.cell_bg));

            final int index = i;
            cell.setOnClickListener(v -> onCellClicked(index));

            cells[i] = cell;
            grid.addView(cell);
        }

        Button btnNewGame = findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(v -> newGame());

        Button btnResetScore = findViewById(R.id.btnResetScore);
        btnResetScore.setOnClickListener(v -> {
            scoreX = scoreO = draws = 0;
            newGame();
        });

        newGame();
    }

    private void onCellClicked(int index) {
        if (!game.makeMove(index)) return;

        char symbol = game.getCell(index);
        cells[index].setText(String.valueOf(symbol));
        cells[index].setTextColor(ContextCompat.getColor(this,
                symbol == TicTacToeGame.X ? R.color.x_color : R.color.o_color));

        if (game.getWinner() != TicTacToeGame.EMPTY) {
            String w = String.valueOf(game.getWinner());
            if (game.getWinner() == TicTacToeGame.X) scoreX++; else scoreO++;
            tvStatus.setText(getString(R.string.win, w));
            for (int i : game.getWinningLine()) {
                cells[i].setBackgroundColor(ContextCompat.getColor(this, R.color.win_bg));
            }
            Toast.makeText(this, getString(R.string.win, w), Toast.LENGTH_SHORT).show();
        } else if (game.isDraw()) {
            draws++;
            tvStatus.setText(R.string.draw);
            Toast.makeText(this, R.string.draw, Toast.LENGTH_SHORT).show();
        } else {
            tvStatus.setText(getString(R.string.turn, String.valueOf(game.getCurrentPlayer())));
        }
        updateScore();
    }

    private void newGame() {
        game.reset();
        for (Button cell : cells) {
            cell.setText("");
            cell.setBackgroundColor(ContextCompat.getColor(this, R.color.cell_bg));
        }
        tvStatus.setText(getString(R.string.turn, String.valueOf(game.getCurrentPlayer())));
        updateScore();
    }

    private void updateScore() {
        tvScore.setText(getString(R.string.score, scoreX, scoreO, draws));
    }
}
