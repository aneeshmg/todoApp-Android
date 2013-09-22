package app.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.app.Dialog;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements OnClickListener
{
    Button submit,reset;
    EditText password;
    String passwordText;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        submit = (Button) findViewById(R.id.submit);
        reset = (Button) findViewById(R.id.reset);
        password = (EditText) findViewById(R.id.passwordBox);

        submit.setOnClickListener(this);
        reset.setOnClickListener(this);
        passwordText = "";


        SharedPreferences mySharedPreferences;
        SharedPreferences.Editor myEditor;
        mySharedPreferences = getSharedPreferences("passwordfile", MODE_PRIVATE);
        if(mySharedPreferences.getString("password","password").equals("password")){
          Toast.makeText(getApplicationContext(), "default pass" , Toast.LENGTH_LONG).show();

          AlertDialog.Builder alert = new AlertDialog.Builder(this);
          alert.setTitle("First run");
          alert.setMessage("Enter new password :");


          final EditText input = new EditText(this);
          alert.setView(input);

          alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton)  {

              SharedPreferences mySharedPreferencesLocal;
              SharedPreferences.Editor myEditorLocal;

              mySharedPreferencesLocal = getSharedPreferences("passwordfile", MODE_PRIVATE);

              String value = input.getText().toString();

              myEditorLocal = mySharedPreferencesLocal.edit();
              myEditorLocal.commit();
              myEditorLocal.putString("password", value);
              myEditorLocal.commit();

              Intent startHome = new Intent("app.todo.HOME");
              startActivity(startHome);

              Toast.makeText(getApplicationContext(), value , Toast.LENGTH_LONG).show();

              return;
            }
          });

          alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
              return;
            }
          });

          alert.show();

        }

    }

    public void onClick(View v) {

        SharedPreferences mySharedPreferences;
        mySharedPreferences = getSharedPreferences("passwordfile", MODE_PRIVATE);
        final SharedPreferences.Editor myEditor = mySharedPreferences.edit();

        passwordText = mySharedPreferences.getString("password", "password");

        switch(v.getId()){

          case R.id.submit:

            if(password.getText().toString().equals(passwordText)) {
              Intent startHome = new Intent("app.todo.HOME");
              startActivity(startHome);
            }
            else Toast.makeText(getApplicationContext(), "Wrong Password Entered" , Toast.LENGTH_SHORT).show();
            break;

          case R.id.reset:
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Reset Password");
            alert.setMessage("Enter passwords :");

            final EditText oldPass = new EditText(this);
            final EditText newPass = new EditText(this);
            oldPass.setHint("Old Password");
            newPass.setHint("New Password");

            LinearLayout ll=new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(oldPass);
            ll.addView(newPass);
            alert.setView(ll);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

              public void onClick(DialogInterface dialog, int whichButton)  {
                if(oldPass.getText().toString().equals(passwordText)){
                  String newPassText = newPass.getText().toString();

                  myEditor.putString("password", newPassText);
                  myEditor.commit();
                }
                else Toast.makeText(getApplicationContext(), "Wrong Password Entered" , Toast.LENGTH_SHORT).show();
              }
            });
            alert.show();
            break;
        }
    }

   @Override
    protected void onPause()  {
      super.onPause();
      finish();
    }
}
