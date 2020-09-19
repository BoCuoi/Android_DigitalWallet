package nguyenlexuantung.digitalwallet.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.model.Transaction_;

/**
 * @items for original list passed from activity code.
 * @tempItems for closing original one and do compare with editable character from user
 * @suggestions for actual filter dropdown values
 */

public class AutoSearchTransactionCollectionAdapter extends ArrayAdapter<Transaction_> {

    Activity context;
    int resourceId;
    ArrayList<Transaction_> items, tempItems, suggestions;

    public AutoSearchTransactionCollectionAdapter(@NonNull Activity context, int resourceId, @NonNull ArrayList<Transaction_> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        this.tempItems = new ArrayList<>(items);
        this.suggestions = new ArrayList<>();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(resourceId, parent, false);
        try {
            if (items.size() > 0 && position >= 0) {
                Transaction_ transaction_ = items.get(position);
                TextView tvTransactionName = convertView.findViewById(R.id.tv_label_transaction_search);
                TextView tvTransactionMoney = convertView.findViewById(R.id.tv_money_transaction_search);
                TextView tvTranasctionDate = convertView.findViewById(R.id.tv_date_transaction_search);

                tvTransactionName.setText(transaction_.getName_transaction());
                tvTransactionMoney.setText(transaction_.getMoney_transaction()+"");
                tvTranasctionDate.setText(transaction_.getDate_transaction());
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return convertView;
    }

    @Nullable
    @Override
    public Transaction_ getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return transactionFilter;
    }

    private Filter transactionFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Transaction_ transaction_ = (Transaction_) resultValue;
            return transaction_.getName_transaction();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Transaction_ transaction_ : tempItems) {
                    if (transaction_.getName_transaction().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(transaction_);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Transaction_> tempValues = (ArrayList<Transaction_>) filterResults.values;
            if (filterResults.count > 0) {
                clear();
                for (Transaction_ transactionObj : tempValues) {
                    add(transactionObj);
                }
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
