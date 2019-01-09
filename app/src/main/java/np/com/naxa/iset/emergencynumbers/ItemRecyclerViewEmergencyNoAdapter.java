package np.com.naxa.iset.emergencynumbers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import np.com.naxa.iset.R;
import np.com.naxa.iset.event.EmergenctContactCallEvent;

/**
 * Created by samir on 01/12/18.
 */

public class ItemRecyclerViewEmergencyNoAdapter extends RecyclerView.Adapter<ItemRecyclerViewEmergencyNoAdapter.ItemViewHolder> {

    String TAG = "EmergencyNoAdapter";

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemLabel;
        private CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemLabel = (TextView) itemView.findViewById(R.id.item_label);
            cardView = (CardView) itemView.findViewById(R.id.item_card);
        }
    }

    private Context context;
    private ArrayList<String> arrayList;

    public ItemRecyclerViewEmergencyNoAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_row_emergencyno_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.itemLabel.setText(arrayList.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, arrayList.get(position) + " Clicked", Toast.LENGTH_SHORT).show();
//                startNewActivity(arrayList.get(position));
                EventBus.getDefault().post(new EmergenctContactCallEvent.ContactItemClick(arrayList.get(position)));

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
