package com.example.noticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/all.json", datos,
                MainActivity.this, MainActivity.this);
        ws.execute("");

        ListView lstOpciones = (ListView)findViewById(R.id.lvnoticia);
        lstOpciones.setOnItemClickListener(this);

        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

    }

    @Override
    public void processFinish(String result) throws JSONException {

        ArrayList<Pais> paises_lista= new ArrayList<Pais>();
        JSONObject jsonObj1 = new JSONObject(result);
        JSONObject jsonObj2 = jsonObj1.getJSONObject("Results");
        Iterator<?> iterator = jsonObj2.keys();
        while (iterator.hasNext()){
            String key =(String)iterator.next();
            JSONObject jsonPais = jsonObj2.getJSONObject(key);
            Pais pais = new Pais();

            pais.setTitulo(jsonPais.getString("Name"));
            JSONObject jsonCodigo = jsonPais.getJSONObject("CountryCodes");
            pais.setUrl("http://www.geognos.com/api/en/countries/flag/"+jsonCodigo.getString("iso2")+".png");
            paises_lista.add(pais);
        }



        AdaptadorPais adaptadornoticias = new AdaptadorPais(this, paises_lista);

        ListView lstOpciones = (ListView)findViewById(R.id.lvnoticia);
        lstOpciones.setAdapter(adaptadornoticias);


    }

    private DownloadManager.Request request;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        String nombre=((Pais)adapterView.getItemAtPosition(i)).getTitulo();

        Toast.makeText(this,
                ((Pais)adapterView.getItemAtPosition(i)).getUrl(),
                Toast.LENGTH_LONG).show();


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(((Pais)adapterView.getItemAtPosition(i)).getUrl()));
        request.setDescription("PDF Paper");
        request.setTitle(nombre);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filedownload.png");
        DownloadManager manager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            manager.enqueue(request);
            Toast.makeText(this, "Descarga completa!!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void getPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1) {
            Toast.makeText(this.getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        }
    }
}
