package com.hzw.permissionhelper.sample;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hzw.permissionhelper.PermissionHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
    }

    private void requestPermission() {
        PermissionHelper.with(this)
                .permission(Manifest.permission.CALL_PHONE)
                .deniedTipsText("我想用你的手机拍个照，可以么？")
                .neverAskDeniedTipsText("此功能需要拍照的权限，否则无法正常使用，是否打开设置？")
                .positiveText("确定")
                .negativeText("取消")
                .permissionCallback(new PermissionHelper.PermissionCallback() {
                    @Override
                    public void callback(boolean isGranted, @NonNull List<String> perms) {
                        if (isGranted) {
                            Toast.makeText(getActivity(), "已经有拍照的权限啦！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "拍照权限获取被拒绝！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
    }
}
