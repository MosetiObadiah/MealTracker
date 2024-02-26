package com.mealtracker;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

public class FoodRVAdapter extends RecyclerView.Adapter<FoodRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<FoodModal> courseModalArrayList;
    private Context context;

    // constructor
    public FoodRVAdapter(ArrayList<FoodModal> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recordspage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        FoodModal modal = courseModalArrayList.get(position);
        holder.food1View.setText(modal.getFood1());
        holder.food2View.setText(modal.getFood2());
        holder.food3View.setText(modal.getFood3());
        holder.hotelView.setText(modal.getHotel());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView food1View, food2View, food3View, hotelView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            food1View = itemView.findViewById(R.id.food1View);
            food2View = itemView.findViewById(R.id.hotelView);
            food3View = itemView.findViewById(R.id.food3View);
            hotelView = itemView.findViewById(R.id.food2View);
        }
    }
}
