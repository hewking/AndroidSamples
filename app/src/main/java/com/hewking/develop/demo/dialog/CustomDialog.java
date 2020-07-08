package com.hewking.develop.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hewking.develop.R;

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private CharSequence message;
        private int messageGravity = Gravity.CENTER;
        private CharSequence confirm_btnText;
        private String cancel_btnText;
        private String neutral_btnText;
        private boolean mCancelable = true;
        private boolean canceledOutside = true;
        private View contentView;
        private OnClickListener confirm_btnClickListener;
        private OnClickListener cancel_btnClickListener;
        private OnClickListener neutral_btnClickListener;
        private boolean titleVisibility = true;
        private int confirmTextColor = R.color.colorPrimary;
        private int cancelTextColor = -1;


        public Builder(Context context) {
            this.context = context;
        }

        /*<Modify>-begin:扩大接收参数的范围，可以进行字体颜色设置;2016/9/23;by xl*/
        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = context.getText(message);
            return this;
        }

        public Builder setMessageGravity(int gravity) {
            this.messageGravity = gravity;
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public void setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
        }

        public void setCanceledOnTouchOutside(boolean canceledOutside) {
            this.canceledOutside = canceledOutside;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleVisibility(boolean visibility) {
            this.titleVisibility = visibility;
            return this;
        }

        /**
         * 设置对话框界面
         *
         * @param v View
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param confirm_btnText
         * @return
         */
        public Builder setPositiveButton(int confirm_btnText,
                                         OnClickListener listener) {
            this.confirm_btnText = (String) context.getText(confirm_btnText);
            this.confirm_btnClickListener = listener;
            return this;
        }

        /**
         * Set the positive button and it's listener ,升级，可以对文字颜色的控制
         *
         * @param confirm_btnText
         * @return
         */
        public Builder setPositiveButton(CharSequence confirm_btnText,
                                         OnClickListener listener) {
            this.confirm_btnText = confirm_btnText;
            this.confirm_btnClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text color
         *
         * @param confirmTextColor
         * @return
         */
        public Builder setPositiveTextColor(int confirmTextColor) {
            this.confirmTextColor = confirmTextColor;
            return this;
        }

        public Builder setNegativeTextColor(int cancelTextColor) {
            this.cancelTextColor = cancelTextColor;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @return
         */
        public Builder setNegativeButton(int cancel_btnText,
                                         OnClickListener listener) {
            this.cancel_btnText = (String) context.getText(cancel_btnText);
            this.cancel_btnClickListener = listener;
            return this;
        }

        /**
         * Set the negative button and it's listener
         *
         * @return
         */
        public Builder setNegativeButton(String cancel_btnText,
                                         OnClickListener listener) {
            this.cancel_btnText = cancel_btnText;
            this.cancel_btnClickListener = listener;
            return this;
        }

        /**
         * Set the netural button resource and it's listener
         *
         * @return
         */
        public Builder setNeutralButton(int neutral_btnText,
                                        OnClickListener listener) {
            this.neutral_btnText = (String) context.getText(neutral_btnText);
            this.neutral_btnClickListener = listener;
            return this;
        }

        /**
         * Set the netural button and it's listener
         *
         * @return
         */
        public Builder setNeutralButton(String neutral_btnText,
                                        OnClickListener listener) {
            this.neutral_btnText = neutral_btnText;
            this.neutral_btnClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.dl_base_common);
            dialog.setCancelable(mCancelable);
//            dialog.setCanceledOnTouchOutside(canceledOutside);
            View layout = inflater.inflate(R.layout.dialog_common_base, null);
            // set the dialog title
            TextView tv_title = (TextView) layout.findViewById(R.id.title);
            tv_title.setText(title);
            tv_title.getPaint()
                    .setFakeBoldText(true);
            if (!titleVisibility) {
                tv_title.setVisibility(View.GONE);
            }

            /*<Modify>-start:设置内容排列方式;2017-03-16;雷管*/
            /*if (title == null || title.trim().length() == 0) {
                ((TextView) layout.findViewById(R.id.message))
                        .setGravity(Gravity.CENTER);
            }*/
            TextView msgTV = (TextView) layout.findViewById(R.id.message);
            if (title == null || title.trim().length() == 0) {
                msgTV.setGravity(Gravity.CENTER);
            } else {
                msgTV.setGravity(messageGravity);
            }
            /*<Modify>-end:设置内容排列方式;2017-03-16;雷管*/

            if (neutral_btnText != null && confirm_btnText != null
                    && cancel_btnText != null) {
                Button btnConfirm = (Button) layout.findViewById(R.id.confirm_btn);
                btnConfirm.setText(confirm_btnText);
                btnConfirm.setTextColor(dialog.getContext().getResources().getColor(confirmTextColor));

                if (neutral_btnClickListener != null) {
                    layout.findViewById(R.id.neutrall_btn)
                            .setOnClickListener(v -> neutral_btnClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEUTRAL));
                } else {
                    layout.findViewById(R.id.neutrall_btn)
                            .setOnClickListener(v -> dialog.dismiss());
                }
            } else {
                // if no confirm button or cancle button or neutral just set the
                // visibility to GONE
                layout.findViewById(R.id.neutrall_btn).setVisibility(View.GONE);
            }
            // set the confirm button
            if (confirm_btnText != null) {
                Button btnConfirm = ((Button) layout.findViewById(R.id.confirm_btn));
                btnConfirm.setText(confirm_btnText);
                btnConfirm.setTextColor(dialog.getContext().getResources().getColor(confirmTextColor));
                if (confirm_btnClickListener != null) {
                    layout.findViewById(R.id.confirm_btn)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    confirm_btnClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                } else {
                    layout.findViewById(R.id.confirm_btn)
                            .setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.confirm_btn).setVisibility(View.GONE);
            }
            // set the cancel button
            if (cancel_btnText != null) {
                ((Button) layout.findViewById(R.id.cancel_btn))
                        .setText(cancel_btnText);

                if (cancelTextColor != -1) {
                    ((Button) layout.findViewById(R.id.cancel_btn)).setTextColor(cancelTextColor);
                }
                if (cancel_btnClickListener != null) {
                    layout.findViewById(R.id.cancel_btn)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cancel_btnClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                } else {
                    layout.findViewById(R.id.cancel_btn)
                            .setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                }
            } else {
                // if no cancel button just set the visibility to GONE
                layout.findViewById(R.id.cancel_btn).setVisibility(View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((RelativeLayout) layout.findViewById(R.id.dialog_base_content)).removeAllViews();
                ((RelativeLayout) layout.findViewById(R.id.dialog_base_content)).addView(
                        contentView, new LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
            }

            if (TextUtils.isEmpty(message) && contentView == null) {
                layout.findViewById(R.id.dialog_base_content).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
//        getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
