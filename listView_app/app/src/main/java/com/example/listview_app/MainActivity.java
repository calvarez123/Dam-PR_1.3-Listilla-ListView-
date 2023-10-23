package com.example.listview_app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String nom;
        public int imgnombre;

        public Record(int _intents, String _nom, int _imgnombre ) {
            intents = _intents;
            nom = _nom;
            imgnombre = _imgnombre;
        }

        public int getIntents() {
            return intents;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public void setIntents(int intents) {
            this.intents = intents;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add( new Record(33,"Manolo",getResources().getIdentifier("foto1", "drawable", getPackageName())) );
        records.add( new Record(12,"Pepe",getResources().getIdentifier("foto3", "drawable", getPackageName())) );
        records.add( new Record(42,"Laura",getResources().getIdentifier("foto2", "drawable", getPackageName())) );

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            // CON ESTO GESTIONAMOS LA UNION DEL ACTIVITI MAIN CON EL LISTITEM.XML
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // Configurar el ImageView


                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                ImageView imageView = convertView.findViewById(R.id.imagen);
                imageView.setImageResource(getItem(pos).imgnombre);

                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        Button boton2 = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> nombres = Arrays.asList(
                        "Juan", "María", "Luis", "Ana", "Pedro", "Laura", "Carlos", "Sofia",
                        "Alejandro", "Andrea", "Miguel", "Natalia", "Manuel", "Carmen", "Javier",
                        "Paula", "Alberto", "Raquel", "David", "Isabel"
                );
                String[] nombresDeImagenes = {"foto1", "foto2", "foto3", "foto4", "foto5"};
                Random random = new Random();

                // Selecciona un nombre aleatorio
                int indiceAleatorio = random.nextInt(nombres.size());
                String nombreAleatorio = nombres.get(indiceAleatorio);
                int numeroAleatorio = random.nextInt(100) + 1;
                int posicion = random.nextInt(nombresDeImagenes.length);
                String imagen = nombresDeImagenes[posicion];
                int imageResourcee = getResources().getIdentifier(imagen, "drawable", getPackageName());

                for (int i=0;i<1;i++) {
                    records.add(new Record(numeroAleatorio, nombreAleatorio,imageResourcee));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<Record> comparadorPorIntentos = new Comparator<Record>() {
                    @Override
                    public int compare(Record record1, Record record2) {
                        return Integer.compare(record1.getIntents(), record2.getIntents());
                    }
                };

                // Ordena la lista de jugadores utilizando el Comparator
                Collections.sort(records, comparadorPorIntentos);
                adapter.notifyDataSetChanged();
            }
        });



    }
}