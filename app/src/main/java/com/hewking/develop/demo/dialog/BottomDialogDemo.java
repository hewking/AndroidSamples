package com.hewking.develop.demo.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hewking.develop.R;

/**
 * @author: jianhao
 * @create: 2020/7/17
 * @description:
 */
public class BottomDialogDemo extends BottomDialogBase implements
        View.OnClickListener{

    public BottomDialogDemo(Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.dialog_bottom_demo);
        ViewGroup.LayoutParams params =  findViewById(android.R.id.content).getLayoutParams();
        params.width = getContext().getResources().getDisplayMetrics().widthPixels;
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        findViewById(R.id.dialog_ok).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
