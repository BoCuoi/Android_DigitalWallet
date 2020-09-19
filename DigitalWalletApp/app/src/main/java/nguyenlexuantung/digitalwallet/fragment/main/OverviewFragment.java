package nguyenlexuantung.digitalwallet.fragment.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;


import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.adapter.SearchViewAdapter;
import nguyenlexuantung.digitalwallet.adapter.TypeCollectionAdapter;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.TransactionType;
import nguyenlexuantung.digitalwallet.model.TransactionTypeStatistics;

public class OverviewFragment extends Fragment {
    View myFragment;
    EditText etTypeName;
    Button btnAddType, btnEditType;
    Integer TypeID;
    private static AnyChartView anyChartView;
    @SuppressLint("StaticFieldLeak")
    private static Activity overviewFragmentContext;
    @SuppressLint("StaticFieldLeak")
    private static TypeCollectionAdapter typeCollectionAdapter;

    @SuppressLint("StaticFieldLeak")
    private static ListView listView;

    public static DatabaseHelper databaseTypeHelper;
    private static ArrayList<TransactionType> transactionTypeList;
    private static ArrayList<TransactionTypeStatistics> transactionTypeStatisticsArrayList;

    public static OverviewFragment getInstance() {
        return new OverviewFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        overviewFragmentContext = getActivity();

        myFragment = inflater.inflate(R.layout.fragment_overview, container, false);


        databaseTypeHelper = new DatabaseHelper(overviewFragmentContext, DatabaseHelper.DATABASE_NAME, null, 1);
        transactionTypeStatisticsArrayList = new ArrayList<>();
        transactionTypeStatisticsArrayList = databaseTypeHelper.getAllTransactionTypeStatistics();

        anyChartView = myFragment.findViewById(R.id.any_chart_view);
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < transactionTypeStatisticsArrayList.size(); i++) {
            data.add(new ValueDataEntry(transactionTypeStatisticsArrayList.get(i).getType_name(), transactionTypeStatisticsArrayList.get(i).getMoney()));
        }
        pie.data(data);
        anyChartView.setChart(pie);


        listView = myFragment.findViewById(R.id.lv_type);
        etTypeName = myFragment.findViewById(R.id.et_TypeName);
        btnAddType = myFragment.findViewById(R.id.btnAddType);
        btnEditType = myFragment.findViewById(R.id.btnEditType);
        btnEditType.setEnabled(false);

        listView = myFragment.findViewById(R.id.lv_type);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TransactionType currentItem = (TransactionType) adapterView.getItemAtPosition(i);
                TypeID = currentItem.getType_id();
                etTypeName.setText(currentItem.getType_name());
                btnEditType.setEnabled(true);
            }
        });


        btnAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTypeName.getText().toString().trim().equalsIgnoreCase("")) {
                    etTypeName.setError("This field can not be blank");
                } else {
                    etTypeName.setError(null);
                    TransactionType transactionType = new TransactionType(
                            0,
                            etTypeName.getText().toString());
                    databaseTypeHelper.addTransactionType(transactionType);
                    updateTypeListView();
                    etTypeName.setText("");
                }
            }
        });

        btnEditType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(overviewFragmentContext);
                builder.setTitle("CONFIRM DIALOG!!!");
                builder.setMessage("ARE YOU SURE ABOUT THAT ???");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TransactionType transactionType = new TransactionType(
                                TypeID,
                                etTypeName.getText().toString());
                        boolean status = databaseTypeHelper.updateTransactionType(transactionType);
                        if (status) {
                            Toast.makeText(overviewFragmentContext, "UPDATED", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(overviewFragmentContext, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                        updateTypeListView();
                        btnEditType.setEnabled(false);
                        etTypeName.setText("");
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
        databaseTypeHelper = new DatabaseHelper(overviewFragmentContext, DatabaseHelper.DATABASE_NAME, null, 1);
        transactionTypeList = new ArrayList<>();
        transactionTypeList = databaseTypeHelper.getAllTransactionType();

        typeCollectionAdapter = new TypeCollectionAdapter(overviewFragmentContext, R.layout.my_type_item_layout, (ArrayList<TransactionType>) transactionTypeList);
        listView.setAdapter(typeCollectionAdapter);


        return myFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        transactionTypeStatisticsArrayList.clear();
        transactionTypeStatisticsArrayList = databaseTypeHelper.getAllTransactionTypeStatistics();

        AnyChartView anyChartView = myFragment.findViewById(R.id.any_chart_view);
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < transactionTypeStatisticsArrayList.size(); i++) {
            data.add(new ValueDataEntry(transactionTypeStatisticsArrayList.get(i).getType_name(), transactionTypeStatisticsArrayList.get(i).getMoney()));
        }
        pie.data(data);
        anyChartView.setChart(pie);

    }

    public static void updateTypeListView() {
        transactionTypeList.clear();
        transactionTypeList = databaseTypeHelper.getAllTransactionType();

        typeCollectionAdapter = new TypeCollectionAdapter(overviewFragmentContext, R.layout.my_type_item_layout, transactionTypeList);
        listView.setAdapter(typeCollectionAdapter);
        typeCollectionAdapter.notifyDataSetChanged();
    }

//    public static void updateChart(){
//        transactionTypeStatisticsArrayList.clear();
//        transactionTypeStatisticsArrayList = databaseTypeHelper.getAllTransactionTypeStatistics();
//
//        AnyChartView anyChartView = overviewFragmentContext.findViewById(R.id.any_chart_view);
//        Pie pie = AnyChart.pie();
//
//        List<DataEntry> data = new ArrayList<>();
//        for (int i = 0; i < transactionTypeStatisticsArrayList.size(); i++) {
//            data.add(new ValueDataEntry(transactionTypeStatisticsArrayList.get(i).getType_name(), transactionTypeStatisticsArrayList.get(i).getMoney()));
//        }
//        pie.data(data);
//        anyChartView.setChart(pie);
//    }


}