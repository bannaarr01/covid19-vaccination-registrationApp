package Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19vaccination.R;

import java.util.List;

import Model.ListLocation;

public class LocAdapter extends RecyclerView.Adapter<LocAdapter.ViewHolder> {
    private Context mContext;
    private List<ListLocation> mListLocations;

    public LocAdapter(Context context, List<ListLocation> listLocations) {
        mContext = context;
        mListLocations = listLocations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_location, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocAdapter.ViewHolder holder, int position) {
        ListLocation mLocation = mListLocations.get(position);
        holder.locationTv.setText(mLocation.getLocationName());
        //set the name of the location to the list Row
    }

    @Override
    public int getItemCount() {
        return mListLocations.size();
    }

    //Hold all the items we have in the Row
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView locationTv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            locationTv = itemView.findViewById(R.id.location_name);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ListLocation location = mListLocations.get(position);

            Uri addressUri = Uri.parse("geo:0,0?q=" + location.getLocationName());
            Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "Can't handle this intent!");
            }
        }
    }
}