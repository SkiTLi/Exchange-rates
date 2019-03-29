package com.sktl.nbrbcurrency;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.sktl.nbrbcurrency.Util.getResponseFromURL;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "sssSA";
    private URL request;
    private TextView textView;
    private RecyclerView recyclerView;
    private SettingsRVAdapter adapter;
    private List<Quotation> quotations;
    private LinearLayoutManager layoutManager;
    private PersistentStorage storage;

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
            quotations = parser.getRates(strResponse);


            adapter = new SettingsRVAdapter((ArrayList<Quotation>) quotations);
            recyclerView.setAdapter(adapter);
            layoutManager = new LinearLayoutManager(getBaseContext());
            recyclerView.setLayoutManager(layoutManager);

            //the adding swipe/drop
            ItemTouchHelper.Callback callback = new TouchHelper(adapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        MenuItem mi = menu.findItem(R.id.settings);
        mi.setIcon(R.drawable.ic_done_grey_24dp);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storage.init(this);

        Util util = new Util();
        request = util.generateURL();
        recyclerView = findViewById(R.id.rv_first);
        quotations = new ArrayList<>();

        //        we create request and execute it
        RequestTaskInner rts = new RequestTaskInner();
        rts.execute(request);


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

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);

                Log.d(TAG, "Settings set");


                this.finish();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
