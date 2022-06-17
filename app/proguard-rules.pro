# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5  #指定代码的压缩级别 0 - 7
-dontusemixedcaseclassnames  #是否使用大小写混合
-dontskipnonpubliclibraryclasses  #如果应用程序引入的有jar包，并且想混淆jar包里面的class
-dontpreverify
-verbose #混淆时是否记录日志（混淆后生产映射文件 map 类名 -> 转化后类名的映射
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  #淆采用的算法
-ignorewarnings
-useuniqueclassmembernames
-renamesourcefileattribute SourceFile
#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers
#保留行号
-keepattributes SourceFile,LineNumberTable
#保持泛型
-keepattributes Signature

-obfuscationdictionary key-proguard.txt
#-classobfuscationdictionary key-proguard.txt
-packageobfuscationdictionary key-proguard.txt

#-keep public class * extends android.app.Activity  #所有activity的子类不要去混淆
#-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.android.vending.licensing.ILicensingService

-ignorewarnings -keep class * { public private *; }

# ViewBinding
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static * inflate(android.view.LayoutInflater);
}

-keepclasseswithmembernames class * {
    native <methods>;  #保持 native 的方法不去混淆
}

##################################################################
#下面注释是因为使用了mess之后可以让它混淆
##################################################################
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);  #保持自定义控件类不被混淆，指定格式的构造方法不去混淆
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}

#-keepclassmembers class * extends android.app.Activity {
#    public void *(android.view.View); #保持指定规则的方法不被混淆（Android layout 布局文件中为控件配置的onClick方法不能混淆）
#}

#-keep public class * extends android.view.View {  #保持自定义控件指定规则的方法不被混淆
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#    public void set*(...);
#}

-keepclassmembers class com.google.android.material.bottomnavigation.BottomNavigationMenuView {
    boolean mShiftingMode;
}

-keepclassmembers class android.widget.SearchView {
    ImageView mGoButton;
}

-keepclassmembers enum * {  #保持枚举 enum 不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {  #保持 Parcelable 不被混淆（aidl文件不能去混淆）
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable #需要序列化和反序列化的类不能被混淆（注：Java反射用到的类也不能被混淆）
-keepclassmembers class * implements java.io.Serializable { #保护实现接口Serializable的类中，指定规则的类成员不被混淆
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepattributes *Annotation*  #假如项目中有用到注解，应加入这行配置

-keep class **.R$* { *; }  #保持R文件不被混淆，否则，你的反射是获取不到资源id的

-keep class **.Webview2JsInterface { *; }  #保护WebView对HTML页面的API不被混淆
-keepclassmembers class * extends android.webkit.WebViewClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String);
}

-keep class com.qisan.wanandroid.entity.** { *; }  #转换JSON的JavaBean，类成员名称保护，使其不被混淆
-assumenosideeffects class android.util.Log {
      public static boolean isLoggable(java.lang.String,int);
      public static int v(...);
      public static int i(...);
      public static int w(...);
      public static int d(...);
     public static int e(...);
  }
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

##################################################################
#                   第三方库
##################################################################
################ retrofit ###############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

################ gson ###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.sunloto.shandong.bean.** { *; }

################ glide ###############
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

################ okhttp ###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-keep class sun.misc.Unsafe { *; }

-dontwarn java.lang.invoke.*

################ LeakCanary #################
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }

################ annotation ###############
-keepattributes *Annotation*