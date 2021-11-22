package com.kusitms.kusitmsmarket.ui.market;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {
//    private Drawable mDivider;

    private final int divHeight;

//    public DividerItemDecorator(Drawable divider) { mDivider = divider; }
    public DividerItemDecorator(int divHeight) { this.divHeight = divHeight; }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)

            outRect.bottom = divHeight;

    }

//    @Override
//    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
//        int dividerLeft = parent.getPaddingLeft();
//        int dividerRight = parent.getWidth() - parent.getPaddingRight();
//        int childCount = parent.getChildCount();
//        for(int i=0;i<=childCount-2; i++){
//            View child = parent.getChildAt(i);
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
//            int dividerTop = child.getBottom()+params.bottomMargin;
//            int dividerBottom = dividerTop+mDivider.getIntrinsicHeight();
//            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
//            mDivider.draw(canvas);
//        }
//    }
}

