package com.hzw.permissionhelper.sample;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hzw.permissionhelper.PermissionHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFg = getIntent().getBooleanExtra(KEY, false);
        if (isFg) {
            findViewById(R.id.btn_callPhone).setVisibility(View.GONE);
            findViewById(R.id.btn_fgtest).setVisibility(View.GONE);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main, new MainFragment());
            transaction.commit();
        } else {
            findViewById(R.id.btn_callPhone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestPermission();
                }
            });
            findViewById(R.id.btn_fgtest).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra(KEY, true);
                    startActivity(intent);
                }
            });
        }
    }

    private void requestPermission() {
        PermissionHelper.with(this)
                //要申请的权限或权限组
                .permission(Manifest.permission.CALL_PHONE)
                //权限被非永久拒绝后的弹框提示信息
                .deniedTipsText("我想用你的手机打个电话，可以么？")
                //权限被永久拒绝后的弹框提示信息，不设置此项时不弹框提示
                .neverAskDeniedTipsText("此功能需要打电话的权限，否则无法正常使用，是否打开设置？")
                //确认按钮
                .positiveText("确定")
                //取消按钮
                .negativeText("取消")
                //授权回调
                .permissionCallback(new PermissionHelper.PermissionCallback() {
                    @Override
                    public void callback(boolean isGranted, @NonNull List<String> perms) {
                        if (isGranted) {
                            Toast.makeText(getApplication(), "已经有打电话的权限啦！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), "打电话权限获取被拒绝！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                //开始授权
                .start();
    }
}
