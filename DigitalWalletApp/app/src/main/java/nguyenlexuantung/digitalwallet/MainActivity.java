package nguyenlexuantung.digitalwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung.digitalwallet.adapter.SearchViewAdapter;
import nguyenlexuantung.digitalwallet.adapter.TransactionCollectionAdapter;
import nguyenlexuantung.digitalwallet.fragment.main.AccountFragment;
import nguyenlexuantung.digitalwallet.fragment.main.HomeFragment;
import nguyenlexuantung.digitalwallet.fragment.main.OverviewFragment;
import nguyenlexuantung.digitalwallet.helper.BottomNavigationViewHelper;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.TransactionType;
import nguyenlexuantung.digitalwallet.model.Transaction_;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public static DatabaseHelper databaseTypeHelper;
    private static ArrayList<TransactionType> transactionTypeList;
    List<Transaction_> listTransaction;
    DatabaseHelper databaseHelper;
    TransactionCollectionAdapter transactionCollectionAdapter;
    @SuppressLint("StaticFieldLeak")
    private static SearchViewAdapter searchViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
//        bottomNavigationView.setItemIconTintList(null);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.action_home:
                        selectedFragment = HomeFragment.getInstance();
                        break;

                    case R.id.action_overview:
                        selectedFragment = OverviewFragment.getInstance();
                        break;

                    case R.id.action_account:
                        selectedFragment = AccountFragment.getInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, selectedFragment);
                transaction.commit();
                return true;
            }
        });
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, HomeFragment.getInstance());
        transaction.commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        databaseTypeHelper = new DatabaseHelper(MainActivity.this, DatabaseHelper.DATABASE_NAME, null, 1);
//
//        transactionTypeList = new ArrayList<>();
//        transactionTypeList = databaseTypeHelper.getAllTransactionType();
//        searchViewAdapter = new SearchViewAdapter(MainActivity.this, R.layout.my_type_item_layout, transactionTypeList);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                try {
//                    searchViewAdapter.getFilter().filter(newText);
////                    searchViewAdapter.notifyDataSetChanged();
//                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });
//        return true;
//    }

}