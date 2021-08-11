package com.example.apicallintentservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText Enterid;
    private Button click;
    private TextView title;
    private TextView userId;
    private TextView body;
    private TextView id;
    private Intent intent;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,intent.getStringExtra("sendTitle"),Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter=new IntentFilter("com.reeta.service");
        registerReceiver(receiver,intentFilter);
        Enterid=findViewById(R.id.edtId);
        click=findViewById(R.id.btnclick);
        title=findViewById(R.id.txttitle);
        userId=findViewById(R.id.txtUserId);
        body=findViewById(R.id.txtBody);
        id=findViewById(R.id.txtId);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.putExtra("enterId",Enterid.getText().toString());
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("userId",userId.getText());
                intent.putExtra("body",body.getText().toString());
                intent.putExtra("id",id.getText());
                startService(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}