package com.qisan.baselib.wigget.verticaltablayout.widget;

import android.content.Context;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import q.rorbin.badgeview.Badge;


/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public abstract class TabView extends FrameLayout implements Checkable, ITabView {

    public TabView(Context context) {
        super(context);
    }

    @Override
    public TabView getTabView() {
        return this;
    }

    @Deprecated
    public abstract ImageView getIconView();

    public abstract TextView getTitleView();

    public abstract Badge getBadgeView();
}
