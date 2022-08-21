package com.example.cakeclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AuthActivity extends AppCompatActivity {
    EditText logBox,passBox,phBox,addrBox;
    private int param;
    TextView joText,regText,swapText;
    Button joBut,regBut;
    private int authCode=0;
    RestService restService;
    public int userCode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestService();
        setContentView(R.layout.activity_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        param=0;
        joText=(TextView)findViewById(R.id.SignText);
        joBut=(Button) findViewById(R.id.joinBut);
        regBut=(Button) findViewById(R.id.regBut);
        regText=(TextView)findViewById(R.id.RegText);
        phBox=(EditText)findViewById(R.id.phoneBox);
        addrBox=(EditText)findViewById(R.id.addressBox);
        logBox=(EditText)findViewById(R.id.emBox);
        passBox=(EditText)findViewById(R.id.passBox);
        joBut.setBackgroundColor(getResources().getColor(R.color.teal_200));
        regBut.setBackgroundColor(getResources().getColor(R.color.teal_200));
        swapText=(TextView) findViewById(R.id.swapText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_auth, menu);

        return true;
    }

    public void onSwapClick(View view){
        passBox.setText("");
        logBox.setText("");
        phBox.setText("");
        addrBox.setText("");
        if(param==1){
            swapText.setText("Нет аккаунта? Создать");
            regText.setVisibility(View.GONE);
            regBut.setVisibility(View.GONE);
            phBox.setVisibility(View.GONE);
            addrBox.setVisibility(View.GONE);
            joText.setVisibility(View.VISIBLE);
            joBut.setVisibility(View.VISIBLE);
            param-=1;
        }
        else {
            swapText.setText("Авторизоваться");
            phBox.setVisibility(View.VISIBLE);
            addrBox.setVisibility(View.VISIBLE);
            regText.setVisibility(View.VISIBLE);
            regBut.setVisibility(View.VISIBLE);
            joText.setVisibility(View.GONE);
            joBut.setVisibility(View.GONE);
            param+=1;
        }
    }
    public void onRegClick(View view)
    {
        restService.getUserService().getUsers(new Callback<List<UserApi>>() {
            @Override
            public void success(List<UserApi> userApis, Response response) {
               UserApi usr = new UserApi();
               usr.Id=userApis.get(userApis.size()-1).Id+1;
               usr.Login=logBox.getText().toString();
               usr.Password=passBox.getText().toString();
               usr.Phone=phBox.getText().toString();
               usr.Address=addrBox.getText().toString();

               int regCode=0;
                for (int i = 0; i < userApis.size(); i++) {
                    if(usr.Login.toString().equals(userApis.get(i).Login) && usr.Password.toString().equals(userApis.get(i).Password)){
                        regCode+=1;
                    }
                }
                if(regCode==1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);

                    builder.setTitle("Регистрация");
                    builder.setMessage("Пользователь с такими логином и паролем уже есть в системе");
                    builder.setPositiveButton("Ok",null);
                    builder.setCancelable(false);
                    builder.show();

                }
                else {
                    restService.getUserService().addUsers(usr, new Callback<UserApi>() {
                        @Override
                        public void success(UserApi userApi, Response response) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);

                            builder.setTitle("Регистрация");
                            builder.setMessage("Пользователь успешно создан");
                            builder.setPositiveButton("Ok",null);
                            builder.setCancelable(false);
                            builder.show();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);

                            builder.setTitle("Регистрация");
                            builder.setMessage(error.getMessage().toString());
                            builder.setPositiveButton("Ok",null);
                            builder.setCancelable(false);
                            builder.show();

                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(AuthActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void onAuthClick(View view)
    {

        authCode=0;
        if(logBox.length()==0 || passBox.length()==0 ){
            AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);

            builder.setTitle("Авторизация");
            builder.setMessage("Не все поля заполнены");
            builder.setPositiveButton("Ok",null);
            builder.setCancelable(false);
            builder.show();

        }
        else {
            restService.getUserService().getUsers(new Callback<List<UserApi>>() {
                @Override
                public void success(List<UserApi> userApis, Response response) {
                    String log;
                    String pas;
                    for (int i = 0; i < userApis.size(); i++) {
                        log = userApis.get(i).Login;
                        pas = userApis.get(i).Password;

                        if (logBox.getText().toString().equals(log) && passBox.getText().toString().equals(pas)) {
                            authCode += 1;
                            userCode = userApis.get(i).Id;
                        }
                    }
                    if (authCode == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);

                        builder.setTitle("Авторизация");
                        builder.setMessage("Добро пожаловать!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AuthActivity.this, CatalogActivity.class);
                                intent.putExtra("userCode",userCode);
                                startActivity(intent);
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);

                        builder.setTitle("Авторизация");
                        builder.setMessage("Неправильно введены данные");
                        builder.setPositiveButton("Ok",null);
                        builder.setCancelable(false);
                        builder.show();}

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(AuthActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}