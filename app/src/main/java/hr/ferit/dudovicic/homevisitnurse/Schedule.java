package hr.ferit.dudovicic.homevisitnurse;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Schedule extends AppCompatActivity {

    private final AppCompatActivity activity = Schedule.this;
    private TextView textViewName, tvDate;

    private TextView nameInput, txt32;
    AlertDialog dialog, dialog32;
    private EditText editText, editText32;
    private SharedPreferences prefs;

    private GestureDetector gestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initViews();
        initObjects();

        gestureDetector = new GestureDetector(new SwipeGestureDetector());



    }

    private void initViews() {
        textViewName = (TextView) findViewById(R.id.textViewName);
        tvDate = (TextView) findViewById(R.id.DATE);

        prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);
        String name = prefs.getString("MY_NAME", "");
        String nam = prefs.getString("MY_NAM", "");


        nameInput = (TextView)findViewById(R.id.textView33);
        txt32 = (TextView)findViewById(R.id.textView32);


        //lčplččtextView = (TextView) findViewById(R.id.textView33);
        dialog = new AlertDialog.Builder(this).create();
        dialog32 = new AlertDialog.Builder(this).create();

        editText = new EditText(this);
        editText32 = new EditText(this);

        nameInput.setText(name);
        txt32.setText(nam);

        dialog.setTitle("Edit the text");
        dialog32.setTitle("Edit the text");

        dialog.setView(editText);
        dialog32.setView(editText32);

    }

    private void initObjects() {
        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMMM/d");
        String strDate = sdf.format(cal.getTime());
        tvDate.setText(strDate);



        dialog.setButton(DialogInterface.BUTTON_POSITIVE,"SAVE TEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                       /* textView.setText(editText.getText());*/
                nameInput.setText(editText.getText());

                String name = nameInput.getText().toString();


                prefs.edit().putString("MY_NAME", name ).apply();



            }});
        dialog32.setButton(DialogInterface.BUTTON_POSITIVE,"SAVE TEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                       /* textView.setText(editText.getText());*/
                txt32.setText(editText32.getText());

                String nam = txt32.getText().toString();

                prefs.edit().putString("MY_NAM", nam ).apply();



            }});
        nameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(nameInput.getText());
                dialog.show();
            }
        });
        txt32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText32.setText(txt32.getText());
                dialog32.show();
            }
        });


    }

/**/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onLeftSwipe() {
        // Do something
        Toast t = Toast.makeText(Schedule.this, "Left swipe", Toast.LENGTH_LONG);
        t.show();
        Intent go = new Intent(Schedule.this, Search.class);
        startActivity(go);
    }

    private void onRightSwipe() {
        // Do something
    }

    // Private class for gestures
    private class SwipeGestureDetector extends SimpleOnGestureListener implements GestureDetector.OnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH) {
                    return false;
                }

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Schedule.this.onLeftSwipe();

                    // Right swipe
                } else if (-diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Schedule.this.onRightSwipe();
                }
            } catch (Exception e) {
                Log.e("MainActivity", "Error on gestures");
            }
            return false;
        }
    }
        private class SimpleOnGestureListener {
        }

}
