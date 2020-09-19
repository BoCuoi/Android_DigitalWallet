package nguyenlexuantung.digitalwallet.fragment.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.activity.InitActivity;
import nguyenlexuantung.digitalwallet.activity.UpdateActivity;
import nguyenlexuantung.digitalwallet.adapter.AutoSearchTransactionCollectionAdapter;
import nguyenlexuantung.digitalwallet.adapter.FragmentCollectionAdapter;
import nguyenlexuantung.digitalwallet.fragment.child.PastTransactionFragment;
import nguyenlexuantung.digitalwallet.fragment.child.PresentTransactionFragment;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.Transaction_;

public class HomeFragment extends Fragment {

    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton floatingActionButton;
    AppCompatAutoCompleteTextView autoTextViewCustom;
    Activity HomeContext;

    private DatabaseHelper databaseHomeHelper;
    private ArrayList<Transaction_> transactionArrayList;
    private AutoSearchTransactionCollectionAdapter autoSearchCollectionAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance() {

        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        HomeContext = getActivity();
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        autoTextViewCustom = myFragment.findViewById(R.id.autoCTV);

        floatingActionButton = myFragment.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InitActivity.class);
                startActivity(intent);
            }
        });

        databaseHomeHelper = new DatabaseHelper(HomeContext, DatabaseHelper.DATABASE_NAME, null, 1);
        transactionArrayList = databaseHomeHelper.getAllTransactionForSearch();
        autoSearchCollectionAdapter = new AutoSearchTransactionCollectionAdapter(HomeContext, R.layout.search_auto_completion, transactionArrayList);
        autoTextViewCustom.setThreshold(0); // 0 is showing all items has same letter
        autoTextViewCustom.setAdapter(autoSearchCollectionAdapter); //setting the adapter data into the AutoCompleteTextView

        autoTextViewCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Transaction_ currentSearchItem;
                    currentSearchItem = (Transaction_) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(HomeContext, UpdateActivity.class);
                    intent.putExtra("buttonVisible",true);
                    intent.putExtra("TransactionID", currentSearchItem.getId_transaction());
                    intent.putExtra("TransactionName", currentSearchItem.getName_transaction());
                    intent.putExtra("TransactionMoney", currentSearchItem.getMoney_transaction());
                    intent.putExtra("TransactionDate", currentSearchItem.getDate_transaction());
                    intent.putExtra("TypeID", currentSearchItem.getType_id());
                    HomeContext.startActivityForResult(intent, 12345);

                } catch (Exception e) {
                    Toast.makeText(HomeContext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return myFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        transactionArrayList = databaseHomeHelper.getAllTransactionForSearch();
        autoSearchCollectionAdapter = new AutoSearchTransactionCollectionAdapter(HomeContext, R.layout.search_auto_completion, transactionArrayList);
        autoTextViewCustom.setThreshold(0); // 0 is showing all items has same letter
        autoTextViewCustom.setAdapter(autoSearchCollectionAdapter); //setting the adapter data into the AutoCompleteTextView

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        FragmentCollectionAdapter adapter = new FragmentCollectionAdapter(getChildFragmentManager());
        adapter.addFragment(new PastTransactionFragment(), "Past");
        adapter.addFragment(new PresentTransactionFragment(), "Today");
        viewPager.setAdapter(adapter);
    }


}