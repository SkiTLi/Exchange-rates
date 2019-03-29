package com.sktl.nbrbcurrency;

public interface ItemTouchHelperAdapter {
    boolean onItemMove (int fromPosition, int toPosition);
    void onItemDismiss (int position);

}
