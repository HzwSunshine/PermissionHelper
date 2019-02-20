# PermissionHelper
基于EasyPermission封装的使用简易的权限请求库

用法：
```

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
```
