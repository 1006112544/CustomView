package com.daobao.asus.customview.PassWordEditText;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/8/24.
 */
public class PassWordEditRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ItemCount = 12;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final int IMAGE_BUTTON = 1;
    private final int TEXT_BUTTON = 2;
    private OnButtonClickListener mOnButtonClickListener;
    public enum ButtonType{
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NIGHT,
        FINISH,
        DELETE
    }

    public PassWordEditRvAdapter(Context context){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.mOnButtonClickListener = onButtonClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType!=IMAGE_BUTTON){
            return new TextViewHolder
                    (mLayoutInflater.inflate(R.layout.item_password_textview,parent,false));
        }
        return new ImageViewHolder
                (mLayoutInflater.inflate(R.layout.item_password_imageview,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(position!=ItemCount-1){
            TextViewHolder mHolder = (TextViewHolder) holder;
            if(position<9){
                mHolder.mPassWordButton.setText(String.valueOf(position+1));
            }else if(position==9){
                mHolder.mPassWordButton.setText("完成");
            }else if(position==10){
                mHolder.mPassWordButton.setText("0");
            }
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnButtonClickListener!=null){
                        ButtonType type;
                        switch (position){
                            case 0:
                                type = ButtonType.ONE;
                                break;
                            case 1:
                                type = ButtonType.TWO;
                                break;
                            case 2:
                                type = ButtonType.THREE;
                                break;
                            case 3:
                                type = ButtonType.FOUR;
                                break;
                            case 4:
                                type = ButtonType.FIVE;
                                break;
                            case 5:
                                type = ButtonType.SIX;
                                break;
                            case 6:
                                type = ButtonType.SEVEN;
                                break;
                            case 7:
                                type = ButtonType.EIGHT;
                                break;
                            case 8:
                                type = ButtonType.NIGHT;
                                break;
                            case 9:
                                type = ButtonType.FINISH;
                                break;
                            case 10:
                                type = ButtonType.ZERO;
                                break;
                            default:
                                type = ButtonType.ZERO;
                                break;

                        }
                        mOnButtonClickListener.onButtonClick(type);
                    }
                }
            });
        }else {
            ImageViewHolder mHolder = (ImageViewHolder) holder;
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnButtonClickListener!=null){
                        mOnButtonClickListener.onButtonClick(ButtonType.DELETE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ItemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if(position!=ItemCount-1){
            return TEXT_BUTTON;
        }
        return IMAGE_BUTTON;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{
        private TextView mPassWordButton;
        public TextViewHolder(View itemView) {
            super(itemView);
            mPassWordButton = itemView.findViewById(R.id.item_password_tv);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView mDeleteImg;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mDeleteImg = itemView.findViewById(R.id.item_password_img);
        }
    }

    public interface OnButtonClickListener{
        void onButtonClick(ButtonType type);
    }

}
