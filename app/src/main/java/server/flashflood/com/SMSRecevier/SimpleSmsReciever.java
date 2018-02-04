package server.flashflood.com.SMSRecevier;

/**
 * Created by ismail on 7/12/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import server.flashflood.com.Model.SMSItem;


// Todo : SMS Reciever Class

public class SimpleSmsReciever extends BroadcastReceiver {

    private static final String TAG = "Message recieved";
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    public final String WaterLevel = "WaterLevel:";
    public final String DeviceId = "DeviceId:";
    public final String AreaId = "AreaId:";
    public final String TOKEN = "Token:HwoEweVdFGks==";
    public final String Time = "Time:";
    public final String FlashFloodRoot = "FlashFloodRoot";
    public final String DATA = "sensorData";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        final SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
        // Log.e(TAG, messages.getMessageBody());

        if (database == null) {
            database = FirebaseDatabase.getInstance();
        }
        if (myRef == null) {
            myRef = database.getReference().child(FlashFloodRoot).child(DATA);
        }

        SaveDataToDatabase(messages);

    }

    public void SaveDataToDatabase(SmsMessage messages) {
        //myRef.push().setValue(messages.getMessageBody());

        if (messages.getDisplayMessageBody().contains(TOKEN)) {
           /* Intent smsIntent = new Intent(context, SMS_Receive.class);
            smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            smsIntent.putExtra("MessageNumber", messages.getOriginatingAddress());
            smsIntent.putExtra("Message", messages.getMessageBody().replace("Device Token:MQAyADUANQAMAAyAA==", ""));
            context.startActivity(smsIntent);*/

            String[] strings = messages.getMessageBody().replaceAll(TOKEN, "").split(",");
            String area = "", device = "", waterlevel = "", time = "";

            for (String str : strings) {
                if (str.contains(AreaId)) {
                    area = str.replaceAll(AreaId, "");
                } else if (str.contains(DeviceId)) {
                    device = str.replaceAll(DeviceId, "");
                } else if (str.contains(WaterLevel)) {
                    waterlevel = str.replaceAll(WaterLevel, "");
                } else if (str.contains(Time)) {
                    time = str.replaceAll(Time, "");
                }
            }

            Log.e(TAG, "onReceive: " + area + " " + device + " " + " " + waterlevel + " " + time);

            if (!area.isEmpty() && !device.isEmpty() && !waterlevel.isEmpty()) {
                SMSItem sms = new SMSItem(area, device, waterlevel, time);
                if (sms != null) {
                    myRef.child(device).push()
                            .setValue(sms)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.e("Result :", "Successfully Data Send to Server");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Error :", e.getMessage());
                        }
                    });
                }
            }


            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    //for (SMSItem smsItem :  dataSnapshot.getValue(SMSItem.class))
                    Log.e("DataSnapshot ", dataSnapshot.getValue(SMSItem.class).toString());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

}
