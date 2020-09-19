package nguyenlexuantung.digitalwallet.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung.digitalwallet.MainActivity;
import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.fragment.main.OverviewFragment;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.TransactionType;
import nguyenlexuantung.digitalwallet.model.Transaction_;

public class SearchViewAdapter extends ArrayAdapter<TransactionType> {
    Activity context;
    ArrayList<TransactionType> transactionTypeArrayList, transactionTypeArrayListFull, transactionTypeArrayListActual;
    int layoutID;

    public SearchViewAdapter(@NonNull Activity context, int textViewResourceId, @NonNull ArrayList<TransactionType> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutID = textViewResourceId;
        this.transactionTypeArrayList = objects;
        transactionTypeArrayListFull = new ArrayList<>(transactionTypeArrayList);
        transactionTypeArrayListActual = new ArrayList<>();
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(layoutID, null);

        final DatabaseHelper databaseAdapterHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, 1);
        if (transactionTypeArrayList.size() > 0 && position >= 0) {
            final TransactionType transactionType = transactionTypeArrayList.get(position);

            TextView tvType = convertView.findViewById(R.id.tv_item_type);
            tvType.setText(transactionType.getType_name());


//            ImageView ibtnDelete = convertView.findViewById(R.id.igb_item_type_delete);
//            ibtnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    boolean status = databaseAdapterHelper.deleteTransactionType(transactionType.getType_id());
//                    OverviewFragment.updateTypeListView();
//                    if (status) {
//                        Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
//                    } else
//                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
        return convertView;
    }


    @Nullable
    @Override
    public TransactionType getItem(int position) {
        return transactionTypeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return transactionTypeArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
//            ArrayList<TransactionType> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                return new FilterResults();
//                filteredList.addAll(transactionTypeArrayListFull);
            } else {
                transactionTypeArrayListActual.clear();
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (TransactionType item : transactionTypeArrayListFull) {
                    if (item.getType_name().toLowerCase().contains(filterPattern)) {
                        transactionTypeArrayListActual.add(item);
//                        Toast.makeText(context, item.getType_name(), Toast.LENGTH_SHORT).show();
                    }
                }
                results.values = transactionTypeArrayListActual;
                results.count = transactionTypeArrayListActual.size();
                Toast.makeText(context, results.values.toString(), Toast.LENGTH_SHORT).show();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                transactionTypeArrayList.clear();
                transactionTypeArrayList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
