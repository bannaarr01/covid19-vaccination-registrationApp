package Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19vaccination.DetailsActivity;
import com.example.covid_19vaccination.R;

import java.util.List;

import Model.ListItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    int images[];
    private Context context;
    private List<ListItem> listitems; //ListItem from the Model

    public MyAdapter(Context context, List listitem, int[] img) {
        this.context = context;
        this.listitems = listitem;
        images = img;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        ListItem item = listitems.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.imgLogo.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    //Hold all the items we have in the List Row
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;
        ImageView imgLogo;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);


            name = itemView.findViewById(R.id.location_name);
            description = itemView.findViewById(R.id.description);
            imgLogo = itemView.findViewById(R.id.imgVaccine);
        }

        @Override
        public void onClick(View v) {
            //Get d position of the row
            int position = getAdapterPosition();
            ListItem item = listitems.get(position);

            Intent intent = new Intent(context, DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", item.getName());
            bundle.putString("description", item.getDescription());
            intent.putExtras(bundle);
            Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        }
    }
}
