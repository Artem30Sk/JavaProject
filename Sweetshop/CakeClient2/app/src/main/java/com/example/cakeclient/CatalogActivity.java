package com.example.cakeclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
public class CatalogActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private CatalogAdapter mAdapter;
    private List<CatalogApi> mCatalogList= new ArrayList<>();
    RestService restService;
    int userCode=0;
    int recipeCode=0;
    public static int usrCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        restService = new RestService();

        Intent intent = getIntent();
        userCode=intent.getIntExtra("userCode",userCode);
        usrCode = userCode;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemSpin = menu.findItem(R.id.spinner);
        MenuItem itemBut = menu.findItem(R.id.addBut);
        Spinner spinner = (Spinner) itemSpin.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        int id=item.getItemId();
        if(id == R.id.addBut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CatalogActivity.this);

            builder.setTitle("Добавление рецепта");
            builder.setMessage("Вы точно уверены?");
            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(CatalogActivity.this, CreateUserCakes.class);
                    intent.putExtra("userCode", userCode);
                    intent.putExtra("recipeCode", recipeCode);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(true);
            builder.show();
        }
            return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                all();
                break;
            case 1:
                my();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void all(){
        restService.getUserService().getRecipes(new Callback<List<CatalogApi>>() {
            @Override
            public void success(List<CatalogApi> catalogApis, Response response) {
                mCatalogList.clear();
                recipeCode=catalogApis.get(catalogApis.size()-1).Id;
                for (int i = 0; i < catalogApis.size(); i++) {
                    if(catalogApis.get(i).IdUser == 1){
                        mCatalogList.add(catalogApis.get(i));
                    }
                }
                ListView listCatalog = findViewById(R.id.magCatalog);
                mAdapter = new CatalogAdapter(CatalogActivity.this,mCatalogList);
                listCatalog.setAdapter(mAdapter);

            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CatalogActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
    private void my(){
        restService.getUserService().getRecipes(new Callback<List<CatalogApi>>() {
            @Override
            public void success(List<CatalogApi> catalogApis, Response response) {
                mCatalogList.clear();
                Log.i("usr", String.valueOf(userCode));
                for (int i = 0; i < catalogApis.size(); i++) {
                    if(catalogApis.get(i).IdUser == userCode){
                        Log.i("usr", String.valueOf(userCode));
                        mCatalogList.add(catalogApis.get(i));
                    }
                }
                Log.i("usr", String.valueOf(mCatalogList));
                ListView listCatalog = findViewById(R.id.magCatalog);
                mAdapter = new CatalogAdapter(CatalogActivity.this,mCatalogList);
                listCatalog.setAdapter(mAdapter);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CatalogActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
}