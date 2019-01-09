package np.com.naxa.iset.emergencynumbers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import np.com.naxa.iset.R;
import np.com.naxa.iset.utils.recycleviewutils.LinearLayoutManagerWithSmoothScroller;
import np.com.naxa.iset.utils.recycleviewutils.RecyclerViewType;

/**
 * Created by samir on 01/12/18..
 */

public class SectionRecyclerViewEmergencyNoAdapter extends RecyclerView.Adapter<SectionRecyclerViewEmergencyNoAdapter.SectionViewHolder> {


    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionLabel;
        private ToggleButton showAllButton;
        private RecyclerView itemRecyclerView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionLabel = (TextView) itemView.findViewById(R.id.section_label);
            showAllButton = (ToggleButton) itemView.findViewById(R.id.section_show_all_button);
            itemRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_recycler_view);
            itemRecyclerView.setVisibility(View.GONE);
            showAllButton.setBackgroundDrawable(showAllButton.getContext().getResources().getDrawable( R.drawable.ic_add_circle_outlline_purple_24dp ));
            itemRecyclerView.setVisibility(View.GONE);
        }
    }

    private Context context;
    private RecyclerViewType recyclerViewType;
    private ArrayList<SectionEmergencyNoModel> sectionEmergencyNoModelArrayList;

    public SectionRecyclerViewEmergencyNoAdapter(Context context, RecyclerViewType recyclerViewType, ArrayList<SectionEmergencyNoModel> sectionEmergencyNoModelArrayList) {
        this.context = context;
        this.recyclerViewType = recyclerViewType;
        this.sectionEmergencyNoModelArrayList = sectionEmergencyNoModelArrayList;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_custom_row_emergencyno_layout, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        final SectionEmergencyNoModel sectionEmergencyNoModel =
                sectionEmergencyNoModelArrayList.get(position);

        holder.sectionLabel.setText(sectionEmergencyNoModel.getSectionLabel());

        //recycler view for items
        holder.itemRecyclerView.setHasFixedSize(true);
        holder.itemRecyclerView.setNestedScrollingEnabled(false);

        /* set layout manager on basis of recyclerview enum type */
        switch (recyclerViewType) {
            case LINEAR_VERTICAL:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(context, LinearLayoutManager.VERTICAL, false);
                holder.itemRecyclerView.setLayoutManager(linearLayoutManager);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//                holder.itemRecyclerView.setLayoutManager(linearLayoutManager);
                break;
            case LINEAR_HORIZONTAL:
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                holder.itemRecyclerView.setLayoutManager(linearLayoutManager1);
                break;
            case GRID:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
                holder.itemRecyclerView.setLayoutManager(gridLayoutManager);
                break;
        }
        ItemRecyclerViewEmergencyNoAdapter adapter = new ItemRecyclerViewEmergencyNoAdapter(context, sectionEmergencyNoModel.getItemArrayList());
        holder.itemRecyclerView.setAdapter(adapter);



        //show toast on click of show all button
        holder.showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();

                if (on) {
                    // Enable
                    holder.showAllButton.setBackgroundDrawable(holder.showAllButton.getContext().getResources().getDrawable( R.drawable.ic_minus_circle_outer_purple_24dp ));
                    holder.itemRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    // Disable
                    holder.showAllButton.setBackgroundDrawable(holder.showAllButton.getContext().getResources().getDrawable( R.drawable.ic_add_circle_outlline_purple_24dp ));
                    holder.itemRecyclerView.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sectionEmergencyNoModelArrayList.size();
    }


}
