package com.pax.printersetting.password;

/**
 * Created by dengtl on 2020/9/16.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.pax.api.MiscException;
import com.pax.api.MiscSettings;
import com.pax.printersetting.MainActivity;
import com.pax.printersetting.R;
import com.pax.printersetting.utils.Utils;


public class PaxPasswordActivity extends Activity {
    private final String TAG = "dengtl";
    private Button btnCancle;
    private Button btnOk;
    private EditText etPassword;
    private CheckBox cbPassword;
    private SharedPreferences pre;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pax_password_view);
        //initWindows();

        pre = getSharedPreferences(PaxPassWordUtils.PREFERENCE_NAME, Context.MODE_APPEND);
        btnCancle = (Button)findViewById(R.id.cancel_button);
        btnOk = (Button)findViewById(R.id.ok_button);
        btnCancle.setOnClickListener(new ClickListener());
        btnOk.setOnClickListener(new ClickListener());
        etPassword = (EditText)findViewById(R.id.et_password);
        cbPassword = (CheckBox)findViewById(R.id.checkbox_display_password);
        cbPassword.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                final int PsdSelection = etPassword.getSelectionStart();
                Editor editor = pre.edit();
                if(isChecked){
                    editor.putBoolean(PaxPassWordUtils.DISPLAY_PASSWORD, true);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    editor.putBoolean(PaxPassWordUtils.DISPLAY_PASSWORD, false);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                editor.commit();
                etPassword.setSelection(PsdSelection);
            }

        });
        if(pre.getBoolean(PaxPassWordUtils.DISPLAY_PASSWORD, false)){
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            cbPassword.setChecked(true);
        }else{
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            cbPassword.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: +++++++");
        if(PaxPassWordUtils.MAINACTIVITY_EXIT){
            PaxPassWordUtils.MAINACTIVITY_EXIT = false;
            finish();
            return;
        }
        if(Utils.isPasswordDisabled()){
            Log.d(TAG, "onStart: =====");
            startMainActivity();
        }
    }

    private class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch(v.getId()){
                case R.id.cancel_button:
                    PaxPassWordUtils.PASSWORD_EXIT = true;
                    PaxPasswordActivity.this.finish();
                    break;
                case R.id.ok_button:
                    try {
                        if(MiscSettings.checkSettingsPassword(etPassword.getText().toString().trim())){
                            startMainActivity();
                            PaxPassWordUtils.PASS = true;
                            PaxPasswordActivity.this.finish();
                        }else{
                            Toast.makeText(PaxPasswordActivity.this, getResources().getString(R.string.password_error), Toast.LENGTH_SHORT).show();
                        }
                    } catch (MiscException e) {
                        e.printStackTrace();
                        if((etPassword.getText().toString().trim()).equals(PaxPassWordUtils.getDefaultSettingsPassword())){
                            startMainActivity();
                            PaxPassWordUtils.PASS = true;
                            PaxPasswordActivity.this.finish();
                        }else{
                            Toast.makeText(PaxPasswordActivity.this, getResources().getString(R.string.password_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }

    }

    private void startMainActivity(){
        Intent intent = new Intent(PaxPasswordActivity.this, MainActivity.class);
        Bundle bundle = new Bundle(); //该类用作携带数据
        bundle.putByte("password1", (byte)0x95);
        bundle.putByte("password2", (byte)0x27);
        intent.putExtras(bundle);
        PaxPasswordActivity.this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "PswdActivity-onDestroy: ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "PswdActivity-onBackPressed: ");
    }
}
