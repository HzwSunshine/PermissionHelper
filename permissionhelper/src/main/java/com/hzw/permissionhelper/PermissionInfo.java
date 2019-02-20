package com.hzw.permissionhelper;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.List;

/**
 * author: hzw
 * time: 2019/2/19 上午9:49
 * description:权限辅助信息
 */
public class PermissionInfo {
    PermissionHelper.PermissionCallback callback;
    AppCompatActivity activity;
    Fragment fragment;
    //被拒一次后的确认按钮
    String positiveText;
    //被拒一次后的取消按钮
    String negativeText;
    //被拒一次后的提示信息
    String deniedTipsText;
    //永久拒绝后的提示信息
    String neverAskDeniedTipsText;
    //要申请的去权限
    String[] perms;
    //永久被拒绝的权限
    List<String> deniedPerms;

    private PermissionInfo() {
    }

    static PermissionInfo instance() {
        return new PermissionInfo();
    }

    void with(AppCompatActivity activity) {
        this.activity = activity;
        this.fragment = null;
    }

    void with(Fragment fragment) {
        this.fragment = fragment;
        this.activity = null;
    }

    private Resources getResources() {
        if (activity != null) {
            return activity.getResources();
        } else {
            return fragment.getResources();
        }
    }

    public PermissionInfo positiveText(@StringRes int textId) {
        this.positiveText = getResources().getString(textId);
        return this;
    }

    public PermissionInfo positiveText(String text) {
        this.positiveText = text;
        return this;
    }

    public PermissionInfo negativeText(@StringRes int textId) {
        this.negativeText = getResources().getString(textId);
        return this;
    }

    public PermissionInfo negativeText(String text) {
        this.negativeText = text;
        return this;
    }

    public PermissionInfo deniedTipsText(@StringRes int textId) {
        this.deniedTipsText = getResources().getString(textId);
        return this;
    }

    public PermissionInfo deniedTipsText(String text) {
        this.deniedTipsText = text;
        return this;
    }

    public PermissionInfo neverAskDeniedTipsText(@StringRes int textId) {
        this.neverAskDeniedTipsText = getResources().getString(textId);
        return this;
    }

    public PermissionInfo neverAskDeniedTipsText(String text) {
        this.neverAskDeniedTipsText = text;
        return this;
    }

    public PermissionInfo permission(String... perms) {
        this.perms = perms;
        return this;
    }

    public PermissionInfo permissionCallback(PermissionHelper.PermissionCallback callback) {
        this.callback = callback;
        return this;
    }

    public void start() {
        PermissionHelper helper = PermissionHelper.getInstance();
        //check info
        if (perms == null || perms.length == 0 || callback == null) {
            return;
        }
        if (TextUtils.isEmpty(deniedTipsText)) {
            return;
        }
        if (TextUtils.isEmpty(positiveText)) {
            positiveText = "确定";
        }
        if (TextUtils.isEmpty(negativeText)) {
            negativeText = "取消";
        }
        helper.start(this);
    }

}
