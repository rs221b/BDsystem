package learn.rs.bdsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class search_update extends AppCompatActivity {

    EditText etblood,etsector;
    Button btnSearch,btnUpdate,btnlogout;
    TextView tvWelcome;
    Databasehelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_update);

        btnSearch=findViewById(R.id.btnSearch);
        btnUpdate=findViewById(R.id.btnUpdate);
        etblood=findViewById(R.id.etblood);
        etsector=findViewById(R.id.etsector);
        btnlogout=findViewById(R.id.btnlogout);
        tvWelcome=findViewById(R.id.tvWelcome);
        myDB=new Databasehelper(this);


        Intent j= getIntent();
       final String loggedin=j.getStringExtra("user");
        tvWelcome.setText("Welcome " + loggedin);
        final String ph= j.getStringExtra("uph");
        final String ag= j.getStringExtra("uag");
        final String ld= j.getStringExtra("uld");
        final String se= j.getStringExtra("use");
        final String ad= j.getStringExtra("uad");
        final String ge= j.getStringExtra("uge");
        final String pa= j.getStringExtra("upa");
        final String bg= j.getStringExtra("ubg");
//        final String loggedin= j.getStringExtra("user");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u=new Intent(search_update.this,update.class);
                u.putExtra("user",loggedin);
                u.putExtra("uph",ph);
                u.putExtra("uag",ag);
                u.putExtra("uld",ld);
                u.putExtra("use",se);
                u.putExtra("uad",ad);
                u.putExtra("upa",pa);
                u.putExtra("ubg",bg);
                u.putExtra("uge",ge);
                startActivity(u);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(search_update.this,MainActivity.class);
                startActivity(a);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String blood=etblood.getText().toString();
                String sec=etsector.getText().toString();

                if(valid(blood,sec)) {
                    Cursor res = myDB.getresults(blood, sec);
                    if (res.getCount() == 0) {
                        Toast.makeText(search_update.this, "No Results", Toast.LENGTH_LONG).show();
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Name: " + res.getString(1) + "\n");
                        buffer.append("Last Donated: " + res.getString(5) + "\n");
//                    buffer.append("Gender: "+ res.getString(6) + "\n" );
                        buffer.append("Phone: " + res.getString(3) + "\n");
                        buffer.append("Address: " + res.getString(8) + "\n\n");
                    }
                    showresults(buffer.toString());

                }
            }
        });


    }
    public void showresults(String message)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Results for Sector"+ etsector.getText().toString());
        builder.setMessage(message);
        builder.show();
    }

    boolean valid(String blood, String sec)
    {
        if(blood.trim().length()==0 || sec.trim().length()==0 )
        {
            Toast.makeText(search_update.this, "Please Fill All The Fields", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!(blood.equals("A+") || blood.equals("B+") || blood.equals("AB+") || blood.equals("O+") || blood.equals("A-") || blood.equals("B-") || blood.equals("AB-") || blood.equals("O-"))) {
            Toast.makeText(search_update.this, "Enter A Valid Blood Group (without space)", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.valueOf(sec)==13 || Integer.valueOf(sec)>56)
        {
            Toast.makeText(search_update.this, "Enter a Valid Sector in Chandigarh", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
