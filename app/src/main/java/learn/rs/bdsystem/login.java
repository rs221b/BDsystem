package learn.rs.bdsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    Button btnLogin;
    EditText etPassword,etID;
    Databasehelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etID=findViewById(R.id.etID);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);


        myDB=new Databasehelper(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=etID.getText().toString();
                String password=etPassword.getText().toString();

                Cursor res= myDB.loginRequest(id,password);
                if(res.getCount()==0) {
                    Toast.makeText(login.this, "Incorrect Phone/Password", Toast.LENGTH_LONG).show();
                    return;
                }

//                etID.setText(res.getCount());
                res.moveToNext();
                String loggedin=res.getString(1);
                String ph=res.getString(3);
                String ag=res.getString(2);
                String bg=res.getString(4);
                String ld=res.getString(5);
                String ge=res.getString(6);
                String se=res.getString(7);
                String ad=res.getString(8);
                String pa=res.getString(9);

                Intent j= new Intent(login.this,search_update.class);
                j.putExtra("user",loggedin);
                j.putExtra("uph",ph);
                j.putExtra("uag",ag);
                j.putExtra("uld",ld);
                j.putExtra("use",se);
                j.putExtra("uad",ad);
                j.putExtra("ubg",bg);
                j.putExtra("uge",ge);
                j.putExtra("upa",pa);
                startActivity(j);

            }
        });
    }
}
