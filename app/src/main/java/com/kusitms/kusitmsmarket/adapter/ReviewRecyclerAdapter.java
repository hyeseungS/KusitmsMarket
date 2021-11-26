package com.kusitms.kusitmsmarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.model.ReviewItem;
import com.kusitms.kusitmsmarket.ui.market.FragmentEditDialog;
import com.kusitms.kusitmsmarket.ui.market.FragmentReportDialog;
import com.kusitms.kusitmsmarket.ui.market.FragmentReviewDialog;
import com.kusitms.kusitmsmarket.ui.market.MarketDetailFragment;
import com.kusitms.kusitmsmarket.ui.market.ReviewActivity;

import java.util.ArrayList;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder> {

    private ArrayList<ReviewItem> mReviewList;
    private Context context;

    @NonNull
    @Override
    public ReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mReviewList.get(position));
    }

    public void setFriendList(ArrayList<ReviewItem> list){
        this.mReviewList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView score;
        TextView content;
        ImageButton reportBtn;
        String userName = "";
        RatingBar personalRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.userName);
            score = (TextView) itemView.findViewById(R.id.personalScore);
            content = (TextView) itemView.findViewById(R.id.reviewContent);
            reportBtn = (ImageButton) itemView.findViewById(R.id.report_button);

            reportBtn.setOnClickListener(this);

            personalRating = (RatingBar) itemView.findViewById(R.id.personalRating);
        }

        void onBind(ReviewItem item){

            if(item.getName() != null) {
                name.setText(item.getName());
                userName = item.getName();
            }
            score.setText("("+String.format("%.1f", item.getScore())+")");
            personalRating.setRating((float) item.getScore());
            content.setText(item.getContent());

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.report_button:
                    FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentReportDialog.setUserName(userName);
                    FragmentReportDialog.setReviewContent(content.getText().toString());
                    FragmentReportDialog dialogFragment = new FragmentReportDialog();
                    dialogFragment.show(fm, "show_report_dialog");
                    break;

            }
        }

    }
}

