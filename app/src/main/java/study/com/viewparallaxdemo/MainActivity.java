package study.com.viewparallaxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import study.com.viewparallaxdemo.util.Cheeses;
import study.com.viewparallaxdemo.view.MyListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyListView mListView = (MyListView) findViewById(R.id.lv);
        //去掉下拉/上拉时候的蓝光
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 加Header
        final View mHeaderView = View.inflate(MainActivity.this, R.layout.view_header, null);
        final ImageView mImage = (ImageView) mHeaderView.findViewById(R.id.iv);
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListView.setImage(mImage);
                mHeaderView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mListView.addHeaderView(mHeaderView);

        // 填充数据
        mListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Cheeses.NAMES));
    }
}
