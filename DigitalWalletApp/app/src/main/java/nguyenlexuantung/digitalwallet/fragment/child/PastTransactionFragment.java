package nguyenlexuantung.digitalwallet.fragment.child;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.adapter.TransactionCollectionAdapter;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.Transaction_;


public class PastTransactionFragment extends Fragment {
    View myFragment;

    @SuppressLint("StaticFieldLeak")
    private static ListView pastListView;
    @SuppressLint("StaticFieldLeak")
    private static Activity pastTransactionContext;
    @SuppressLint("StaticFieldLeak")
    public static TransactionCollectionAdapter transactionCollectionAdapter;

    public static DatabaseHelper databasePastHelper;
    public static List<Transaction_> listTransaction;
    private static List<Transaction_> listPastTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pastTransactionContext = getActivity();
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_present_transaction, container, false);
        pastListView = myFragment.findViewById(R.id.lv_present_transaction);

        databasePastHelper = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, 1);
        listTransaction = new ArrayList<>();
        listTransaction = databasePastHelper.getAllTransaction();

        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

        listPastTransaction = new ArrayList<>();
        for (int i = 0; i < listTransaction.size(); i++) {
            if (!listTransaction.get(i).getDate_transaction().equals(formattedToday)) {
                listPastTransaction.add(listTransaction.get(i));
            }
        }

        transactionCollectionAdapter = new TransactionCollectionAdapter(pastTransactionContext, R.layout.my_transaction_item_layout, listPastTransaction);
        pastListView.setAdapter(transactionCollectionAdapter);

        return myFragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        listTransaction.clear();
        listTransaction = databasePastHelper.getAllTransaction();

        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

        listPastTransaction.clear();
        for (int i = 0; i < listTransaction.size(); i++) {
            if (!listTransaction.get(i).getDate_transaction().equals(formattedToday)) {
                listPastTransaction.add(listTransaction.get(i));
            }
        }
        transactionCollectionAdapter = new TransactionCollectionAdapter(pastTransactionContext, R.layout.my_transaction_item_layout, listPastTransaction);
        pastListView.setAdapter(transactionCollectionAdapter);
    }

    public static void updatePastTransactionListView() {
        listTransaction.clear();
        listTransaction = databasePastHelper.getAllTransaction();

        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

        listPastTransaction.clear();
        for (int i = 0; i < listTransaction.size(); i++) {
            if (!listTransaction.get(i).getDate_transaction().equals(formattedToday)) {
                listPastTransaction.add(listTransaction.get(i));
            }
        }

        transactionCollectionAdapter = new TransactionCollectionAdapter(pastTransactionContext, R.layout.my_transaction_item_layout, listPastTransaction);
        pastListView.setAdapter(transactionCollectionAdapter);
        transactionCollectionAdapter.notifyDataSetChanged();
    }
}