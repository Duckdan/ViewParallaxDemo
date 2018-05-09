package study.com.viewparallaxdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import study.com.viewparallaxdemo.animation.RestAnimation;

/**
 * Created by Administrator on 2018/5/9.
 */

public class MyListView extends ListView {

    private static final String TAG = "TAG";
    //头部图片
    private ImageView imageView;
    //用于记录ImageView的初始高度
    private int imageViewOriginalHeight;
    //ImageView的高度
    private int imageViewHeight;
    //图片的高度
    private int drawableHeight;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }


    public void setImage(ImageView imageView) {
        this.imageView = imageView;
        imageViewHeight = imageView.getHeight();
        imageViewOriginalHeight = imageViewHeight;
        drawableHeight = imageView.getDrawable().getIntrinsicHeight();
    }

    /**
     * @param deltaX
     * @param deltaY         y轴瞬时的变化量 到达顶部时还往下拉的话改值为负数，到达底部还往上拉的话是正值
     * @param scrollX
     * @param scrollY        y轴的变化量
     * @param scrollRangeX
     * @param scrollRangeY   y轴可以滚动的范围
     * @param maxOverScrollX
     * @param maxOverScrollY 最大滚动值
     * @param isTouchEvent   为true时代表当前滚动是手指触发的，为false时代表当前滚动是惯性滑动
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {
        Log.e(TAG, "deltaY: " + deltaY + " scrollY: " + scrollY + " scrollRangeY: " + scrollRangeY
                + " maxOverScrollY: " + maxOverScrollY + " isTouchEvent: " + isTouchEvent);

        if (deltaY < 0 && isTouchEvent) {
            //当控件的高度小于图片真实宽度时才允许触发如下代码
            if (imageView.getHeight() <= drawableHeight) {
                //不再用imageViewHeight的原因是因为，当手指抬起后的第二次拉伸，它的值已经被第一次拉伸加大了，
                //会出现一个跨度，所以要想使用imageViewHeight必须使其起始值赋值为imageView.getHeight()
//                imageViewHeight += Math.abs(deltaY);
//                imageView.getLayoutParams().height = imageViewHeight;
                int newHeight = (int) (imageView.getHeight() + Math.abs(deltaY/3.0f));
                imageView.getLayoutParams().height = newHeight;
                imageView.requestLayout();
            }
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY,
                isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
//                valueAnimatorMethod();
                RestAnimation restAnimation = new RestAnimation(imageView,imageView.getHeight(), imageViewOriginalHeight);
                restAnimation.setDuration(500);
                restAnimation.setInterpolator(new OvershootInterpolator());
                startAnimation(restAnimation);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void valueAnimatorMethod() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //0-1.0 动画距离的差值
                float fraction = animation.getAnimatedFraction();
                //随动画差值获取距离的变化量
                Float evaluate = evaluate(fraction, imageView.getHeight(), imageViewOriginalHeight);
                imageView.getLayoutParams().height = evaluate.intValue();
                imageView.requestLayout();
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.start();
    }

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
}
