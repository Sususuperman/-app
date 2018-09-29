package com.cs.common.view.xview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.android.common.R;


/**
 * Created by weifei on 16/10/9.
 */

public class XImgEditText extends LinearLayout {

    public final static int TOP = 0;
    public final static int BOTTOM = 1;
    public final static int CENTER = 2;

    private XEditView xeditview;
    private ImageView ximage;
    private TextView xrestrictionhint;
    private LinearLayout ximagelayout;
    private LinearLayout xlayout;

    private int textColorHighlight = 0;
    private ColorStateList textColor = null;
    private ColorStateList textColorHint = null;
    private ColorStateList textColorLink = null;
    private int textSize = 15;
    private int styleIndex = -1;
    private boolean allCaps = false;
    private int shadowcolor = 0;
    private float dx = 0, dy = 0, r = 0;


    public XImgEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public XImgEditText(Context context) {
        this(context, null, 0);
    }

    public XImgEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = View.inflate(getContext(), R.layout.layout_x_img_layout, this);

        this.xlayout = (LinearLayout) view.findViewById(R.id.x_layout);
        this.ximagelayout = (LinearLayout) view.findViewById(R.id.x_image_layout);
        this.ximage = (ImageView) view.findViewById(R.id.x_image);
        //创建风险警示输入内容中加入提示限3000字以内
        xrestrictionhint = (TextView) view.findViewById(R.id.x_restrictionhint);
        this.xeditview = (XEditView) view.findViewById(R.id.x_editview);

        final Resources.Theme theme = context.getTheme();
        CharSequence text = "";
        CharSequence hint = null;
        boolean password = false;
        int inputType = EditorInfo.TYPE_NULL;
        int autocap = -1;
        int ellipsize = -1;
        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.xti_textInputview, defStyleAttr, 0);

        int img_visible = a.getInt(R.styleable.xti_textInputview_xti_img_visible, View.GONE);
        this.ximage.setVisibility(img_visible);
        LayoutParams params = (LayoutParams) xlayout.getLayoutParams();
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.xti_textInputview_xti_img) {
                this.ximage.setImageResource(a.getResourceId(attr, R.drawable.delete_icon));
            } else if (attr == R.styleable.xti_textInputview_xti_maxLines) {
                xeditview.setMaxLines(a.getInt(attr, -1));
            } else if (attr == R.styleable.xti_textInputview_xti_background) {
                xlayout.setBackgroundResource(a.getResourceId(attr, R.drawable.x_edit_bg));
            } else if (attr == R.styleable.xti_textInputview_xti_maxHeight) {
                xeditview.setMaxHeight(a.getDimensionPixelSize(attr, -1));
            } else if (attr == R.styleable.xti_textInputview_xti_minHeight) {
                xeditview.setMinHeight(a.getDimensionPixelSize(attr, -1));
            } else if (attr == R.styleable.xti_textInputview_xti_lines) {
                xeditview.setLines(a.getInt(attr, -1));
            } else if (attr == R.styleable.xti_textInputview_xti_capitalize) {
                autocap = a.getInt(attr, autocap);
            } else if (attr == R.styleable.xti_textInputview_xti_height) {
                params.height = a.getDimensionPixelOffset(attr, 0);
            } else if (attr == R.styleable.xti_textInputview_xti_width) {
                params.width = a.getDimensionPixelOffset(attr, 0);
            } else if (attr == R.styleable.xti_textInputview_xti_gravity) {
                xeditview.setGravity(a.getInt(attr, Gravity.CENTER_VERTICAL));
            } else if (attr == R.styleable.xti_textInputview_xti_img_gravity) {
                LayoutParams p = (LayoutParams) ximage.getLayoutParams();
                p.gravity = a.getInt(attr, Gravity.CENTER_VERTICAL);
            } else if (attr == R.styleable.xti_textInputview_xti_hint) {
                hint = a.getText(attr);
            } else if (attr == R.styleable.xti_textInputview_xti_text) {
                text = a.getText(attr);
            } else if (attr == R.styleable.xti_textInputview_xti_ellipsize) {
                ellipsize = a.getInt(attr, ellipsize);
            } else if (attr == R.styleable.xti_textInputview_xti_enabled) {
                xeditview.setEnabled(a.getBoolean(attr, true));
            } else if (attr == R.styleable.xti_textInputview_xti_textColor) {
                textColor = a.getColorStateList(attr);
            } else if (attr == R.styleable.xti_textInputview_xti_textColorHint) {
                textColorHint = a.getColorStateList(attr);
            } else if (attr == R.styleable.xti_textInputview_xti_textSize) {
                textSize = a.getDimensionPixelSize(attr, textSize);
            } else if (attr == R.styleable.xti_textInputview_xti_textStyle) {
                styleIndex = a.getInt(attr, styleIndex);
            } else if (attr == R.styleable.xti_textInputview_xti_password) {
                password = a.getBoolean(attr, password);
            } else if (attr == R.styleable.xti_textInputview_xti_inputType) {
                inputType = a.getInt(attr, EditorInfo.TYPE_NULL);
            } else if (attr == R.styleable.xti_textInputview_xti_imeOptions) {
                int ime = a.getInt(attr, 0x00000000);
                xeditview.setImeOptions(ime);
            }
        }

        if (inputType != EditorInfo.TYPE_NULL) {
            xeditview.setInputType(inputType);
            // If set, the input type overrides what was set using the deprecated singleLine flag.
        } else if (autocap != -1) {

            inputType = EditorInfo.TYPE_CLASS_TEXT;

            switch (autocap) {
                case 1:
                    inputType |= EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES;
                    break;

                case 2:
                    inputType |= EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS;
                    break;

                case 3:
                    inputType |= EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS;
                    break;

                default:
                    break;
            }
            xeditview.setInputType(inputType);
        }

        if (ellipsize < 0) {
            ellipsize = 3; // END
        }

        switch (ellipsize) {
            case 1:
                xeditview.setEllipsize(TextUtils.TruncateAt.START);
                break;
            case 2:
                xeditview.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            case 3:
                xeditview.setEllipsize(TextUtils.TruncateAt.END);
                break;
            case 4:
                xeditview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                break;
        }

        xeditview.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.material_color_grey_700)));
        xeditview.setHintTextColor(textColorHint != null ? textColorHint : ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.material_text_color_black_hint)));
        xeditview.setLinkTextColor(textColorLink);
        if (textColorHighlight != 0) {
            xeditview.setHighlightColor(textColorHighlight);
        }

        if (allCaps) {
            xeditview.setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        }

        if (password) {
            xeditview.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        if (shadowcolor != 0) {
            xeditview.setShadowLayer(r, dx, dy, shadowcolor);
        }

        xeditview.setText(text);
        if (xeditview != null) {
            if (hint != null)
                xeditview.setHint(hint);
        }

        a.recycle();
    }

    public EditText getEditView() {
        return this.xeditview;
    }


    public void setTextSize(int textSize) {
        this.textSize = textSize;
        this.xeditview.setTextSize(textSize);

    }

    public void setHintTextColor(int textColorHint) {
        this.xeditview.setHintTextColor(textColorHint);
    }

    public void setHint(String text) {
        this.xeditview.setHint(text);
    }

    public void setTextColor(ColorStateList textColor) {
        this.textColor = textColor;
        this.xeditview.setTextColor(textColorHint);
    }

    public Editable getText() {
        return xeditview.getText();
    }

    public void setText(String text) {
        this.xeditview.setText(text);
    }

    /**
     * 显示或隐藏图标
     *
     * @param visible
     */
    public void setImgVisible(int visible) {
        getXimageView().setVisibility(visible);
    }

    public void setImageClickListener(OnClickListener clickListener) {
        ximage.setOnClickListener(clickListener);
    }

    public XEditView getXeditview() {
        return xeditview;
    }

    public XEditView setEnable(boolean b) {
        xeditview.setEnabled(b);
        xeditview.setFocusable(b);
        return xeditview;
    }


    public ImageView getXimageView() {
        return ximage;
    }

    //拿到右下角文字提示
    public TextView getXrestrictionhint() {
        return xrestrictionhint;
    }
}
