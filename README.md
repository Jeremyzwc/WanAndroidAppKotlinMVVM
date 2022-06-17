# Kotlin+Flow+协程+Jetpack+MVVM架构实现的WanAndroid客户端的开发搭建

## Github 地址:https://github.com/Jeremyzwc/WanAndroidAppKotlinMVVM

## 简介

采用 `Kotlin` 语言编写，采用MVVM架构，结合ViewMdel、Lifecycle、paging、LiveData、navigation等Jetpack组件以及Retrofit使用。
API是采用的鸿神开放[**玩 Android API**](http://www.wanandroid.com/blog/show/2)
本项目的大部分资源文件和部分代码以及ui效果都是参照WanAndroid站内的[开源WanAndroid项目](https://github.com/iceCola7/WanAndroid), 因为本项目只是为了展示Kotlin+Jetpack+MVVM的开发
架构，并不是再造一个完整的WanAndroid客户端。

**如果这个项目对你学习有帮助或有参考价值，麻烦给个 `Star` 或 `Fork` 哈，谢谢~**

## 主要涉及的技术要点

使用Flow+Retrofit实现Http的链式请求

封装基类:BaseActivity、BaseFragment、BaseViewModel、BasePagingSource、BasePagingDataAdapter、BaseRvAdapter，BaseLifecycleDialog

使用反射实现ViewBinding和ViewModel的模板式绑定

初始化ViewModel时将其和Activity的生命周期绑定

通过新的setMaxLifecycle方法实现Fragment的懒加载

## 最低兼容版本:23

## 主要使用的开源框架

- [Retrofit](https://github.com/square/retrofit)
- [okhttp](https://github.com/square/okhttp)
- [Glide](https://github.com/bumptech/glide)
- [LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)
- [UnpeekLiveData](https://github.com/KunMinX/UnPeek-LiveData)
- [AgentWeb](https://github.com/Justson/AgentWeb)
- [FlowLayout](https://github.com/hongyangAndroid/FlowLayout)
- [BGABanner-Android](https://github.com/bingoogolapple/BGABanner-Android)
- [leakcanary](https://github.com/square/leakcanary)
- [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)

**感谢所有优秀的开源项目**
