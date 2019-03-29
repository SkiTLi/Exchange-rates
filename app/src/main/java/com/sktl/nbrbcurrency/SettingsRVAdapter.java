package com.sktl.nbrbcurrency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsRVAdapter extends RecyclerView.Adapter<SettingsRVAdapter.SettingsRVViewHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = "sssSRVA";
    private List<Quotation> mArrayList;
    private Context mContext;
    private int numberItems;
    private PersistentStorage storage;


    public SettingsRVAdapter(ArrayList<Quotation> arrayList) {
        mArrayList = arrayList;

        storage = new PersistentStorage();
        numberItems = arrayList.size();
    }


    @NonNull
    @Override
    public SettingsRVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context;
        context = viewGroup.getContext();
        int layoutId = R.layout.item_view_settings;

        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        SettingsRVViewHolder myViewHolder = new SettingsRVViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SettingsRVViewHolder myViewHolder, final int position) {


        //We fill the viewHolder fields with data from the data set item

        myViewHolder.textViewName.setText(mArrayList.get(position).getName());
        myViewHolder.textViewAbr.setText(mArrayList.get(position).getAbbreviation());
        myViewHolder.textViewScale.setText(mArrayList.get(position).getScale());
        if (mArrayList.get(position).getPosition() > 0) {
            myViewHolder.sw.setChecked(true);
        } else {
            myViewHolder.sw.setChecked(false);
        }


        myViewHolder.sw.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // action on click

                storage.addProperty(mArrayList.get(position).getAbbreviation(),
                        storage.getProperty(mArrayList.get(position).getAbbreviation()) * (-1));
            }
        });

    }


    @Override
    public int getItemCount() {
        return numberItems;
    }


    //override methods for moving recyclerView items

    /**
     * if position is positive, then we show it,
     * otherwise - do not show
     */

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        int pos, pos2, sign, sign2, newPos, newPos2;
        if (fromPosition < toPosition) {
            Log.d(TAG, mArrayList.toString());
            for (int i = fromPosition; i < toPosition; i++) {


                pos = mArrayList.get(i).getPosition();
                sign = pos / Math.abs(pos);
                pos2 = mArrayList.get(i + 1).getPosition();
                sign2 = pos2 / Math.abs(pos2);

                if (sign == sign2) {
                    newPos = (Math.abs(pos2)) * sign;
                    newPos2 = (Math.abs(pos)) * sign2;

                    mArrayList.get(i).setPosition(newPos);
                    mArrayList.get(i + 1).setPosition(newPos2);

                    storage.addProperty(mArrayList.get(i).getAbbreviation(), newPos);
                    storage.addProperty(mArrayList.get(i + 1).getAbbreviation(), newPos2);
                }


                Collections.swap(mArrayList, i, i + 1); //меняет местами элементы с индексами
            }
        } else {
            Log.d(TAG, mArrayList.toString());
            for (int i = fromPosition; i > toPosition; i--) {

                pos = mArrayList.get(i).getPosition();
                sign = pos / Math.abs(pos);
                pos2 = mArrayList.get(i - 1).getPosition();
                sign2 = pos2 / Math.abs(pos2);

                if (sign == sign2) {
                    newPos = (Math.abs(pos2)) * sign;
                    newPos2 = (Math.abs(pos)) * sign2;

                    mArrayList.get(i).setPosition(newPos);
                    mArrayList.get(i - 1).setPosition(newPos2);

                    storage.addProperty(mArrayList.get(i).getAbbreviation(), newPos);
                    storage.addProperty(mArrayList.get(i - 1).getAbbreviation(), newPos2);
                }
                Collections.swap(mArrayList, i, i - 1); //меняет местами элементы с индексами
            }
        }
        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class SettingsRVViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewAbr;
        TextView textViewScale;

        Switch sw;
        ImageButton imageButton;

        public SettingsRVViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAbr = itemView.findViewById(R.id.tv_shortName);
            textViewName = itemView.findViewById(R.id.tv_fullName);
            textViewScale = itemView.findViewById(R.id.tv_scale);
            sw = itemView.findViewById(R.id.cheked_s);
            imageButton = itemView.findViewById(R.id.drop_ib);
        }
    }


}
