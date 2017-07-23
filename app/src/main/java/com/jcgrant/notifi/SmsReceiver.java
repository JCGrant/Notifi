package com.jcgrant.notifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SmsReceiver extends BroadcastReceiver {

    private static final String URL = "http://192.168.1.114:9090";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            byte[][] pdus = (byte[][]) bundle.get("pdus");
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage message = SmsMessage.createFromPdu(pdus[i]);
                JSONObject json = new JSONObject();
                try {
                    json.put("from", message.getOriginatingAddress());
                    json.put("msg", message.getMessageBody());
                } catch (JSONException e) {
                    Log.e("SmsReceiver", e.toString());
                }
                sendJson(context, URL, json);
            }
        }
    }

    private void sendJson(Context context, String url, JSONObject json) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, json,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("SmsReceiver", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("SmsReceiver", error.toString());
                }
            });
        queue.add(stringRequest);
    }
}
