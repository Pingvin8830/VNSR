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
        if (point.getArrivalDateTime().getTime() != 0) { holder.mTxtArrivalDateTime.setText("-> " + point.getArrivalDateTime().toString()); }
        else { holder.mTxtArrivalDateTime.setVisibility(View.INVISIBLE); }
        if (point.getDepartureDateTime().getTime() != 0) { holder.mTxtDepartureDateTime.setText(point.getDepartureDateTime().toString() + " ->"); }
        else { holder.mTxtDepartureDateTime.setVisibility(View.INVISIBLE); }
        holder.mTxtAddress.setText(point.getAddress().getName());
        holder.mTxtDoing.setText(point.getDoingText());
        holder.mTxtOdometer.setText(String.valueOf(point.getOdometer()));
    }

    @Override
    public int getItemCount() {
        return mPoints.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtArrivalDateTime;
        public TextView mTxtDepartureDateTime;
        public TextView mTxtOdometer;
        public TextView mTxtAddress;
        public TextView mTxtDoing;

        public ListItemHolder(View view) {
            super(view);
            mTxtArrivalDateTime = view.findViewById(R.id.txtArrivalDateTime);
            mTxtDepartureDateTime = view.findViewById(R.id.txtDepartureDateTime);
            mTxtOdometer = view.findViewById(R.id.txtOdometer);
            mTxtAddress = view.findViewById(R.id.txtAddress);
            mTxtDoing = view.findViewById(R.id.txtDoing);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
