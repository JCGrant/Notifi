package com.jcgrant.notifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            byte[][] pdus = (byte[][]) bundle.get("pdus");
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage message = SmsMessage.createFromPdu(pdus[i]);
                String msgFrom = message.getOriginatingAddress();
                String msgBody = message.getMessageBody();
                Log.d("SmsReceiver", msgFrom + " : " + msgBody);
            }
        }
    }
}
