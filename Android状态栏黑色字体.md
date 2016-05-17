
# Android状态栏黑色字体

标签（空格分隔）： android 状态栏 statusbar

---
[TOC]

###前言

> [如果你只关心解决方案，请点我](#解决方案)
>由于公司项目的欢迎页是白色的，，修改状态栏颜色后，导致状态栏的白色字体完全被覆盖了，联想到之前在QQ、UC等一些app上都见到过状态栏颜色是字体的，想着，，必定有解决的方案。于是，就有了本篇blog。
    
###搜索结果
> 目前只有一部分系统支持该功能
>  - android6.0+
>  - MIUI6+
>  - flyme4+

###参考
下面是我在网上找到的两篇文章



#### 1.[白底黑字！Android浅色状态栏黑色字体模式](http://www.jianshu.com/p/7f5a9969be53)
 
> 着手试了下，结论
> - android6.0生效
> - MIUI6+生效
> - flyme4+无效

想着，既然是flyme系统官方支持，应该有文档吧，，于是便找到了一下这篇文章
#### 2.[Android-->沉浸式状态栏字体颜色的修改(只针对小米和魅族)](http://blog.csdn.net/angcyo/article/details/49834739)

 

> - [flyme4+](http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI#.E4.BA.8C.E3.80.81.E6.B2.89.E6.B5.B8.E5.BC.8F.E7.8A.B6.E6.80.81.E6.A0.8F)
> - [MIUI6+](http://dev.xiaomi.com/doc/p=4769/index.html)
 
 发现，flyme对系统自定义的一些特性已经有了较为完善的文档和demo，于是，[直接下载demo](http://open.res.flyme.cn/fileserver/upload/file/201501/96d62f2b1c694fc380debec7f656deb5.zip)

经过浏览源码发现，flyme在设置的时候需要加入以下代码

    //设置状态栏透明
    StatusBarProxy.setImmersedWindow(getWindow(), true);

```
    /**
	 * 设置沉浸式窗口，设置成功后，状态栏则透明显示
	 * @param window 需要设置的窗口
	 * @param immersive 是否把窗口设置为沉浸
	 * @return boolean 成功执行返回true
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static boolean setImmersedWindow(Window window, boolean immersive) {
		boolean result = false;
		if (window != null) {
			WindowManager.LayoutParams lp = window.getAttributes();
			int trans_status = 0;
			Field flags;
			if (android.os.Build.VERSION.SDK_INT < 19) {
				try {
					trans_status = 1 << 6;
					flags = lp.getClass().getDeclaredField("meizuFlags");
					flags.setAccessible(true);
					int value = flags.getInt(lp);
					if (immersive) {
						value = value | trans_status;
					} else {
						value = value & ~trans_status;
					}
					flags.setInt(lp, value);
					result = true;
				} catch (Exception e) {
					Log.e(TAG, "setImmersedWindow: failed");
				}
			} else {
				lp.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
				window.setAttributes(lp);
				result = true;
			}
		}
		return result;
	}
```
在加入上述代码后，在flyme5的系统,走的是else分支，调用的是android系统的原生代码，此时，系统进入沉浸式模式，界面会上移statusBar高度，由于没有<19的魅族手机，无法测试，如果有人测试出结果，烦请不吝赐教。我会补充相应内容。

### <span id="解决方案">解决方案</span>
####流程图
```flow
st=>start
condM=>condition: is MIUI6?
condF=>condition: is flyme4+?
cond6=>condition: is 6.0+?
e=>end

st->condM
condM(no)->condF
condF(no)->cond6
cond6(no)->e
cond6(yes)->e
condM(yes)->e
condF(yes)->e
```


####MIUI
```
/**
 *设置状态栏黑色字体图标，
 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
 * @param activity
 * @return 1:MIUUI 2:Flyme 3:android6.0
 */
public static int StatusBarLightMode(Activity activity){
    int result=0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        if(MIUISetStatusBarLightMode(activity.getWindow(), true)){
            result=1;
        }else if(FlymeSetStatusBarLightMode(activity.getWindow(), true)){
            result=2;
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            result=3;
        }
    }
    return result;
}
```
####flyme4+
```
/**
 * 设置状态栏图标为深色和魅族特定的文字风格
 * 可以用来判断是否为Flyme用户
 * @param window 需要设置的窗口
 * @param dark 是否把状态栏字体及图标颜色设置为深色
 * @return  boolean 成功执行返回true
 *
 */
public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            result = true;
        } catch (Exception e) {

        }
    }
    return result;
}
```
#### android6.0+
#####1.代码设置
```
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
   }
```
#####2.style属性设置
```
<item name="android:windowLightStatusBar">true</item>
```

### 结论
本方案适配一下系统
>  - android6.0+
>  - MIUI6
>  - flyme4+ 必须使用沉浸式开发

据说：适配浅色状态栏深色字体的时候发现底层版本为Android6.0.1的MIUI7.1系统不支持View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置，还是得用MIUI自己的深色字体方法。所以，这里先适配MIUI跟flyme，再适配6.0，当然了，如果使用可以直接获取系统名，根据字符串判断，也可以先6.0在MIUI，但是这个不靠谱。还不如直接在6的系统上统一全配置上


----------
说了这么多废话，，下面进入正题
![福利][1]


  [1]: http://hbimg.b0.upaiyun.com/4ef3886fd17a74c9ae5c60ffffed13a62b49c7a33acdf-flB6An_fw658