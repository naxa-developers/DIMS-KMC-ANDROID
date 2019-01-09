package np.com.naxa.iset.drr_dictionary.data_glossary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import np.com.naxa.iset.R;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> implements Filterable {

    private final Context mContext;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds

    private boolean isFiltered = false;

    private List<WordsWithDetailsModel> mData;
    //    List<WordsWithDetailsModel> wordsWithDetailsList;
    private List<WordsWithDetailsModel> wordListFiltered;

    public void add(List<WordsWithDetailsModel> s, int position) {
        position = position == -1 ? getItemCount() : position;
        if (isFiltered) {
            wordListFiltered.add(position, s.get(position));
            notifyItemInserted(position);
        } else {
            mData.add(position, s.get(position));
            notifyItemInserted(position);
        }
    }

    public void remove(int position) {
        if (position < getItemCount()) {
            if (isFiltered) {
                wordListFiltered.remove(position);
                notifyItemRemoved(position);
            } else {
                mData.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                isFiltered = true;

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
//                    wordListFiltered = wordsWithDetailsList;
                    wordListFiltered = mData;
                } else {
                    List<WordsWithDetailsModel> filteredList = new ArrayList<>();
//                    for (WordsWithDetailsModel row : wordsWithDetailsList) {
                    for (WordsWithDetailsModel row : mData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
//                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    wordListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = wordListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                wordListFiltered = (ArrayList<WordsWithDetailsModel>) filterResults.values;
                wordListFiltered = (ArrayList<WordsWithDetailsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.section_text_child);
        }
    }

    public SimpleAdapter(Context context, List<WordsWithDetailsModel> wordsWithDetailsList) {
        mContext = context;
        if (wordsWithDetailsList != null)
            mData = new ArrayList<WordsWithDetailsModel>(wordsWithDetailsList);
        else mData = new ArrayList<WordsWithDetailsModel>();
//        this.wordsWithDetailsList = wordsWithDetailsList ;

    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.section_recycler_child, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
//        holder.title.setText(mData.get(position).getTitle().trim());
        if (isFiltered) {

            holder.title.setText(wordListFiltered.get(position).getTitle());
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(mContext,"Position ="+position,Toast.LENGTH_SHORT).show();
//                view.getContext().startActivity(new Intent(view.getContext(),asabani_cat.class));
                    Intent intent = new Intent(view.getContext(), DataGlossaryWordDetailsActivity.class);
//                intent.putExtra("wordsWithDetails", wordsWithDetailsList.get(position));
                    intent.putExtra("wordsWithDetails", wordListFiltered.get(position));
                    view.getContext().startActivity(intent);
                }
            });
        } else {
            holder.title.setText(mData.get(position).getTitle());
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(mContext,"Position ="+position,Toast.LENGTH_SHORT).show();
//                view.getContext().startActivity(new Intent(view.getContext(),asabani_cat.class));
                    Intent intent = new Intent(view.getContext(), DataGlossaryWordDetailsActivity.class);
//                intent.putExtra("wordsWithDetails", wordsWithDetailsList.get(position));
                    intent.putExtra("wordsWithDetails", mData.get(position));
                    view.getContext().startActivity(intent);
                }
            });
        }
        setScaleAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        if (isFiltered) {
            if(wordListFiltered != null) {
                return wordListFiltered.size();
            }else {
                return  0;
            }
        } else {
            return mData.size();

        }
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}