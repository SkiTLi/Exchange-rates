package com.sktl.nbrbcurrency;

import android.content.Intent;

import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.sktl.nbrbcurrency.libs.TinyDB;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;

import java.util.List;

import static com.sktl.nbrbcurrency.Util.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "sssMA";
    private URL requestToday;
    private URL requestTomorrow;
    private URL requestYesterday;
    private TextView Date1textView;
    private TextView Date2textView;
    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private List<Quotation> quotationsToday;
    private List<Quotation> quotationsTomorrow;
    private List<Quotation> quotationsYesterday;
    private LinearLayoutManager layoutManager;
    private PersistantStorage storage;
    private Util util;
    private UsedDate date;
    private RequestTaskInner rts;
    private TinyDB tinydb;

    public class RequestTaskInner extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String strResponse) {


            Parser parser = new Parser(getBaseContext(), strResponse);

            if (parser.getRates(strResponse).size() > 0) {

                List<Quotation> quotations = parser.getRates(strResponse);
                Log.d(TAG, "quotations.toString()=" + quotations.toString());

                String dateOfResponse = quotations.get(0).getDate();
                Log.d(TAG, "dateOfResponse=" + dateOfResponse);

                if (dateOfResponse.equals(date.getDateString())) {
                    tinydb.putListObject("today", quotations);


                    if (tinydb.getListObject("yesterday", Quotation.class).isEmpty()) {
                        tinydb.putListObject("yesterday", quotations);
                    }


                }

                if (dateOfResponse.equals(date.getPreviousDateString())) {
                    tinydb.putListObject("yesterday", quotations);

                }

                if (dateOfResponse.equals(date.getNextDateString())) {
                    if (quotations.isEmpty()) {
                    } else {
                        tinydb.putListObject("tomorrow", quotations);
                        quotationsYesterday = quotationsToday;
                        quotationsTomorrow = tinydb.getListObject("tomorrow", Quotation.class);
                        quotationsToday = quotationsTomorrow;
                    }
                }


                Date1textView.setText(tinydb.getListObject("yesterday", Quotation.class).get(0).getDate());
                Date2textView.setText(tinydb.getListObject("today", Quotation.class).get(0).getDate());

                onResume();


            } else {
                Log.d(TAG, "parser.getRates(strResponse).size() <= 0");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage.init(this);

        //пдключили стороннюю юиюлиотеку по сохранению объектов в SharedPreferences
        tinydb = new TinyDB(this);

        date = new UsedDate();
        Date1textView = findViewById(R.id.tv_firstValue);
        Date2textView = findViewById(R.id.tv_secondValue);


        util = new Util();
        requestToday = util.generateURL(date.getDateString());
        requestTomorrow = util.generateURL(date.getNextDateString());
        requestYesterday = util.generateURL(date.getPreviousDateString());
        recyclerView = findViewById(R.id.rv_first);

        quotationsToday = new ArrayList<>();
        quotationsTomorrow = new ArrayList<>();
        quotationsYesterday = new ArrayList<>();


        if (tinydb.getAll().isEmpty()) {
            tinydb.putListObject("today", quotationsToday);
            tinydb.putListObject("tomorrow", quotationsTomorrow);
            tinydb.putListObject("yesterday", quotationsYesterday);
            Toast.makeText(getBaseContext(), "создается <<БД>> ...", Toast.LENGTH_SHORT).show();
        }


        if (tinydb.getListObject("today", Quotation.class).isEmpty()) {
            rts = new RequestTaskInner();
            rts.execute(requestToday);
        }
        if (tinydb.getListObject("yesterday", Quotation.class).isEmpty()) {
            rts = new RequestTaskInner();
            rts.execute(requestYesterday);
        }


    }


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        MenuItem mi = menu.findItem(R.id.settings);
        mi.setIcon(R.drawable.ic_settings_grey_24dp);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.settings:

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

                Toast.makeText(this, "НАСТРОЙКИ приложения", Toast.LENGTH_SHORT).show();

                this.finish();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }


    //показываем только выбранные курсы валют
    public List<Quotation> showCheckedQuotations(List<Quotation> quotations) {

        List<Quotation> checkedQuotations = new ArrayList<>();
        for (Quotation q : quotations) {
            if (storage.hasProperty(q.getAbbreviation())) {
                q.setPosition(storage.getProperty(q.getAbbreviation()));
            }
            if (q.getPosition() > 0) {
                checkedQuotations.add(q);
            }
        }
        return checkedQuotations;
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (!tinydb.getListObject("today", Quotation.class).isEmpty()
                && !tinydb.getListObject("yesterday", Quotation.class).isEmpty()
                && tinydb.getListObject("today", Quotation.class).get(0).getDate().equals(date.getDateString())
                && tinydb.getListObject("yesterday", Quotation.class).get(0).getDate().equals(date.getPreviousDateString())
                ) {
            quotationsToday = tinydb.getListObject("today", Quotation.class);
            quotationsYesterday = tinydb.getListObject("yesterday", Quotation.class);

            Date1textView.setText(tinydb.getListObject("yesterday", Quotation.class).get(0).getDate());
            Date2textView.setText(tinydb.getListObject("today", Quotation.class).get(0).getDate());

            Toast.makeText(getBaseContext(), "курсы из <<БД>>", Toast.LENGTH_SHORT).show();
        }

        if (!tinydb.getListObject("today", Quotation.class).isEmpty()
                && !tinydb.getListObject("yesterday", Quotation.class).isEmpty()
                ) {
            if (!tinydb.getListObject("yesterday", Quotation.class).get(0).getDate().equals(date.getPreviousDateString())) {
                if (!tinydb.getListObject("today", Quotation.class).get(0).getDate().equals(date.getPreviousDateString())) {
                    quotationsYesterday = tinydb.getListObject("today", Quotation.class);
                } else {
                    rts = new RequestTaskInner();
                    rts.execute(requestYesterday);
                }
            }
            if (!tinydb.getListObject("today", Quotation.class).get(0).getDate().equals(date.getDateString())) {
                rts = new RequestTaskInner();
                rts.execute(requestToday);
            }

        }


        if (!tinydb.getListObject("tomorrow", Quotation.class).isEmpty()
                && !tinydb.getListObject("today", Quotation.class).isEmpty()
                && tinydb.getListObject("today", Quotation.class).get(0).getDate().equals(date.getDateString())
                && tinydb.getListObject("tomorrow", Quotation.class).get(0).getDate().equals(date.getNextDateString())
                ) {
            quotationsToday = tinydb.getListObject("tomorrow", Quotation.class);
            quotationsYesterday = tinydb.getListObject("today", Quotation.class);

            Date1textView.setText(tinydb.getListObject("today", Quotation.class).get(0).getDate());
            Date2textView.setText(tinydb.getListObject("tomorrow", Quotation.class).get(0).getDate());

            Toast.makeText(getBaseContext(), "УРА! есть курсы на сегодня-завтра", Toast.LENGTH_SHORT).show();
        }

        if (tinydb.getListObject("tomorrow", Quotation.class).isEmpty()) {
            rts = new RequestTaskInner();
            rts.execute(requestTomorrow);
        } else {
            if (!tinydb.getListObject("tomorrow", Quotation.class).get(0).getDate().equals(date.getNextDateString())) {
                rts = new RequestTaskInner();
                rts.execute(requestTomorrow);
            }
        }


        adapter = new RVAdapter((ArrayList<Quotation>) showCheckedQuotations(quotationsYesterday),
                (ArrayList<Quotation>) showCheckedQuotations(quotationsToday));
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

    }
}
