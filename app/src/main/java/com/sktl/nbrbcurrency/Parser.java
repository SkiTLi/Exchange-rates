package com.sktl.nbrbcurrency;

import android.content.Context;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {
    private static final String TAG = "sssP";
    Context mContext;
    String str;
    String result = ""; //отчет
    List<Quotation> quotations;

    private PersistantStorage storage;

    public Parser(Context context, String str) {
        this.mContext = context;
        this.str = str;

    }


    public List<Quotation> getRates(String str) {


        quotations = new ArrayList<>();
        Quotation quotation;


        try {


            JSONArray results = new JSONArray(str);
            if (results.length() <= 0) {
                result = (result.concat("-=-").concat("noResults"));
            } else {

                for (int i = 1; i < results.length(); i++) {
                    quotation = new Quotation();
                    JSONObject resultsObject = results.getJSONObject(i);

                    quotation.setId(resultsObject.getString("Cur_ID"));
                    quotation.setDate(resultsObject.getString("Date"));
                    quotation.setDate(quotation.getDate().split("T", 2)[0]);//leave only date (without time)
                    quotation.setAbbreviation(resultsObject.getString("Cur_Abbreviation"));
                    quotation.setName(resultsObject.getString("Cur_Name"));
                    quotation.setScale(resultsObject.getString("Cur_Scale"));
                    quotation.setRate(resultsObject.getString("Cur_OfficialRate"));
                    /**
                     * если position положительный, значит мы его показываем,
                     * в противном случае - не показываем
                     */
                    quotation.setPosition(i * (-1));
                    if (resultsObject.getString("Cur_Abbreviation").equals("USD")
                            || resultsObject.getString("Cur_Abbreviation").equals("RUB")
                            || resultsObject.getString("Cur_Abbreviation").equals("EUR")) {
                        quotation.setChosen(true);
                        quotation.setPosition(quotation.getPosition() * (-1));
                    }

                    if (!storage.hasProperty("visited")) {
                        storage.addProperty("visited", -8);
                    }

                    if (storage.getProperty("visited") < 0) {

                        Log.d(TAG, "!storage.isVisited() = true");

                        storage.addProperty(quotation.getAbbreviation(), quotation.getPosition());

                        Log.d(TAG, quotation.getAbbreviation()
                                + " => "
                                + storage.getProperty(quotation.getId()));

                    } else {
                        if (storage.hasProperty(quotation.getAbbreviation())) {
                            quotation.setPosition(storage.getProperty(quotation.getAbbreviation()));
                        }
                    }
                    quotations.add(quotation);
                }
                //поменяй знак на - для обнуления настроек
                storage.addProperty("visited", 1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }


        Collections.sort(quotations, Quotation.COMPARE_BY_POSITION);

        return quotations;


    }


}
