package com.example.myapplicationtest;

import android.content.Intent;
import android.os.Bundle;

import com.linecorp.linesdk.LoginDelegate;
import com.linecorp.linesdk.LoginListener;
import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.api.LineApiClient;;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.linecorp.linesdk.widget.LoginButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static LineApiClient lineApiClient;
    private static final int REQUEST_CODE = 1;
    private static String IdChannelBrosnan = "1653842847";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginDelegate loginDelegate = LoginDelegate.Factory.create();

        LoginButton loginButton = (LoginButton)findViewById(R.id.loginButton);

        loginButton.setChannelId(IdChannelBrosnan);

        // configure whether login process should be done by Line App, or inside WebView.
        loginButton.enableLineAppAuthentication(true);

        loginButton.setAuthenticationParams(new LineAuthenticationParams.Builder()
                        .scopes(Arrays.asList(Scope.PROFILE,Scope.FRIEND))
//                 .nonce("<a randomly-generated string>") // nonce can be used to improve security
                        .build()

        );
        loginButton.setLoginDelegate(loginDelegate);

//        //login listener ini ga kebaca
//        loginButton.addLoginListener(new LoginListener() {
//            @Override
//            public void onLoginSuccess(@NonNull LineLoginResult result) {
//
//                Log.println(Log.DEBUG,"onLoginSuccess","masuk onLoginSuccess");
//
//                String accessToken = result.getLineCredential().getAccessToken().getTokenString();
//
//                Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
//                intent.putExtra("display_name", result.getLineProfile().getDisplayName());
//                intent.putExtra("status_message", result.getLineProfile().getStatusMessage());
//                intent.putExtra("user_id", result.getLineProfile().getUserId());
//                intent.putExtra("picture_url", result.getLineProfile().getPictureUrl().toString());
//                startActivity(intent);
//
//                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLoginFailure(@Nullable LineLoginResult result) {
//                Log.println(Log.DEBUG,"onLoginFailed","masuk onLoginFailed");
//                Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
//            }
//        });

 //      CODE CUSTOM CODE
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Login Custom!!",Toast.LENGTH_SHORT).show();
                try {
                    Log.d("TIDAK ERROR BROS","Tidak ERRO BROS");
                    Intent loginIntent = LineLoginApi.getLoginIntent(
                            getApplicationContext(),
                            SyncStateContract.Constants._ID,
                            new LineAuthenticationParams.Builder()
                                .scopes(Arrays.asList(Scope.PROFILE))
                                .build());
                    startActivityForResult(loginIntent,REQUEST_CODE);
                }catch (Exception e){
                    Log.e("ERROR BROS", "Brosnan errorrr"+e.toString());
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            Log.e("ERROR", "Unsupported Request");
            return;
        }

        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);

        switch (result.getResponseCode()) {

            case SUCCESS:
                // Login successful
                String accessToken = result.getLineCredential().getAccessToken().getTokenString();

                Intent transitionIntent = new Intent(this, FriendListActivity.class);
                transitionIntent.putExtra("display_name", result.getLineProfile().getDisplayName());
                transitionIntent.putExtra("status_message", result.getLineProfile().getStatusMessage());
                transitionIntent.putExtra("user_id", result.getLineProfile().getUserId());
                transitionIntent.putExtra("picture_url", result.getLineProfile().getPictureUrl().toString());
                transitionIntent.putExtra("line_profile", result.getLineProfile());
                transitionIntent.putExtra("line_credential", result.getLineCredential());
                startActivity(transitionIntent);
                break;

            case CANCEL:
                // Login canceled by user
                Log.e("ERROR", "LINE Login Canceled by user.");
                break;

            default:
                // Login canceled due to other error
                Log.e("ERROR", "Login FAILED!");
                Log.e("ERROR", result.getErrorData().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
