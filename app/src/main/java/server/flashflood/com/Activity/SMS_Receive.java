package server.flashflood.com.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import server.flashflood.com.R;

// Todo : Activity Class

public class SMS_Receive extends AppCompatActivity {


  /*  Button msg;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms__receive);

        // Todo : Receive Message And Number In Intent

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessageNumber");
            String message = extras.getString("Message");
            TextView addressField = (TextView) findViewById(R.id.address);
            TextView messageField = (TextView) findViewById(R.id.message);

            // Todo : Set Number And Message In TextView

            addressField.setText("Message From : " +address);
            messageField.setText(message);
        }
       /* msg = (Button)findViewById(R.id.btn_msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

}
