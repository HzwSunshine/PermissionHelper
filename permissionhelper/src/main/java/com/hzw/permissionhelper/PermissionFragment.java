package com.hzw.permissionhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.hzw.permissionhelper.PermissionHelper.PERM_CODE;


/**
 * author: hzw
 * time: 2019/2/19 上午11:08
 * description:权限获取辅助类
 */
public class PermissionFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private PermissionInfo info;

    public void setPermissionInfo(PermissionInfo info) {
        this.info = info;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (info == null) return;
        //申请权限
        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, PERM_CODE, info.perms)
                        .setRationale(info.deniedTipsText)
                        .setPositiveButtonText(info.positiveText)
                        .setNegativeButtonText(info.negativeText)
                        //.setTheme(R.style.my_fancy_style)
                        .build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (info != null && info.callback != null && requestCode == PERM_CODE) {
            info.callback.callback(true, perms);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (info != null && info.callback != null && requestCode == PERM_CODE) {
            neverAskDenied(perms);
            info.callback.callback(false, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE && info != null
                && !TextUtils.isEmpty(info.neverAskDeniedTipsText)
                && info.callback != null && info.deniedPerms != null) {
            if (PermissionHelper.hasPermission(this.getContext(), info.deniedPerms.toArray(new String[0]))) {
                info.callback.callback(true, info.deniedPerms);
            }
        }
    }

    private void neverAskDenied(List<String> perms) {
        //权限永久被拒绝后，提示对应的信息
        if (!TextUtils.isEmpty(info.neverAskDeniedTipsText)
                && EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            info.deniedPerms = perms;
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale(info.neverAskDeniedTipsText)
                    .setPositiveButton(info.positiveText)
                    .setNegativeButton(info.negativeText)
                    .build()
                    .show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        info = null;
    }
}
