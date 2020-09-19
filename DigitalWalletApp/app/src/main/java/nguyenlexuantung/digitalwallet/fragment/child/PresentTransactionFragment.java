package nguyenlexuantung.digitalwallet.fragment.child;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.activity.InitActivity;
import nguyenlexuantung.digitalwallet.adapter.TransactionCollectionAdapter;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.Transaction_;

/**
 * Represents PresentListView TransactionFragment
 *
 * @author Tung Nguyen
 */
public class PresentTransactionFragment extends Fragment {
    View myFragment;


    @SuppressLint("StaticFieldLeak")
    private static ListView presentListView;
    @SuppressLint("StaticFieldLeak")
    private static Activity presentFragmentContext;
    @SuppressLint("StaticFieldLeak")
    private static TransactionCollectionAdapter transactionCollectionAdapter;

    public static DatabaseHelper databasePresentHelper;
    private static List<Transaction_> listTransaction;
    private static List<Transaction_> listPresentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presentFragmentContext = getActivity();


        myFragment = inflater.inflate(R.layout.fragment_present_transaction, container, false);
        databasePresentHelper = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, 1);

        presentListView = myFragment.findViewById(R.id.lv_present_transaction);

        listTransaction = new ArrayList<>();
        listTransaction = databasePresentHelper.getAllTransaction();


        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        listPresentTransaction = new ArrayList<>();
        for (int i = 0; i < listTransaction.size(); i++) {
            if (listTransaction.get(i).getDate_transaction().equals(formattedToday)) {
                listPresentTransaction.add(listTransaction.get(i));
            }
        }

        transactionCollectionAdapter = new TransactionCollectionAdapter(presentFragmentContext, R.layout.my_transaction_item_layout, listPresentTransaction);
        presentListView.setAdapter(transactionCollectionAdapter);

        return myFragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        listTransaction.clear();
        listTransaction = databasePresentHelper.getAllTransaction();

        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

        listPresentTransaction.clear();
        for (int i = 0; i < listTransaction.size(); i++) {
            if (listTransaction.get(i).getDate_transaction().equals(formattedToday)) {
                listPresentTransaction.add(listTransaction.get(i));
            }
        }

        transactionCollectionAdapter = new TransactionCollectionAdapter(presentFragmentContext, R.layout.my_transaction_item_layout, listPresentTransaction);
        presentListView.setAdapter(transactionCollectionAdapter);
    }

    public static void updatePresentTransactionListView() {
        listTransaction.clear();
        listTransaction = databasePresentHelper.getAllTransaction();

        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

        listPresentTransaction.clear();
        for (int i = 0; i < listTransaction.size(); i++) {
            if (listTransaction.get(i).getDate_transaction().equals(formattedToday)) {
                listPresentTransaction.add(listTransaction.get(i));
            }
        }
        transactionCollectionAdapter = new TransactionCollectionAdapter(presentFragmentContext, R.layout.my_transaction_item_layout, listPresentTransaction);
        presentListView.setAdapter(transactionCollectionAdapter);
        transactionCollectionAdapter.notifyDataSetChanged();
    }
}