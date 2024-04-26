package ru.sknt.vlasovnetwork.vnsr.travels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.PointsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ListItemHolder> {
    private final List<Point> mPoints;
    private final PointsFragment mFragment;

    public PointsAdapter(PointsFragment fragment, List<Point> points) {
        mFragment = fragment;
        mPoints = points;
    }

    @NonNull
    @Override
    public PointsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.travels_point_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PointsAdapter.ListItemHolder holder, int position) {
        Point point = mPoints.get(position);
        holder.mTxtDateTime.setText(point.getDateTime().toString());
        holder.mTxtOdometer.setText(String.valueOf(point.getOdometer()));
        holder.mTxtAddress.setText(point.getAddress().getName());
    }

    @Override
    public int getItemCount() {
        return mPoints.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtDateTime;
        public TextView mTxtOdometer;
        public TextView mTxtAddress;

        public ListItemHolder(View view) {
            super(view);
            mTxtDateTime = view.findViewById(R.id.txtDateTime);
            mTxtOdometer = view.findViewById(R.id.txtOdometer);
            mTxtAddress = view.findViewById(R.id.txtAddress);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
