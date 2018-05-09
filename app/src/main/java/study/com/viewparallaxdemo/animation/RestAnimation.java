package study.com.viewparallaxdemo.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * 重写Animation
 */

public class RestAnimation extends Animation {


    private ImageView imageView;
    private int imageViewHeight;
    private int imageViewOriginalHeight;

    public RestAnimation(ImageView imageView, int imageViewHeight, int imageViewOriginalHeight) {
        this.imageView = imageView;
        this.imageViewHeight = imageViewHeight;
        this.imageViewOriginalHeight = imageViewOriginalHeight;
    }


    /**
     * @param interpolatedTime
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        //随动画差值获取距离的变化量
        Float evaluate = evaluate(interpolatedTime, imageViewHeight, imageViewOriginalHeight);

        imageView.getLayoutParams().height = evaluate.intValue();
        imageView.requestLayout();
    }

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
}
