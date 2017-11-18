package hr.ferit.dudovicic.homevisitnurse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;


import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import hr.ferit.dudovicic.homevisitnurse.helpers.InputValidation;
import hr.ferit.dudovicic.homevisitnurse.sql.DatabaseHelper;
import hr.ferit.dudovicic.homevisitnurse.model.User;




public class register extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = register.this;
    private RelativeLayout relativeLayout1;

    private Context context;


    private EditText editID1;
    private EditText edit_email1;
    private EditText password1;
    private EditText passwor1Confirm;

    private Button btnRegistration1;
    private TextView txtSignin;

    private InputValidation inputValidation;


    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);





        editID1 = (EditText) findViewById(R.id.editID1);
        edit_email1 = (EditText) findViewById(R.id.edit_email1);
        password1 = (EditText) findViewById(R.id.password1);
        passwor1Confirm = (EditText) findViewById(R.id.password1Confirm);

        btnRegistration1 = (Button) findViewById(R.id.btnRegistration1);

        txtSignin = (TextView) findViewById(R.id.txtSignin);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btnRegistration1.setOnClickListener(this);
        txtSignin.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnRegistration1:
                postDataToSQLite();
                break;

            case R.id.txtSignin:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(editID1)) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(edit_email1)) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(edit_email1)) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(password1)) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(password1, passwor1Confirm)) {
            return;
        }



        if (!databaseHelper.checkUser(edit_email1.getText().toString().trim())) {

            user.setName(editID1.getText().toString().trim());
            user.setEmail(edit_email1.getText().toString().trim());
            user.setPassword(password1.getText().toString().trim());

            databaseHelper.addUser(user);


            // Snack Bar to show success message that record saved successfully
            Snackbar.make(relativeLayout1, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
          /*  Toast.makeText(context,getString(R.string.success_message),Toast.LENGTH_SHORT).show();*/





        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(relativeLayout1, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
          /*  Toast.makeText(context,getString(R.string.error_email_exists),Toast.LENGTH_SHORT).show();*/

        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editID1.setText(null);
        edit_email1.setText(null);
        password1.setText(null);
        passwor1Confirm.setText(null);
    }




}
