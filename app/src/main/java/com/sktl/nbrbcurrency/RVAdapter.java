package com.sktl.nbrbcurrency;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//public class RVAdapter extends RecyclerView.Adapter<RVViewHolder> implements ItemTouchHelperAdapter {
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
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View view = layoutInflater.inflate(layoutId, viewGroup, false);
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
//            myViewHolder.textViewRateNext.setText(String.valueOf(mArrayList1.get(position).getPosition()));
        }

//        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // action on click
//                Quotation tempQuotation = mArrayList.get(position);
//                Intent intent = new Intent(mContext, SettingsActivity.class);
//                intent.putExtra("image", tempQuotation.getId());
//                intent.putExtra("idComics", tempQuotation.getAbbreviation());
//
//                mContext.startActivity(intent);
//            }
//        });


    }


    @Override
    public int getItemCount() {
        if (numberItems1 == numberItems2) {
            return numberItems1;
        } else {
            return 1;
        }

    }


    //переопределяем методы для перемещения

    /**
     * если position положительный, значит мы его показываем,
     * в противном случае - не показываем
     */

    /*
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


    */
}
