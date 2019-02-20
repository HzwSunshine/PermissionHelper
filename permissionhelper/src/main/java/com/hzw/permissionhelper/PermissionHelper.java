package com.hzw.permissionhelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * author: hzw
 * time: 2019/2/18 下午7:29
 * description:权限获取工具，基于 Google 官方的 EasyPermissions 封装
 */
public class PermissionHelper {

    static final int PERM_CODE = 1314;

    private PermissionHelper() {
    }

    static PermissionHelper getInstance() {
        return new PermissionHelper();
    }

    /**
     * 检查是否有某个权限
     */
    public static boolean hasPermission(Context context, String... perms) {
        if (context == null) return false;
        return EasyPermissions.hasPermissions(context, perms);
    }

    public static PermissionInfo with(@NonNull AppCompatActivity activity) {
        PermissionInfo info = PermissionInfo.instance();
        info.with(activity);
        return info;
    }

    public static PermissionInfo with(@NonNull Fragment fragment) {
        PermissionInfo info = PermissionInfo.instance();
        info.with(fragment);
        return info;
    }

    public interface PermissionCallback {
        void callback(boolean isGranted, @NonNull List<String> perms);
    }

    void start(PermissionInfo info) {
        Context context;
        if (info.activity != null) {
            context = info.activity;
        } else {
            context = info.fragment.getActivity();
        }
        //已经有权限
        if (PermissionHelper.hasPermission(context, info.perms)) {
            info.callback.callback(true, Arrays.asList(info.perms));
            return;
        }

        //开始使用辅助类获取权限
        FragmentManager manager;
        if (info.activity != null) {
            manager = info.activity.getSupportFragmentManager();
        } else {
            manager = info.fragment.getChildFragmentManager();
        }
        PermissionFragment fragment = (PermissionFragment)
                manager.findFragmentByTag(PermissionFragment.class.getName());
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != null) {
            transaction.remove(fragment);
        }
        fragment = new PermissionFragment();
        fragment.setPermissionInfo(info);
        transaction.add(fragment, PermissionFragment.class.getName());
        transaction.commitNow();
    }


}
