package tw.brad.myphoneinfo;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private AccountManager amgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    0);
        }else {init();}
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
    }

    private void init(){
        amgr = AccountManager.get(this);
    }


    public void test1(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent googlePicker = AccountManager.newChooseAccountIntent(
                    null,
                    null,
//                new String[] {"com.google","com.facebook.auth.login"},
                    null,
                    true,
                    null,
                    null,
                    null,
                    null);
            startActivityForResult(googlePicker, 0);
        }else{
            AccountManager accountManager = AccountManager.get(this);
            Account[] accounts = accountManager.getAccounts();
            for (Account a : accounts) {
                Log.d("brad", "type--- " + a.type + " ---- name---- " + a.name);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            Log.d("brad", "Account Name=" + accountName);
            String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
            Log.d("brad", "Account type=" + accountType);

            AccountManager accountManager = AccountManager.get(this);
            Account[] accounts = accountManager.getAccounts();
            for (Account a : accounts) {
                Log.d("brad", "type--- " + a.type + " ---- name---- " + a.name);
            }
        }
    }

}
