package nguyenlexuantung.digitalwallet.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import nguyenlexuantung.digitalwallet.R;
import nguyenlexuantung.digitalwallet.fragment.child.PastTransactionFragment;
import nguyenlexuantung.digitalwallet.fragment.child.PresentTransactionFragment;
import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.TransactionType;
import nguyenlexuantung.digitalwallet.model.Transaction_;

public class UpdateActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    List<TransactionType> listType;
    Integer transactionID;

    EditText etName, etMoney, etDate;
    Spinner spnType;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etName = (EditText) findViewById(R.id.et_Name);
        etMoney = (EditText) findViewById(R.id.et_Money);
        etDate = (EditText) findViewById(R.id.et_Date);
        spnType = (Spinner) findViewById(R.id.spn_Type);

        btnUpdate = (Button) findViewById(R.id.btn_Edit);
        btnDelete = (Button) findViewById(R.id.btn_Delete);
        btnDelete.setVisibility(View.INVISIBLE);

        databaseHelper = new DatabaseHelper(UpdateActivity.this, DatabaseHelper.DATABASE_NAME, null, 1);

        listType = new ArrayList<>();
        listType = databaseHelper.getAllTransactionType();
        ArrayAdapter<TransactionType> spinnerAdapterType = new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, listType);
        spinnerAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnType.setAdapter(spinnerAdapterType);

        if (getIntent().getBooleanExtra("buttonVisible", true)) {
            btnDelete.setVisibility(View.VISIBLE);
        }

        transactionID = getIntent().getIntExtra("TransactionID", -1);
        String name_trans = getIntent().getStringExtra("TransactionName");
        int money_trans = getIntent().getIntExtra("TransactionMoney", 10000);
        String date_trans = getIntent().getStringExtra("TransactionDate");
        int type_trans_id = getIntent().getIntExtra("TypeID", -1);

        etName.setText(name_trans);
        etMoney.setText(Integer.toString(money_trans));
        etDate.setText(date_trans);
        spnType.setSelection(type_trans_id -1); // auto increasement from 1

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionType transactionType = listType.get(spnType.getSelectedItemPosition());
                Transaction_ transaction_ = new Transaction_(
                        transactionID,
                        etName.getText().toString(),
                        Integer.parseInt(etMoney.getText().toString()),
                        etDate.getText().toString(),
                        transactionType.getType_id());

                boolean status = databaseHelper.updateTransaction(transaction_);
                if (status) {
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(UpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                etDate.setText(sdf.format(myCalendar.getTime()));
            }

        };
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                builder.setTitle("CONFIRM DIALOG!!!");
                builder.setMessage("ARE YOU SURE ABOUT THAT ???");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            boolean status = databaseHelper.deleteTransaction(transactionID);
                            PresentTransactionFragment.updatePresentTransactionListView();
                            PastTransactionFragment.updatePastTransactionListView();
                            if (status) {
                                finish();
                                Toast.makeText(UpdateActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(UpdateActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
    }
}