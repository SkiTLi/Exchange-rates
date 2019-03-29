package com.sktl.nbrbcurrency;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RVViewHolder extends RecyclerView.ViewHolder {

    TextView textViewName;
    TextView textViewAbr;
    TextView textViewId;
    LinearLayout linearLayout;
    TextView textViewScale;
    TextView textViewRate;
    TextView textViewRateNext;

    public RVViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewAbr = itemView.findViewById(R.id.tv_shortName);
        textViewName = itemView.findViewById(R.id.tv_fullName);
        textViewRate = itemView.findViewById(R.id.tv_firstValue);
        textViewRateNext = itemView.findViewById(R.id.tv_secondValue);
        textViewScale = itemView.findViewById(R.id.tv_scale);
    }
}
