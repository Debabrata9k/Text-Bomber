package com.example.textrepetator;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
EditText editText, edtNum;
TextView txtOutput;
Button btnCopy;
AppCompatButton btnGen, btnShare, btnReset;
CheckBox btnCheck;
Toolbar toolbar;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText = findViewById(R.id.edtText);
        edtNum = findViewById(R.id.edtNum);
        txtOutput = findViewById(R.id.txtOutput);
        btnCopy = findViewById(R.id.btnCopy);
        btnGen = findViewById(R.id.btnGen);
        btnShare = findViewById(R.id.btnShare);
        toolbar = findViewById(R.id.toolBar);
        btnReset = findViewById(R.id.btnReset);
        btnCheck = findViewById(R.id.btnCheck);

        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("TEXT BOMBER");
        }

        btnCopy.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        btnShare.setVisibility(View.GONE);




        btnGen.setOnClickListener(view -> {
            String text = editText.getText().toString();
            String countStr = edtNum.getText().toString();

            if (!text.isEmpty() && !countStr.isEmpty()){
                int count = Integer.parseInt(countStr);
                StringBuilder result = new StringBuilder();
                String saparator = btnCheck.isChecked() ? "\n":" ";
                for (int i = 0; i < count; i++){
                    //This appends the user-provided text (stored in the text variable) to the StringBuilder object named result.
                    result.append(text).append(saparator);
                }
                txtOutput.setText(result.toString());
                btnCopy.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                btnShare.setVisibility(View.VISIBLE);
            }else {
                Toast.makeText(this, "Please enter both text and count!", Toast.LENGTH_SHORT).show();
                btnCopy.setVisibility(View.GONE);
                btnReset.setVisibility(View.GONE);
                btnShare.setVisibility(View.GONE);
            }
        });
        btnShare.setOnClickListener(view -> {
            String outPUt = txtOutput.getText().toString();
            if (!outPUt.isEmpty()) {
                Intent iShara = new Intent(Intent.ACTION_SEND);
                iShara.setType("text/plain");
                iShara.putExtra(Intent.EXTRA_TEXT, outPUt);
                startActivity(Intent.createChooser(iShara, "SHARE With"));
            } else {
                Toast.makeText(this, "No Text Present To Share", Toast.LENGTH_SHORT).show();
            }
        });
        btnCopy.setOnClickListener(view -> {
            String outPut = txtOutput.getText().toString();
            if (!outPut.isEmpty()){
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Repeated Text", outPut);
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(this, "Text Copied Successfully", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No text to copy!", Toast.LENGTH_SHORT).show();
            }
        });
        btnReset.setOnClickListener(view -> {
            editText.setText("");
            edtNum.setText("");
            txtOutput.setText("");
            btnReset.setVisibility(View.GONE);
            btnCopy.setVisibility(View.GONE);
            btnShare.setVisibility(View.GONE);
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId==R.id.opt_contact){

            Intent iEmail = new Intent(Intent.ACTION_SEND);
            iEmail.setType("massage/rfc822");
            iEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"karmakardebabrata177@gmail.com"});
            iEmail.putExtra(Intent.EXTRA_SUBJECT, "Queries");
            iEmail.putExtra(Intent.EXTRA_TEXT, "Please resolve your app issue:- ");
            startActivity(Intent.createChooser(iEmail, "E-mail Via"));

        } else if (itemId==R.id.opt_share) {
            Intent iShare = new Intent(Intent.ACTION_SEND);
            iShare.setType("text/plain");
            iShare.putExtra(Intent.EXTRA_TEXT, "Download our app from this link:- https://www.mediafire.com/folder/ox7taejckhusg/APPs");
            startActivity(Intent.createChooser(iShare, "SHARE Via"));
        }else {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}