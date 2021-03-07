package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText cityName;
    ImageView search;
    TextView tempText,descText,humidityText;
    String url="api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apiKey="48b84fba7d0c966c7942d6d6c4f046a1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName=findViewById(R.id.textField);
        search=findViewById(R.id.search);
        tempText=findViewById(R.id.tempText);
        descText=findViewById(R.id.descText);
        humidityText=findViewById(R.id.humidityText);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder().
                        baseUrl("https://api.openweathermap.org/data/2.5/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                weatherApi myapi=retrofit.create(weatherApi.class);
                Call<Users> usersCall=myapi.getweather(cityName.getText().toString(),apiKey);
                usersCall.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        if (response.code()==404)  //data not available
                        {Toast.makeText(MainActivity.this,"please enter a valid city",Toast.LENGTH_LONG).show();}
                        else if (!response.isSuccessful())
                        {Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_LONG).show();}

                        Users mydata=response.body();
                        Main main=mydata.getMain();
                        Double temp=main.getTemp();
                        Integer temperature=(int)(temp-273.15);
                        tempText.setText(String.valueOf(temperature) +"C");
                        descText.setText("pressure" + " " + response.body().getMain().getPressure());
                        humidityText.setText("Humidity" + " " + response.body().getMain().getHumidity());


                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
}