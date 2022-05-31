package com.qisan.wanandroid.widget

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

/**
 * 重写FragmentNavigator控制Navigation控制的Fragment不会每次切换都重建
 * Created by QiSan 2022/6/1
 * package com.qisan.wanandroid.widget
 */
@Navigator.Name("multiplexNavigator")
class MultiplexNavigator(val context: Context, private val fragmentManager: FragmentManager, private val containerId: Int) :
    FragmentNavigator(context, fragmentManager, containerId) {

    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        val ft = fragmentManager.beginTransaction();
        // 获取当前显示的Fragment
        var fragment = fragmentManager.primaryNavigationFragment
        if (fragment != null) {
            ft.hide(fragment);
        }

        val tag = destination.id.toString()
        fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            ft.show(fragment)
        } else {
            fragment = instantiateFragment(context, fragmentManager, destination.className, args)
//            fragment = fragmentManager.fragmentFactory.instantiate(context.classLoader, destination.className)
            ft.add(containerId, fragment, tag)
        }
        ft.setPrimaryNavigationFragment(fragment)
        ft.setReorderingAllowed(true)
        ft.commit()

        return destination
    }

}