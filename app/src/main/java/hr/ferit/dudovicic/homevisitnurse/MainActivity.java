package hr.ferit.dudovicic.homevisitnurse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import hr.ferit.dudovicic.homevisitnurse.sql.DatabaseHelper;
import hr.ferit.dudovicic.homevisitnurse.helpers.InputValidation;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = MainActivity.this;

    private RelativeLayout relativeLayout;
    private EditText editID;
    private EditText password;

    private Button ButtonLogin;
    private Button ButtonReg;

    private Context context;



    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        initViews();
        initListeners();
        initObjects();


    }


    /**
     * This method is to initialize views
     */
    private void initViews() {

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);




        editID = (EditText) findViewById(R.id.editID);
        password = (EditText) findViewById(R.id.password);

        ButtonLogin = (Button) findViewById(R.id.btnSignIn);

        ButtonReg = (Button) findViewById(R.id.btnRegistration);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        ButtonLogin.setOnClickListener(this);
        ButtonReg.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);



    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                verifyFromSQLite();

                break;
            case R.id.btnRegistration:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), register.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {

        if (!inputValidation.isInputEditTextFilled(editID)) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editID)) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(password)) {
            return;
        }

        if (databaseHelper.checkUser(editID.getText().toString().trim()
                , password.getText().toString().trim())) {



            Intent accountsIntent = new Intent(getApplicationContext(), Schedule.class);
            accountsIntent.putExtra("EMAIL", editID.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(relativeLayout, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            /*Toast.makeText(context,getString(R.string.error_valid_email_password),Toast.LENGTH_SHORT).show();*/

        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editID.setText(null);
        password.setText(null);
    }


}
