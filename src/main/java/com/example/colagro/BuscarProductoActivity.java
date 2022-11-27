package com.example.colagro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.colagro.Clases.Categoria;
import com.example.colagro.Clases.Modelo;

import java.util.ArrayList;

public class BuscarProductoActivity extends AppCompatActivity {

    Spinner SpinCat;
    Button btnBuscarProd, btnElimProd, btnActProd;
    EditText ProdBus, nom, can, img, pres, prec, cat;
    SQLiteDatabase miBase;
    ArrayList<String> listaCategorias;
    ArrayList<Categoria> categorias;

    //creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // key for storing name
    public static final String NAME_KEY = "name_key";

    // key for storing id
    public static final String ID_KEY = "id_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String email, password, name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);

        btnBuscarProd = findViewById(R.id.btnBuscarProd);
        btnElimProd = findViewById(R.id.BtnElimProd);
        btnActProd = findViewById(R.id.btnActProd);
        ProdBus = findViewById(R.id.txtBusProd);
        nom = findViewById(R.id.txtProdNom2);
        can = findViewById(R.id.txtProdCan2);
        img = findViewById(R.id.txtProdImg2);
        pres = findViewById(R.id.txtProdPres2);
        prec = findViewById(R.id.txtProdPrec2);
        SpinCat = findViewById(R.id.SpinCatAct);

        consultarListaCategorias();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1,listaCategorias);

        SpinCat.setAdapter(adaptador);


        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        email = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY,null);
        name = sharedpreferences.getString(NAME_KEY, null);
        id = sharedpreferences.getString(ID_KEY,null);

        btnElimProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String prod = ProdBus.getText().toString().trim();

                    Modelo admin = new Modelo(getApplicationContext());
                    miBase = admin.getWritableDatabase();

                    Cursor fila = miBase.rawQuery("SELECT * FROM producto WHERE pro_nom ="+"'"+prod+"' AND " +
                            "pro_us_id = " + id,null);

                    if(fila.moveToFirst()){
                        Toast toast = Toast.makeText(getApplicationContext(),"Hostias!!! ",Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),"No se encuentra ningún producto!!!",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }catch (SQLiteException e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Error: " + e,Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnActProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String prod = ProdBus.getText().toString().trim();

                    Modelo admin = new Modelo(getApplicationContext());
                    miBase = admin.getWritableDatabase();

                    Cursor fila = miBase.rawQuery("SELECT * FROM producto WHERE pro_nom ="+"'"+prod+"' AND " +
                            "pro_us_id = " + id,null);

                    if(fila.moveToFirst()){
                        Toast toast = Toast.makeText(getApplicationContext(),"Hostias!!! ",Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),"No se encuentra ningún producto!!!",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }catch (SQLiteException e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Error: " + e,Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnBuscarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String prod = ProdBus.getText().toString().trim();

                    Modelo admin = new Modelo(getApplicationContext());
                    miBase = admin.getWritableDatabase();

                    Cursor fila = miBase.rawQuery("SELECT * FROM producto  JOIN categoria ON pro_cat_id = cat_id" +
                            " WHERE pro_nom ="+"'"+prod+"' AND pro_us_id = " + id +"",null);

                    if(fila.moveToFirst()){
                        nom.setText(fila.getString(1));
                        can.setText(fila.getString(2));
                        img.setText(fila.getString(3));
                        pres.setText(fila.getString(4));
                        prec.setText(fila.getString(5));


                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),"No se encuentra ningún producto!!!",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }catch (SQLiteException e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Error: " + e,Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }


    private void consultarListaCategorias() {
        try {
            Modelo admin = new Modelo(getApplicationContext());
            SQLiteDatabase miBase = admin.getWritableDatabase();

            Categoria categoria;
            categorias = new ArrayList<Categoria>();
            Cursor fila = miBase.rawQuery("SELECT * FROM categoria", null);

            while (fila.moveToNext()) {
                categoria = new Categoria();
                categoria.setId(fila.getInt(0));
                categoria.setNombre(fila.getString(1));

                categorias.add(categoria);
            }

            fila.close();
            obtenerLista();

        }catch (SQLiteException e){
            Toast toast = Toast.makeText(getApplicationContext(),"Error: "+e,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void obtenerLista() {
        listaCategorias = new ArrayList<String>();
        listaCategorias.add("Seleccione");

        for (int i = 0; i<categorias.size(); i++){
            listaCategorias.add(categorias.get(i).getNombre());
        }
    }
}