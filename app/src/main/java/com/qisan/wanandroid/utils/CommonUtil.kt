package com.qisan.wanandroid.utils

import android.graphics.Color
import java.util.*

/**
 * Created by QiSan 2022/6/15
 * package com.qisan.wanandroid.utils
 */
object CommonUtil {

    /**
     * 获取随机颜色值
     */
    fun randomColor(): Int {
        val random = Random()
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        var red = random.nextInt(190)
        var green = random.nextInt(190)
        var blue = random.nextInt(190)
        if (SettingUtil.getIsNightMode()) {
            //150-255
            red = random.nextInt(105) + 150
            green = random.nextInt(105) + 150
            blue = random.nextInt(105) + 150
        }
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue)
    }

}