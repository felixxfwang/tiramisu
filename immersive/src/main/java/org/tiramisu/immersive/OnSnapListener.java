package org.tiramisu.immersive;

public interface OnSnapListener {

    /*释放的监听*/
    void onPageUnselected(boolean isNext, int position);

    /*选中的监听以及判断是否滑动到底部*/
    void onPageSelected(int position, boolean isBottom);
}