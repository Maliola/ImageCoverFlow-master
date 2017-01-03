package com.mogujie.coverflowsample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dolphinwang.imagecoverflow.CoverFlowAdapter;
import com.dolphinwang.imagecoverflow.CoverFlowView;

public class MyActivity extends Activity {

    protected static final String VIEW_LOG_TAG = "CoverFlowDemo";
    private TextView bottomtitle;
    int index=0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
        setContentView(v);

        final CoverFlowView<MyCoverFlowAdapter> mCoverFlowView = (CoverFlowView<MyCoverFlowAdapter>) findViewById(R.id.coverflow);
        bottomtitle= (TextView) findViewById(R.id.bottomtitle);
        MyCoverFlowAdapter adapter = new MyCoverFlowAdapter(this);
        mCoverFlowView.setAdapter(adapter);
        mCoverFlowView.setSelection(0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < 3) {
                    mCoverFlowView.setSelection(index);
                }
                index++;
                if(index<3)
                handler.postDelayed(this,500);
            }
        }, 500);
        mCoverFlowView.setCoverFlowListener(new CoverFlowView.CoverFlowListener<MyCoverFlowAdapter>() {
            @Override
            public void imageOnTop(CoverFlowView<MyCoverFlowAdapter> view, int position, float left, float top, float right, float bottom) {
                Log.e(VIEW_LOG_TAG, position + " on top!");
            }

            @Override
            public void topImageClicked(CoverFlowView<MyCoverFlowAdapter> view, int position) {
                Log.e(VIEW_LOG_TAG, position + " clicked!");
            }

            @Override
            public void invalidationCompleted() {
            }
        });

        mCoverFlowView.setTopImageLongClickListener(new CoverFlowView.TopImageLongClickListener() {
            @Override
            public void onLongClick(int position) {
                Log.e(VIEW_LOG_TAG, "top image long clicked == >" + position);
            }
        });
    }
    class MyCoverFlowAdapter extends CoverFlowAdapter {
        public MyCoverFlowAdapter(Context context) {
            image1 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.icon1_youxi_da);

            image2 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.icon1_shipin_da);

            image3 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.icon1_zhaopian_da);
        }
        private Bitmap image1 = null;

        private Bitmap image2 = null;

        private Bitmap image3 = null;
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Bitmap getImage(final int position) {
            if(position == 0){
                bottomtitle.setText("你不曾感受的空间");
                return image1;
            }else if(position == 1){
                bottomtitle.setText("你从未见过的画面");
                return image2;
            }
            bottomtitle.setText("另一个世界，就在眼前");
            return image3;
        }
    }
}
