package maybay.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    Button singupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.useranmeEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        singupButton = (Button) findViewById(R.id.signupButton);

        loginButton.setOnClickListener(this);
        singupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == loginButton)
        {
            DbHelper db = new DbHelper(getApplicationContext());
            String username = usernameEditText.getText().toString();
            String password = usernameEditText.getText().toString();
            boolean isAuthen = db.customerAuthen(username,password);
            if (isAuthen)
            {
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(i);
            }
            else Toast.makeText(getApplicationContext(), "User or Password is not correct", Toast.LENGTH_LONG).show();
        }
        else if (v == singupButton)
        {
            Intent i = new Intent(getApplicationContext(),SingupActivity.class);
            startActivity(i);
        }
    }

}
