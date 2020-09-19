package nguyenlexuantung.digitalwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import nguyenlexuantung.digitalwallet.helper.DatabaseHelper;
import nguyenlexuantung.digitalwallet.model.TransactionType;
import nguyenlexuantung.digitalwallet.model.Transaction_;

public class InitActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    List<TransactionType> listType;
    EditText etName, etMoney, etDate;
    Spinner spnType;
    Button btnProcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        etName = (EditText) findViewById(R.id.et_Name);
        etMoney = (EditText) findViewById(R.id.et_Money);
        etDate = (EditText) findViewById(R.id.et_Date);
        spnType = (Spinner) findViewById(R.id.spn_Type);
        btnProcess = (Button) findViewById(R.id.btn_Process);


        databaseHelper = new DatabaseHelper(this, DatabaseHelper.DATABASE_NAME, null, 1);
        listType = new ArrayList<>();
        listType = databaseHelper.getAllTransactionType();
//        String[] nameType = new String[listType.size()];
//        for (int i = 0; i < listType.size(); i++) {
//            nameType[i] = listType.get(i).getType_name(); //create array of name
//        }
        ArrayAdapter<TransactionType> adapterType = new ArrayAdapter<>(this, R.layout.layout_spinner, listType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adapterType);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /// HIDE SOFTKEYBOARD
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                /// CREATE DatePickerDialog
                new DatePickerDialog(InitActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etName.getText().toString().trim().equalsIgnoreCase("")) {
                    etName.setError("This field can not be blank");
                } else {
                    etName.setError(null);
                }
                if (etMoney.getText().toString().trim().equalsIgnoreCase("")) {
                    etMoney.setError("This field can not be blank");
                } else {
                    etName.setError(null);
                }
                if (etDate.getText().toString().trim().equalsIgnoreCase("")) {
                    etDate.setError("This field can not be blank");
                } else {
                    etDate.setError(null);
                }
                if (!etName.getText().toString().trim().equalsIgnoreCase("") &&
                        !etMoney.getText().toString().trim().equalsIgnoreCase("") &&
                        !etDate.getText().toString().trim().equalsIgnoreCase("")) {
                    updateDatabase();
                }
            }
        });

    }

    private void updateDatabase() {
        TransactionType transactionType = listType.get(spnType.getSelectedItemPosition());
        Transaction_ transaction_ = new Transaction_(
                0,
                etName.getText().toString(),
                Integer.parseInt(etMoney.getText().toString()),
                etDate.getText().toString(),
                transactionType.getType_id()
        );

        boolean status = databaseHelper.addTransaction(transaction_);
        if (status) {

            Toast.makeText(InitActivity.this, "ADDED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(InitActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}