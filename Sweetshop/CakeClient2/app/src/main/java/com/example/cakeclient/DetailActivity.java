package com.example.cakeclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import static com.example.cakeclient.CatalogActivity.usrCode;
public class DetailActivity extends AppCompatActivity {

    RestService restService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        restService = new RestService();
        CatalogApi catalogApi = getIntent().getParcelableExtra("catalog");
        ImageView imageView = findViewById(R.id.imageObject);
        TextView txtTitle = findViewById(R.id.textTitle);
        TextView txtDescription = findViewById(R.id.textDescription);
        TextView txtCost = findViewById(R.id.textCost);
        Button btnOrder = findViewById(R.id.buttonOrder);
        btnOrder.setBackgroundColor(getResources().getColor(R.color.teal_200));
        if(catalogApi.getBitmapSource()==null){
            imageView.setImageResource(R.drawable.disable_picture);
        }
        else{
            imageView.setImageBitmap(catalogApi.getBitmapSource());
        }
        txtTitle.setText(catalogApi.getTitle());
        txtDescription.setText(catalogApi.getDescription());
        txtCost.setText("Цена " + String.valueOf(catalogApi.getCost()) + " руб/кг");

    }

    public void Order(View view){

        OrderApi order = new OrderApi();
        restService.getUserService().getOrders(new Callback<List<OrderApi>>() {
            @Override
            public void success(List<OrderApi> orderApis, Response response) {
                order.Id=orderApis.get(orderApis.size()-1).Id +1;
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(DetailActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
        String[] s = { "1","2","3","4","5"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        final TextView addressHint = new TextView(DetailActivity.this);
        final TextView space = new TextView(DetailActivity.this);
        final EditText addressText = new EditText(DetailActivity.this);
        final TextView weightHint = new TextView(DetailActivity.this);
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(DetailActivity.this,
                android.R.layout.simple_spinner_item, s);
        addressHint.setText("  Введите адрес доставки");
        weightHint.setText("  Укажите вес торта");
        addressHint.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        weightHint.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        addressText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        final Spinner sp = new Spinner(DetailActivity.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        final int[] weight = new int[1];
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        weight[0] =1;
                        break;
                    case 1:
                        weight[0] =2;
                        break;
                    case 2:
                        weight[0] =3;
                        break;
                    case 3:
                        weight[0] =4;
                        break;
                    case 4:
                        weight[0] =5;
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                weight[0]=-1;
            }

        });
        builder.setTitle("Формирование заказа");
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(space);
        lay.addView(addressHint);
        lay.addView(addressText);
        lay.addView(weightHint);
        lay.addView(sp);
        builder.setView(lay);
        builder.setPositiveButton("Сформировать заказ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(addressText.getText().toString().equals(null) || (weight[0]==-1)){
                    Toast.makeText(DetailActivity.this,"Неверно указаны данные",Toast.LENGTH_LONG).show();
                }
                else {
                    CatalogApi catalogApi = getIntent().getParcelableExtra("catalog");
                    CatalogActivity catalogActivity = new CatalogActivity();
                    order.IdUser=usrCode;
                    order.IdRecipe=catalogApi.getId();
                    order.Weight=weight[0];
                    order.Cost=catalogApi.getCost()*order.Weight;
                    order.Address=addressText.getText().toString();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    order.Data=formatter.format(date);
                    restService.getUserService().addOrders(order, new Callback<OrderApi>() {
                        @Override
                        public void success(OrderApi orderApi, Response response) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                            final EditText addressText = new EditText(DetailActivity.this);
                            builder.setTitle("Формирование заказа");
                            builder.setMessage("Ваш заказ №" + order.Id + " сформирован\n" + "По адресу " + order.Address + "\n"+"Итоговая цена - "+order.Cost);
                            builder.setPositiveButton("Ok",null);
                            builder.show();
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }
}