package com.hywy.publichzt.view.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.common.utils.PhoneUtil;
import com.hywy.publichzt.R;

/**
 * 更多界面的每项菜单界面
 * @author weifei
 *
 */
public class MoreItemView extends LinearLayout {
    private Context mContext;
    private View mTopLine,mBottomLine;
    private LinearLayout mItemLayout,mMoreLayout;
    private TextView mItemText,mItemRightText;
    private ImageView mItemLeftIcon ,mItemRightIcon,mUnreadIcon;


    private String _item1_Text;
    private int _item1_TextColor;
    private float _item1_TextSize;
    
    private String _item2_Text;
    private int _item2_TextColor;
    private float _item2_TextSize;
    private int _item_background;
    private int _line1_Visibility;
    private int _line2_Visibility;
    private int _rightIcon_visibility;
    private int _leftIcon_visibility;
    private Drawable _leftIcon;
    private Drawable _rightIcon;
//    private Drawable _item_Drawable_background;
    private int mHeight;
    private LayoutParams moreLayoutParams;
//    private OnMenuClickListener onMenuClickListener;
    public MoreItemView(Context context) {
        this(context, null);
    }
    
    public MoreItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }
    
    
    @SuppressLint("Recycle")
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.More_menuItem);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_more_menu_view, null);
        addView(view);
        mItemLayout = (LinearLayout)view.findViewById(R.id.item);
        mMoreLayout = (LinearLayout)view.findViewById(R.id.more_layout);
        
        moreLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 48);
        setLayoutParams(moreLayoutParams);
        mMoreLayout.setLayoutParams(moreLayoutParams);
        mHeight = a.getDimensionPixelSize(R.styleable.More_menuItem_item_height, PhoneUtil.dp2px(mContext, 48));
        mTopLine = view.findViewById(R.id.top_line);
        mBottomLine = view.findViewById(R.id.bottom_line);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mItemText = (TextView)view.findViewById(R.id.item_text);
        _item1_TextColor = a.getColor(R.styleable.More_menuItem_item1_textcolor, getResources().getColor(R.color.font_1));
        _item1_TextSize = a.getDimension(R.styleable.More_menuItem_item1_textsize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
        _item1_Text = a.getString(R.styleable.More_menuItem_item1_text);
        _item2_TextColor = a.getColor(R.styleable.More_menuItem_item2_textcolor,  getResources().getColor(R.color.font_1));
        _item2_TextSize = a.getDimension(R.styleable.More_menuItem_item2_textsize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
        _item2_Text = a.getString(R.styleable.More_menuItem_item2_text);



//        _item_Drawable_background = a.getDrawable(R.styleable.More_menuItem_item_background);

        _item_background = a.getResourceId(R.styleable.More_menuItem_item_background,  android.R.color.white);
        _line1_Visibility = a.getInteger(R.styleable.More_menuItem_line1_visibility, View.GONE);
        _line2_Visibility = a.getInteger(R.styleable.More_menuItem_line2_visibility , View.GONE);
        _rightIcon_visibility = a.getInteger(R.styleable.More_menuItem_rightIcon_visibility , View.INVISIBLE);
        _leftIcon_visibility = a.getInteger(R.styleable.More_menuItem_leftIcon_visibility, View.VISIBLE);
        
        mItemRightText = (TextView)view.findViewById(R.id.item_text1);
        mItemLeftIcon = (ImageView)view.findViewById(R.id.left_icon);
        mItemRightIcon = (ImageView)view.findViewById(R.id.right_icon);
        mUnreadIcon = (ImageView)view.findViewById(R.id.unread);
        
        
        _leftIcon = a.getDrawable(R.styleable.More_menuItem_leftIcon);
        _rightIcon = a.getDrawable(R.styleable.More_menuItem_rightIcon);
        
        
        setItemText(_item1_Text);
        setItemRightText(_item2_Text);
        
        setLeftIcon(_leftIcon);
        setRightIcon(_rightIcon);
//        setDrawableBackground(R.drawable.menu_green_light_bg);
//        setItem1_TextSize(_item1_TextSize);
//        setItem2_TextSize(_item2_TextSize);
//        getResources().getDimension()
//        setItemBackground(android.R.attr.selectableItemBackground);
        setItemBackground(_item_background);
        setLine1_Visibility(_line1_Visibility);
        setLine2_Visibility(_line2_Visibility);
        setRightIcon_visibility(_rightIcon_visibility);
        setLeftIcon_visibility(_leftIcon_visibility);

        this.mItemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, _item1_TextSize);
        this.mItemRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, _item2_TextSize);

        setItem1_TextColor(_item1_TextColor);
        setItem2_TextColor(_item2_TextColor);
        
        setItemHeight(mHeight);
        a.recycle();
//        mItemLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onMenuClickListener != null) {
//                    onMenuClickListener.onClick(v);
//                }
//            }
//        });
    }
    public void setItemBackground(String text){

    }
    public  void setItemBackground(int color){
      this._item_background=color;
      this.mItemLayout.setBackgroundResource(color);
    }
    public void setItemText(String text){
        if(text != null){
            _item1_Text = text;
            mItemText.setText(_item1_Text);
        }
    }
    public void setItemText(int id){
        setItemText(getResources().getString(id));
    }
    
    public void setItemRightText(String text){
        if(text != null){
            _item2_Text = text;
            mItemRightText.setText(_item2_Text);
        }
    }

    public void setItemRightText(CharSequence text){
        if(text != null){
            mItemRightText.setText(text);
        }
    }
    public void setItemRightText(int id){
        setItemRightText(getResources().getString(id));
    }
    
    public void setLeftIcon(Drawable drawable){
        if(drawable != null){
            _leftIcon = drawable;
            mItemLeftIcon.setImageDrawable(drawable);
        }
    }
    
    public void setLeftIcon(int drawableId){
        setLeftIcon(getResources().getDrawable(drawableId));
    }
    
    public void setRightIcon(Drawable drawable){
        if (drawable != null) {
            _rightIcon = drawable;
            mItemRightIcon.setImageDrawable(drawable);
        }
    }
    
    public void setRightIcon(int drawableId){
        setRightIcon(getResources().getDrawable(drawableId));
    }
    
    public void setItem1_TextSize(float size){
        this._item1_TextSize = size;
        this.mItemText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
    
    public void setItem2_TextSize(float size){
        this._item2_TextSize = size;
        this.mItemRightText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
    
    public void setItem1_TextColor(int color){
        this._item1_TextSize = color;
        this.mItemText.setTextColor(color);
    }
    
    public void setItem2_TextColor(int color){
        this._item2_TextSize = color;
        this.mItemRightText.setTextColor(color);
    }
    
    public void setLine1_Visibility(int visbility){
        this._line1_Visibility = visbility;
        mTopLine.setVisibility(visbility);
    }
    
    public void setLine2_Visibility(int visbility){
        this._line2_Visibility = visbility;
        mBottomLine.setVisibility(visbility);
    }
    
    public void setRightIcon_visibility(int visbility){
        this._rightIcon_visibility = visbility;
        mItemRightIcon.setVisibility(visbility);
    }
    
    public void setLeftIcon_visibility(int visbility){
        this._leftIcon_visibility = visbility;
        mItemLeftIcon.setVisibility(visbility);
    }
    
    public void setItemHeight(int height){
        moreLayoutParams.height = height;
        mMoreLayout.setLayoutParams(moreLayoutParams);
    }

    public TextView getItemRightText() {
        return mItemRightText;
    }

    public ImageView getItemRightIcon() {
        return mItemRightIcon;
    }

    public void setItemRightText(TextView mItemRightText) {
        this.mItemRightText = mItemRightText;
    }

    public TextView getItemText() {
        return mItemText;
    }

    public void setItemText(TextView mItemText) {
        this.mItemText = mItemText;
    }

    public ImageView getUnreadIcon() {
        return mUnreadIcon;
    }

    public void setUnreadIcon(ImageView mUnreadIcon) {
        this.mUnreadIcon = mUnreadIcon;
    }
    
    
    
//    public interface OnMenuClickListener{
//        public void onClick(View v);
//    }

//    public OnMenuClickListener getOnMenuClickListener() {
//        return onMenuClickListener;
//    }
//
//    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
//        this.onMenuClickListener = onMenuClickListener;
//    }
    
    
}
