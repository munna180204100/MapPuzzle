package com.example.mappuzzledemo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.example.mappuzzledemo.GestureDetectGridView;
import com.example.mappuzzledemo.customAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class PuzzlePage extends AppCompatActivity {
    private static final int column = 3;
    private static final int dimension = column * column;
    public static int moves=0; //140  144 no line
    public static boolean gameOver=false;

    private static String[] tileList;
    private static GestureDetectGridView mGridView;
    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_page);

        init();
        scramble();
        setDimension();
    }




    private void setDimension ()
    {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusBarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusBarHeight;

                mColumnWidth = displayWidth / column;
                mColumnHeight = requiredHeight / column;

                display(getApplicationContext());
            }
        });
    }

    private int getStatusBarHeight (Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) result = context.getResources().getDimensionPixelSize(resourceId);
        return result;
    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for(int i=0; i< tileList.length; i++)
        {
            button = new Button(context);

            if (tileList[i].equals("0"))
                button.setBackgroundResource(R.drawable.img1);
            else if (tileList[i].equals("1"))
                button.setBackgroundResource(R.drawable.img2);
            else if (tileList[i].equals("2"))
                button.setBackgroundResource(R.drawable.img3);
            else if (tileList[i].equals("3"))
                button.setBackgroundResource(R.drawable.img4);
            else if (tileList[i].equals("4"))
                button.setBackgroundResource(R.drawable.img5);
            else if (tileList[i].equals("5"))
                button.setBackgroundResource(R.drawable.img6);
            else if (tileList[i].equals("6"))
                button.setBackgroundResource(R.drawable.img7);
            else if (tileList[i].equals("7"))
                button.setBackgroundResource(R.drawable.img8);
            else if (tileList[i].equals("8"))
                button.setBackgroundResource(R.drawable.img9);

            buttons.add(button);
        }

        mGridView.setAdapter(new customAdapter(buttons, mColumnWidth, mColumnHeight));
    }


    private void scramble ()
    {
        int index;
        String temp;
        Random random = new Random();

        for(int i = tileList.length - 1; i > 0; i--)
        {
            index = random.nextInt(i+1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void init() {
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(column);
        tileList = new String [dimension];
        for(int i = 0; i < dimension; i++)
        {
            tileList [i] = String.valueOf(i);
        }
    }

    private static void swap (Context context, int position, int swap)
    {
        String newPosition = tileList [position + swap];
        tileList [position + swap] = tileList [position];
        tileList [position] = newPosition;
        display(context);
        moves++;

        if (isSolved())
        {
            Toast.makeText(context, "You Win\nTotal move:"+ moves, Toast.LENGTH_SHORT).show();
            gameOver=true;


        }
    }

    private static boolean isSolved ()
    {
        boolean solved = false;

        for (int i=0; i<tileList.length; i++)
        {
            if (tileList[i].equals(String.valueOf(i))) solved = true;
            else
            {
                solved = false;
                break;
            }
        }
        return solved;
    }

    public static void moveTiles (Context context, String direction, int position)
    {
        //upper-left-corner-tile
        if (position == 0)
        {
            if(direction.equals(RIGHT))
            {
                swap(context, position,1);
            }
            else if (direction.equals(DOWN))
            {
                swap(context, position, column);
            }
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
        }
        //upper-center-tiles
        else if (position > 0 && position < column-1)
        {
            if(direction.equals(LEFT)) swap (context,position,-1);
            else if(direction.equals(DOWN)) swap (context,position, column);
            else if(direction.equals(RIGHT)) swap (context,position, 1);
            else Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
        }

        else if (position == column - 1) {
            if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, column);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > column - 1 && position < dimension - column &&
                position % column == 0) {
            if (direction.equals(UP)) swap(context, position, -column);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(DOWN)) swap(context, position, column);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == column * 2 - 1 || position == column * 3 - 1) {
            if (direction.equals(UP)) swap(context, position, -column);
            else if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= dimension - column - 1) swap(context, position, column);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == dimension - column) {
            if (direction.equals(UP)) swap(context, position, -column);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < dimension - 1 && position > dimension - column) {
            if (direction.equals(UP)) swap(context, position, -column);
            else if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(UP)) swap(context, position, -column);
            else if (direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else swap(context, position, column);
        }
    }





    // backpress and exit
    public void onBackPressed() {
        if (gameOver) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PuzzlePage.this);
            builder.setTitle("Bravo!! You Have Done A Great Job Only In " + moves + " Moves")
                    .setMessage("Go Back To The HomePage??")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PuzzlePage.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false);
            AlertDialog alert = builder.create();
            moves=0;
            gameOver=false;
            alert.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(PuzzlePage.this);
            builder.setTitle("You Are not Done Yet??")
                    .setMessage("Are you Sure You Want to Quit??")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PuzzlePage.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No,Continue", null)
                    .setCancelable(false);
            AlertDialog alert = builder.create();
            moves=0;
            gameOver=false;
            alert.show();
        }
    }
}
