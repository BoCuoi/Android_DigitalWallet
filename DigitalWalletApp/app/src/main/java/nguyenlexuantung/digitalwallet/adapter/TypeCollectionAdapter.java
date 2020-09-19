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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.activity.UpdateActivity;
import nguyenlexuantung.digitalwallet.fragment.main.OverviewFragment;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.TransactionType;

public class TypeCollectionAdapter extends ArrayAdapter<TransactionType> {
    Activity context;
    ArrayList<TransactionType> transactionTypeList = null;
    int layoutID;

    public TypeCollectionAdapter(Activity context, int textViewResourceId, ArrayList<TransactionType> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutID = textViewResourceId;
        this.transactionTypeList = objects;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(layoutID, null);
        final DatabaseHelper databaseAdapterHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, 1);
        if (transactionTypeList.size() > 0 && position >= 0) {
            final TransactionType transactionType = transactionTypeList.get(position);
            TextView tvType = convertView.findViewById(R.id.tv_item_type);
            tvType.setText(transactionType.getType_name());

            /// Using ImageView instead ImageButton
            ImageView ibtnDelete = convertView.findViewById(R.id.igb_item_type_delete);
            ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("CONFIRM DIALOG!!!");
                    builder.setMessage("ARE YOU SURE ABOUT THAT ???");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean status = databaseAdapterHelper.deleteTransactionType(transactionType.getType_id());
                            OverviewFragment.updateTypeListView();
                            if (status) {
//                                OverviewFragment.updateChart();
                                Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
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
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
