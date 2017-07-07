package maybay.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SingupActivity extends AppCompatActivity implements View.OnClickListener

{
    EditText nameEditText;
    EditText surnameEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText genderEditText;
    EditText emailEditText;
    EditText telephoneEditText;
    EditText photoEditText;

    Button cancelButton;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        usernameEditText = (EditText) findViewById(R.id.useranmeEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        genderEditText = (EditText) findViewById(R.id.genderEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        telephoneEditText = (EditText) findViewById(R.id.telephoneEditText);
        photoEditText = (EditText) findViewById(R.id.photoEditText);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == okButton)
        {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Customer cus = new Customer();
                            cus.Name = nameEditText.getText().toString();
                            cus.Surname = surnameEditText.getText().toString();
                            cus.Username = usernameEditText.getText().toString();
                            cus.Password = passwordEditText.getText().toString();
                            cus.Gender = genderEditText.getText().toString();
                            cus.Email = emailEditText.getText().toString();
                            cus.Telephone = telephoneEditText.getText().toString();
                            cus.Photo = photoEditText.getText().toString();

                            DbHelper db = new DbHelper(getApplicationContext());
                            long id = db.customerAdd(cus);

                            if (id > 0)
                            {
                                Toast.makeText(getApplicationContext(), "Sign Up Success yout ID #" + id, Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error ! Someting wrong.", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }
        else if (v == cancelButton)
        {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to cancel?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
