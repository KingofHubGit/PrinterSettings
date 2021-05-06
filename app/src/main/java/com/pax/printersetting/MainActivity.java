package com.pax.printersetting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pax.printersetting.application.ActivityLifeCycleManager;
import com.pax.printersetting.constants.SettingConfig;
import com.pax.printersetting.password.PaxPassWordUtils;
import com.pax.printersetting.password.PaxPasswordActivity;
import com.pax.printersetting.utils.Utils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.lang.ref.WeakReference;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "dengtl";
    private Context mContext;

    static QMUIRoundButton SizeSettingsButton;

    String[] SizeItems;
    String[] SizeItemValues;

    String mCurrentProp;
    int mCurrentSizeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        init();
        registerActivityLifecycleInit();

    }

    private void init() {

        Bundle bundle=getIntent().getExtras();
        if(bundle != null
        && (bundle.getByte("password1", (byte)0x00) ==(byte)0x95)
        && (bundle.getByte("password2", (byte)0x00) == (byte)0x27)){
            byte password1 = bundle.getByte("password1", (byte)0x00);
            byte password2 = bundle.getByte("password2", (byte)0x00);

            Log.d(TAG, "init: getByteExtra1=" + (byte)password1);
            Log.d(TAG, "init: getByteExtra2=" + (byte)password2);

            Log.d(TAG, "init: password1=" + (byte)0x95);
            Log.d(TAG, "init: password2=" + (byte)0x27);
        }else{
            Log.d(TAG, "init: finished!");
            finish();
            return;
        }

/*        if(!(password1 == (byte)0x95 && password2 == (byte)0x27)){
            Log.d(TAG, "init: finished!");
            finish();
            return;
        }*/

        SizeItems = getResources().getStringArray(R.array.size_option_string);
        SizeItemValues = getResources().getStringArray(R.array.size_option_values);

        SizeSettingsButton = (QMUIRoundButton) findViewById(R.id.size_setting);
        SizeSettingsButton.setOnClickListener(this);
        update();
    }

    private void update() {
        mCurrentProp = Utils.getProp(SettingConfig.PRINTER_SIZE_PROP, SizeItemValues[0]);
        mCurrentSizeIndex = findSizeOptionIndex(mCurrentProp);
        SizeSettingsButton.setText(getString(R.string.size_option_current_printer) + " : " +SizeItems[mCurrentSizeIndex]);
    }

    private void registerActivityLifecycleInit() {
        this.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks(){
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
            Log.d(TAG, "onActivityCreated: ");
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            Log.d(TAG, "onActivityStarted: PaxPassWordUtils.PASS=" + PaxPassWordUtils.PASS);
            Log.d(TAG, "onActivityStarted: ");
            Log.d(TAG, "onActivityStarted: isPasswordDisabled = " + Utils.isPasswordDisabled());
            if(!PaxPassWordUtils.PASS && !Utils.isPasswordDisabled()){
                if(PaxPassWordUtils.PASSWORD_EXIT){
                    finish();
                    PaxPassWordUtils.PASSWORD_EXIT = false;
                }else{
                    startPassWordActivity();
                }
            }
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            Log.d(TAG, "onActivityResumed: ");

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            Log.d(TAG, "onActivityPaused: ");
            PaxPassWordUtils.PASS = false;
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            Log.d(TAG, "onActivityStopped: ");
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
            Log.d(TAG, "onActivitySaveInstanceState: ");
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            Log.d(TAG, "onActivityDestroyed: ");
        }
    };

    private void startPassWordActivity(){
        Intent intent = new Intent(this, PaxPasswordActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PaxPassWordUtils.MAINACTIVITY_EXIT = false;
        Log.d(TAG, "onDestroy: =====");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: =======");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ======");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: ======");
        PaxPassWordUtils.MAINACTIVITY_EXIT = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ======");
        this.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())){
            case R.id.size_setting:
                showSizeChoiceDialog();
                break;
            default:
                break;
        }
    }

    private void showSizeChoiceDialog(){
        Log.d(TAG, "showSizeChoiceDialog: checkedIndex=" + mCurrentSizeIndex);
        Log.d(TAG, "showSizeChoiceDialog: PRINTER_SIZE_PROP=" + Utils.getProp(SettingConfig.PRINTER_SIZE_PROP, SizeItemValues[0]));
        QMUIDialog.CheckableDialogBuilder SizeOptionDialog = new QMUIDialog.CheckableDialogBuilder(this);
        SizeOptionDialog.setTitle(R.string.size_option_title)
                .setCheckedIndex(mCurrentSizeIndex)
                .setSkinManager(QMUISkinManager.defaultInstance(this))
                .addItems(SizeItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentSizeIndex = which;
                        showRebootComfirmDialog();
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog)
                .show();
    }


    @SuppressLint("StringFormatInvalid")
    private void showRebootComfirmDialog(){
        QMUIDialog.MessageDialogBuilder SizeOptionDialog = new QMUIDialog.MessageDialogBuilder(this);
        SizeOptionDialog.setTitle(R.string.reboot_confirm_title)
                .setMessage(mContext.getString(R.string.reboot_confirm_message, SizeItems[mCurrentSizeIndex]))
                .setSkinManager(QMUISkinManager.defaultInstance(this))
                .addAction(mContext.getText(R.string.reboot_confirm_cancel), new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                update();
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .addAction(0, mContext.getText(R.string.reboot_confirm_reboot), QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        comfirmPrinterSize();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                update();
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog)
                .show();
    }

    @SuppressLint("StringFormatInvalid")
    private void comfirmPrinterSize(){
        Utils.setProp(SettingConfig.PRINTER_SIZE_PROP, SizeItemValues[mCurrentSizeIndex]);
        Toast.makeText(mContext, mContext.getString(R.string.size_option_selected, SizeItems[mCurrentSizeIndex]), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                confirmToReboot();
            }
        }, SettingConfig.REBOOT_DELAY_TIME_MS);
    }

    private int findSizeOptionIndex(String value){
        for (int i = 0; i < SizeItemValues.length; i++) {
            if(SizeItemValues[i].equals(value)){
                return i;
            }
        }
        return -1;
    }

    private void confirmToReboot(){
        Intent reBoot = new Intent(Intent.ACTION_REBOOT);
        reBoot.putExtra("nowait", 1);
        reBoot.putExtra("interval", 1);
        reBoot.putExtra("window", 0);
        sendBroadcast(reBoot);
    }

}