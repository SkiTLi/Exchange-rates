package com.sktl.nbrbcurrency;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVViewHolder> {

    private static final String TAG = "sssMA";
    private Context mContext;
    private PersistantStorage storage;

    private List<Quotation> mArrayList1;
    private int numberItems1;

    private List<Quotation> mArrayList2;
    private int numberItems2;


    public RVAdapter(ArrayList<Quotation> arrayList1, ArrayList<Quotation> arrayList2) {
        storage = new PersistantStorage();

        mArrayList1 = arrayList1;
        numberItems1 = arrayList1.size();

        mArrayList2 = arrayList2;
        numberItems2 = arrayList2.size();
    }


    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context;
        context = viewGroup.getContext();
        int layoutId = R.layout.item_view_main;

        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        RVViewHolder myViewHolder = new RVViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder myViewHolder, int position) {


        //Заполняем поля viewHolder'а данными из элемента набора данных
        if (numberItems1 == numberItems2
                && mArrayList1.get(position).getName().equals(mArrayList2.get(position).getName())
                && mArrayList1.get(position).getAbbreviation().equals(mArrayList2.get(position).getAbbreviation())
                && mArrayList1.get(position).getScale().equals(mArrayList2.get(position).getScale())
                ) {
            myViewHolder.textViewName.setText(mArrayList1.get(position).getName());
            myViewHolder.textViewAbr.setText(mArrayList1.get(position).getAbbreviation());
            myViewHolder.textViewScale.setText(mArrayList1.get(position).getScale());

            myViewHolder.textViewRate.setText(mArrayList1.get(position).getRate());
            myViewHolder.textViewRateNext.setText(mArrayList2.get(position).getRate());

        }



    }


    @Override
    public int getItemCount() {
        if (numberItems1 == numberItems2) {
            return numberItems1;
        } else {
            return 1;
        }

    }



}
