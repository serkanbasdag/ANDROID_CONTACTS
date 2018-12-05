package com.example.acerv17nitro.directoryapp3;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etAd, etSoyad, etTel;
    private Button btnKaydet, btnListele, btnSil, btnDuzenle;
    private ListView VeriListele;
    private int idBul = 0;
    Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAd = findViewById(R.id.etAd);
        etSoyad = findViewById(R.id.etSoyad);
        etTel = findViewById(R.id.etTel);
        btnKaydet = findViewById(R.id.btnKaydet);
        btnListele = findViewById(R.id.btnListele);
        VeriListele = findViewById(R.id.VeriListele);
        btnSil = findViewById(R.id.btnSil);
        btnDuzenle = findViewById(R.id.btnDuzenle);

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAd.length() > 0 || etSoyad.length() > 0 || etTel.length() > 0) {
                    String gelenAd = etAd.getText().toString();
                    String gelenSoyad = etSoyad.getText().toString();
                    String gelenTel = etTel.getText().toString();
                    String mess = gelenAd+" "+gelenSoyad+" Başarıyla Kaydedildi!";
                    VeriTabani vt = new VeriTabani(MainActivity.this);
                    vt.VeriEkle(gelenAd, gelenSoyad, gelenTel);

                    temizle();
                    ListViewItem();
                    Listele();
                    Toast.makeText(MainActivity.this,mess, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Lütfen Bütün Alanları Doldurunuz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temizle();
                Listele();
                ListViewItem();
            }
        });
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idBul > 0) {
                    String mess = etAd.getText().toString()+" "+etSoyad.getText().toString()+" Kalıcı Olarak Silinecek!";
                    AlertDialog.Builder uyari = new AlertDialog.Builder(con);
                    uyari.setTitle("Bu İşlem Geri Alınamaz!");
                    uyari.setMessage(mess);
                    uyari.setCancelable(false);
                    uyari.setPositiveButton("SİL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            VeriTabani vt = new VeriTabani(MainActivity.this);
                            vt.VeriSil(idBul);

                            temizle();
                            Listele();
                            ListViewItem();
                        }
                    });
                    uyari.setNegativeButton("VAZGEÇ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = uyari.create();
                    alertDialog.show();
                }
                else {
                    Toast.makeText(con, "Lütfen Silmek İstediğiniz Kişiyi Seçiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gelenAd = etAd.getText().toString();
                String gelenSoyad = etSoyad.getText().toString();
                String gelenTel = etTel.getText().toString();
                String mess = gelenAd+" "+gelenSoyad+" Başarıyla Düzenlendi!";
                VeriTabani vt = new VeriTabani(MainActivity.this);
                vt.VeriDuzenle(idBul,gelenAd,gelenSoyad,gelenTel);

                Toast.makeText(MainActivity.this,mess, Toast.LENGTH_SHORT).show();
                Listele();
                ListViewItem();
            }
        });
    }
    public void Listele() {
        VeriTabani vt = new VeriTabani(MainActivity.this);
        List<String> list = vt.VeriListele();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        VeriListele.setAdapter(adapter);
        ListViewItem();
        if(list.size()==0)
        {
            Toast.makeText(con, "Rehberde Kayıtlı Kimse Yok!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ListViewItem() {
        VeriListele.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = VeriListele.getItemAtPosition(position).toString();
                String[] itemBol = item.split(" - ");

                idBul = Integer.valueOf(itemBol[0]);
                etAd.setText(itemBol[1]);
                etSoyad.setText(itemBol[2]);
                etTel.setText(itemBol[3]);
            }
        });
    }
    public void temizle(){
        etAd.setText("");
        etSoyad.setText("");
        etTel.setText("");
    }
}

