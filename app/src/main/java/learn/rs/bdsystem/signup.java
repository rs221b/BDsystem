package learn.rs.bdsystem;

        import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    EditText etName,etPhone,etAge,etBlood,etLast,etGender,etSector,etAddress,etPassword;
    Button btnSignup;
    Databasehelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        myDB=new Databasehelper(this);
        etName=findViewById(R.id.etName);
        etPhone=findViewById(R.id.etPhone);
        etAge=findViewById(R.id.etAge);
        etBlood=findViewById(R.id.etBlood);
        etLast=findViewById(R.id.etLast);
        etGender=findViewById(R.id.etGender);
        etSector=findViewById(R.id.etSector);
        etAddress=findViewById(R.id.etAddress);
        etPassword=findViewById(R.id.etPassword);
        btnSignup=findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = etName.getText().toString();
                String uage = etAge.getText().toString();
                String uphone = etPhone.getText().toString();
                String ublood = etBlood.getText().toString();
                String ulast = etLast.getText().toString();
                String ugender = etGender.getText().toString();
                String usector = etSector.getText().toString();
                String uaddress = etAddress.getText().toString();
                String upassword = etPassword.getText().toString();


                if (validData(uname, uage, uphone, ublood, ulast, ugender, usector, uaddress, upassword)) {

//                    StringBuffer newString = new StringBuffer(ulast);
//                    newString.insert(2,'-');
//                    newString.insert(5,'-');
//                    ulast=newString.toString();


                    boolean isInserted = myDB.insertData(uname, uage, uphone, ublood, ulast, ugender, usector, uaddress, upassword);
                    if (isInserted) {
                        Toast.makeText(signup.this, "Signup Successful", Toast.LENGTH_LONG).show();
                        Intent m = new Intent(signup.this, MainActivity.class);
                        startActivity(m);
                    } else
                        Toast.makeText(signup.this, "Signup Unsuccessful. Please Check the Details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    boolean validData(String uname,String uage,String uphone,String ublood,String ulast,String ugender,String usector,String uaddress,String upassword)
    {

        if(uage.trim().length()==0 || uname.trim().length()==0 || uphone.trim().length()==0 || ublood.trim().length()==0 || ugender.trim().length()==0 || usector.trim().length()==0 || uaddress.trim().length()==0 || upassword.trim().length()==0  )
        {
        Toast.makeText(signup.this, "Please Fill All The Mandatory Fields", Toast.LENGTH_LONG).show();
        return false;
        }

        for (char c : uname.toCharArray()) {
            if (Character.isDigit(c)) {
                Toast.makeText(signup.this, "Enter A Valid Name", Toast.LENGTH_LONG).show();
                return false;

            }
        }


        if(Integer.valueOf(uage)<18)
        {
            Toast.makeText(signup.this, "Sorry, you need to be above 18 years", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.valueOf(uage)>125 )
        {
            Toast.makeText(signup.this, "Enter A Valid Age", Toast.LENGTH_LONG).show();
            return false;
        }

        if( uphone.length()!=10)
        {
            Toast.makeText(signup.this, "Please Enter a Valid Phone No.", Toast.LENGTH_LONG).show();
            return false;
        }

        for (int i = 0; i < uphone.length(); i++)
            if (Character.isDigit(uphone.charAt(i)) == false) {
                Toast.makeText(signup.this, "Please Enter a Valid Phone No.", Toast.LENGTH_LONG).show();
                return false;
            }

        if(!(ublood.equals("A+") || ublood.equals("B+") || ublood.equals("AB+") || ublood.equals("O+") || ublood.equals("A-") || ublood.equals("B-") || ublood.equals("AB-") || ublood.equals("O-"))) {
             Toast.makeText(signup.this, "Enter A Valid Blood Group (without space)", Toast.LENGTH_LONG).show();
            return false;
        }

        if(ulast.length()!=10 ^ ulast.length()==0)
        {
            Toast.makeText(signup.this, "Enter Date in format DD-MM-YYYY", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!(ugender.equals("M") || ugender.equals("F")))
        {
            Toast.makeText(signup.this, "Enter Gender as M/F", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.valueOf(usector)==13 || Integer.valueOf(usector)>56)
    {
        Toast.makeText(signup.this, "Enter a Valid Sector in Chandigarh", Toast.LENGTH_LONG).show();
        return false;
    }
        return true;
    }

}
