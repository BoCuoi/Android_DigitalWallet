package nguyenlexuantung.digitalwallet.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.activity.UpdateActivity;
import nguyenlexuantung.digitalwallet.fragment.child.PastTransactionFragment;
import nguyenlexuantung.digitalwallet.fragment.child.PresentTransactionFragment;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.Transaction_;

public class TransactionCollectionAdapter extends ArrayAdapter<Transaction_> implements Filterable {
    Activity context;
    int layoutID;
    List<Transaction_> listTransaction;

    public TransactionCollectionAdapter(@NonNull Activity context, int textViewResourceId, @NonNull List<Transaction_> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutID = textViewResourceId;
        this.listTransaction = objects;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(layoutID, null);

        final Transaction_ currentItem = listTransaction.get(position);
        final DatabaseHelper databaseAdapterHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, 1);

        if (listTransaction.size() > 0 && position >= 0) {

            TextView tvTransactionName = (TextView) convertView.findViewById(R.id.tv_label_transaction);
            TextView tvTransactionMoney = (TextView) convertView.findViewById(R.id.tv_money_transaction);
            TextView tvTranasctionDate = (TextView) convertView.findViewById(R.id.tv_date_transaction);

            tvTransactionName.setText(listTransaction.get(position).getName_transaction());
            tvTransactionMoney.setText(Integer.toString(listTransaction.get(position).getMoney_transaction()));
            tvTranasctionDate.setText(listTransaction.get(position).getDate_transaction());


            ImageButton imageBtnDelete = convertView.findViewById(R.id.igb_delete_transaction);
            ImageButton imageBtnEdit = convertView.findViewById(R.id.igb_edit_transaction);

            imageBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("CONFIRM DIALOG!!!");
                    builder.setMessage("ARE YOU SURE ABOUT THAT ???");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                boolean status = databaseAdapterHelper.deleteTransaction(currentItem.getId_transaction());
                                PresentTransactionFragment.updatePresentTransactionListView();
                                PastTransactionFragment.updatePastTransactionListView();
                                if (status) {
                                    Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            imageBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(context, UpdateActivity.class);
                        intent.putExtra("buttonVisible",false);
                        intent.putExtra("TransactionID", currentItem.getId_transaction());
                        intent.putExtra("TransactionName", currentItem.getName_transaction());
                        intent.putExtra("TransactionMoney", currentItem.getMoney_transaction());
                        intent.putExtra("TransactionDate", currentItem.getDate_transaction());
                        intent.putExtra("TypeID", currentItem.getType_id());
                        context.startActivityForResult(intent, 9999);

                    } catch (Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                FilterResults filterResults = new FilterResults();
//                List<Transaction_> filterArrayList = new ArrayList<>();
//
//                if (charSequence == null || charSequence.length() == 0) {
////                    PastTransactionFragment.updatePastTransactionListView();
//                    filterArrayList.addAll(listTransaction);
////                    filterArrayList.addAll(PastTransactionFragment.updateFilterFull());
//                } else {
//                    for (Transaction_ transaction_ : listTransaction) {
//                        if (transaction_.getName_transaction().toLowerCase().contains(charSequence.toString().toLowerCase())) {
//                            filterArrayList.add(transaction_);
//                        }
//                    }
//                    PastTransactionFragment.updateFilterList(filterArrayList);
//                }
//
//                filterResults.values = filterArrayList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
////                listTransaction.clear();
////                listTransaction.addAll((Collection<? extends Transaction_>) filterResults.values);
//                notifyDataSetChanged();
//            }
//        };
//    }


}