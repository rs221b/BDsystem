package learn.rs.bdsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class update extends AppCompatActivity {
    Button btnupdate;
    EditText etphone, etage, etlast, etsector, etaddress;
    Databasehelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent u = getIntent();
        final String ph = u.getStringExtra("uph");
        final String ag = u.getStringExtra("uag");
        final String ld = u.getStringExtra("uld");
        final String se = u.getStringExtra("use");
        final String ad = u.getStringExtra("uad");
        final String ge = u.getStringExtra("uge");
        final String pa = u.getStringExtra("upa");
        final String bg = u.getStringExtra("ubg");
        final String loggedin = u.getStringExtra("user");

        etphone = findViewById(R.id.etphone);
        etage = findViewById(R.id.etage);
        etlast = findViewById(R.id.etlast);
        etsector = findViewById(R.id.etsector);
        etaddress = findViewById(R.id.etaddress);
        myDB=new Databasehelper(this);

        etphone.setText(ph);
        etage.setText(ag);
        etlast.setText(ld);
        etsector.setText(se);
        etaddress.setText(ad);

        btnupdate = findViewById(R.id.btnUpdate);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newage = etage.getText().toString();
                String newphone = etphone.getText().toString();
                String newlast = etlast.getText().toString();
                String newsector = etsector.getText().toString();
                String newaddress = etaddress.getText().toString();

                if (isValid(newage, newphone, newlast, newsector, newaddress)) {

                    boolean profileupdated = myDB.uprofile(loggedin, newage, newphone, bg, newlast, ge, newsector, newaddress, pa, ph);
                    if (profileupdated == true) {
                        Toast.makeText(update.this, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                        Intent u=new Intent(update.this,search_update.class);
                        u.putExtra("user",loggedin);
                        u.putExtra("uph",newphone);
                        u.putExtra("uag",newage);
                        u.putExtra("uld",newlast);
                        u.putExtra("use",newsector);
                        u.putExtra("uad",newaddress);
                        u.putExtra("upa",pa);
                        u.putExtra("ubg",bg);
                        u.putExtra("uge",ge);
                        startActivity(u);

                    }
                    else
                        Toast.makeText(update.this, "Profile Updation Failed", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    boolean isValid(String newage,String newphone,String newlast,String newsector,String newaddress) {
        if (newage.trim().length() == 0 || newphone.trim().length() == 0 || newsector.trim().length() == 0 || newaddress.trim().length() == 0) {
            Toast.makeText(update.this, "Please Fill All The Mandatory Fields", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.valueOf(newage)<18)
        {
            Toast.makeText(update.this, "Sorry, you need to be above 18 years", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.valueOf(newage)>125 )
        {
            Toast.makeText(update.this, "Enter A Valid Age", Toast.LENGTH_LONG).show();
            return false;
        }

        if( newphone.length()!=10)
        {
            Toast.makeText(update.this, "Please Enter a Valid Phone No.", Toast.LENGTH_LONG).show();
            return false;
        }

        for (int i = 0; i < newphone.length(); i++)
            if (Character.isDigit(newphone.charAt(i)) == false) {
                Toast.makeText(update.this, "Please Enter a Valid Phone No.", Toast.LENGTH_LONG).show();
                return false;
            }

        if(newlast.length()!=10 ^ newlast.length()==0)
        {
            Toast.makeText(update.this, "Enter Date in format DD-MM-YYYY", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.valueOf(newsector)==13 || Integer.valueOf(newsector)>56)
        {
            Toast.makeText(update.this, "Enter a Valid Sector in Chandigarh", Toast.LENGTH_LONG).show();
            return false;
        }

    return true;
    }
}
