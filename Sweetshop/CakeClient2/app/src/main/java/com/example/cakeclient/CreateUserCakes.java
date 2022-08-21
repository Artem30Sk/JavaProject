package com.example.cakeclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CreateUserCakes extends AppCompatActivity {
    ImageView galleryPicture;
    Button butSelPicture, butCrust, butCream, butFill, butAdditn,butAdd;
    TextView costText;
    EditText titleText, descrText;
    RecipeApi recipe = new RecipeApi();
    Bitmap bitmap;
    int userCode=0;
    int recipeCode=0;
    RestService restService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_cakes);
        butSelPicture=(Button) findViewById(R.id.butSelPicture);
        galleryPicture=(ImageView) findViewById(R.id.galleryPicture);
        descrText=(EditText)findViewById(R.id.descriptionText);
        titleText=(EditText)findViewById(R.id.titleText);
        costText=(TextView)findViewById(R.id.costText);
        butAdd=(Button)findViewById(R.id.buttonCreate);
        butCrust=(Button)findViewById(R.id.buttonCrust);
        butCream=(Button)findViewById(R.id.buttonCream);
        butFill=(Button)findViewById(R.id.buttonFilling);
        butAdditn=(Button)findViewById(R.id.buttonAddition);
        restService = new RestService();
        Intent intent = getIntent();
        userCode=intent.getIntExtra("userCode",userCode);
        recipeCode=intent.getIntExtra("recipeCode",recipeCode);
        recipe.Id=recipeCode+=1;
        Log.i("Id", String.valueOf(recipe.Id));
        recipe.IdUser=userCode;
        butAdditn.setBackgroundColor(getResources().getColor(R.color.teal_200));
        butCream.setBackgroundColor(getResources().getColor(R.color.teal_200));
        butFill.setBackgroundColor(getResources().getColor(R.color.teal_200));
        butCrust.setBackgroundColor(getResources().getColor(R.color.teal_200));
        butSelPicture.setBackgroundColor(getResources().getColor(R.color.teal_200));
        butAdd.setBackgroundColor(getResources().getColor(R.color.teal_200));
    }
    public void selectPicture(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
            if(resultCode == RESULT_OK){
                try {
                   final Uri imageUri = data.getData();
                   final InputStream inputStream = getContentResolver().openInputStream(imageUri);
                   final Bitmap seletedImage = BitmapFactory.decodeStream(inputStream);
                   galleryPicture.setImageBitmap(seletedImage);
                    bitmap = seletedImage;
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void selectCrust(View view){

        restService.getUserService().getCrusts(new Callback<List<IngredientsApi>>() {
            @Override
            public void success(List<IngredientsApi> ingredientsApis, Response response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserCakes.this);
                builder.setTitle("Коржи");
// Add a checkbox list
                String[] title = new String[ingredientsApis.size()];
                for (int i = 0; i < ingredientsApis.size(); i++) {
                    title[i]=ingredientsApis.get(i).Title;
                }
                boolean[] checkedItems= new boolean[title.length];
                for (int i = 0; i <title.length ; i++) {
                    checkedItems[i]=false;
                }

                builder.setMultiChoiceItems(title, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which]=isChecked;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int j=0;
                        for (int i = 0; i <checkedItems.length ; i++) {
                            if(checkedItems[i]==true){
                                j++;
                            }
                        }
                        if(j==0 || j>3){
                            Toast.makeText(CreateUserCakes.this,"Необходимо выбрать 1-3 значения",Toast.LENGTH_LONG).show();
                        }
                        else {
                            String first="";
                            String sec="";
                            String third="";
                            recipe.TypeCrust1="";
                            recipe.TypeCrust2="";
                            recipe.TypeCrust3="";
                            for (int i = 0; i <checkedItems.length ; i++) {
                                if(checkedItems[i]==true){
                                   if(first.equals("")){
                                       first= String.valueOf(i+1);
                                       recipe.TypeCrust1=first;
                                   }
                                   else {
                                       if(sec.equals("")){
                                           sec= String.valueOf(i+1);
                                           recipe.TypeCrust2=sec;
                                       }
                                       else {
                                           third= String.valueOf(i+1);
                                           recipe.TypeCrust3=third;
                                       }
                                   }
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CreateUserCakes.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });

    }
    public void selectCream(View view){

        restService.getUserService().getCreams(new Callback<List<IngredientsApi>>() {
            @Override
            public void success(List<IngredientsApi> ingredientsApis, Response response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserCakes.this);
                builder.setTitle("Крема");
// Add a checkbox list
                String[] title = new String[ingredientsApis.size()];
                for (int i = 0; i < ingredientsApis.size(); i++) {
                    title[i]=ingredientsApis.get(i).Title;
                }
                boolean[] checkedItems= new boolean[title.length];
                for (int i = 0; i <title.length ; i++) {
                    checkedItems[i]=false;
                }

                builder.setMultiChoiceItems(title, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which]=isChecked;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int j=0;
                        for (int i = 0; i <checkedItems.length ; i++) {
                            if(checkedItems[i]==true){
                                j++;
                            }
                        }
                        if(j==0 || j>3){
                            Toast.makeText(CreateUserCakes.this,"Необходимо выбрать 1-3 значения",Toast.LENGTH_LONG).show();
                        }
                        else {
                            String first="";
                            String sec="";
                            String third="";
                            recipe.TypeCream1="";
                            recipe.TypeCream2="";
                            recipe.TypeCream3="";
                            for (int i = 0; i <checkedItems.length ; i++) {
                                if(checkedItems[i]==true){
                                    if(first.equals("")){
                                        first= String.valueOf(i+1);
                                        recipe.TypeCream1=first;
                                    }
                                    else {
                                        if(sec.equals("")){
                                            sec= String.valueOf(i+1);
                                            recipe.TypeCream2=sec;
                                        }
                                        else {
                                            third= String.valueOf(i+1);
                                            recipe.TypeCream3=third;
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CreateUserCakes.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });


    }
    public void selectFilling(View view){

        restService.getUserService().getFillings(new Callback<List<IngredientsApi>>() {
            @Override
            public void success(List<IngredientsApi> ingredientsApis, Response response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserCakes.this);
                builder.setTitle("Начинки");
// Add a checkbox list
                String[] title = new String[ingredientsApis.size()];
                for (int i = 0; i < ingredientsApis.size(); i++) {
                    title[i]=ingredientsApis.get(i).Title;
                }
                boolean[] checkedItems= new boolean[title.length];
                for (int i = 0; i <title.length ; i++) {
                    checkedItems[i]=false;
                }

                builder.setMultiChoiceItems(title, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which]=isChecked;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int j=0;
                        for (int i = 0; i <checkedItems.length ; i++) {
                            if(checkedItems[i]==true){
                                j++;
                            }
                        }
                        if(j==0 || j>3){
                            Toast.makeText(CreateUserCakes.this,"Необходимо выбрать 1-3 значения",Toast.LENGTH_LONG).show();
                        }
                        else {
                            String first="";
                            String sec="";
                            String third="";
                            recipe.TypeFilling1="";
                            recipe.TypeFilling2="";
                            recipe.TypeFilling3="";
                            for (int i = 0; i <checkedItems.length ; i++) {
                                if(checkedItems[i]==true){
                                    if(first.equals("")){
                                        first= String.valueOf(i+1);
                                        recipe.TypeFilling1=first;
                                    }
                                    else {
                                        if(sec.equals("")){
                                            sec= String.valueOf(i+1);
                                            recipe.TypeFilling2=sec;
                                        }
                                        else {
                                            third= String.valueOf(i+1);
                                            recipe.TypeFilling3=third;
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CreateUserCakes.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });


    }
    public void selectAddition(View view){

        restService.getUserService().getAdditions(new Callback<List<IngredientsApi>>() {
            @Override
            public void success(List<IngredientsApi> ingredientsApis, Response response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserCakes.this);
                builder.setTitle("Дополнения");
// Add a checkbox list
                String[] title = new String[ingredientsApis.size()];
                for (int i = 0; i < ingredientsApis.size(); i++) {
                    title[i]=ingredientsApis.get(i).Title;
                }
                boolean[] checkedItems= new boolean[title.length];
                for (int i = 0; i <title.length ; i++) {
                    checkedItems[i]=false;
                }

                builder.setMultiChoiceItems(title, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which]=isChecked;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int j=0;
                        for (int i = 0; i <checkedItems.length ; i++) {
                            if(checkedItems[i]==true){
                                j++;
                            }
                        }
                        if(j==0 || j>3){
                            Toast.makeText(CreateUserCakes.this,"Необходимо выбрать 1-3 значения",Toast.LENGTH_LONG).show();
                        }
                        else {
                            String first="";
                            String sec="";
                            String third="";
                            recipe.TypeAddition1="";
                            recipe.TypeAddition2="";
                            recipe.TypeAddition3="";
                            for (int i = 0; i <checkedItems.length ; i++) {
                                if(checkedItems[i]==true){
                                    if(first.equals("")){
                                        first= String.valueOf(i+1);
                                        recipe.TypeAddition1=first;
                                    }
                                    else {
                                        if(sec.equals("")){
                                            sec= String.valueOf(i+1);
                                            recipe.TypeAddition2=sec;
                                        }
                                        else {
                                            third= String.valueOf(i+1);
                                            recipe.TypeAddition3=third;
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CreateUserCakes.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });


    }
    public  void createRecipe(View view){
        recipe.Title=titleText.getText().toString();
        recipe.Description=descrText.getText().toString();
        recipe.Cost=2000;
        if(bitmap !=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] imageByteArray = stream.toByteArray();
            String base64String = Base64.encodeToString(imageByteArray, Base64.NO_WRAP);
            recipe.Photo = base64String;
        }
        else {
        recipe.Photo="";
        }

        restService.getUserService().addRecipes(recipe, new Callback<RecipeApi>() {
            @Override
            public void success(RecipeApi recipeApi, Response response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserCakes.this);
                builder.setTitle("Добавление рецепта");
                builder.setMessage("Ваш рецепт успешно создан");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CreateUserCakes.this, CatalogActivity.class);
                        intent.putExtra("userCode", userCode);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CreateUserCakes.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

}