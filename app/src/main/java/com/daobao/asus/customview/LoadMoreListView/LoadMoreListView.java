package com.daobao.asus.customview.LoadMoreListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.daobao.asus.customview.R;


/**
 * 上拉加载更多的listview
 * Created by Db
 */

public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private Context mContext;
    private View mFootView;
    private int mTotalItemCount;
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mIsLoading=false;
    private boolean mCanLoadMore=true;

    public LoadMoreListView(Context context) {
        this(context,null);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext=context;
        mFootView= LayoutInflater.from(context).inflate(R.layout.listview_loadmore_footview,null);
        setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        if(mCanLoadMore){
            int lastVisibleIndex=listView.getLastVisiblePosition();
            if (!mIsLoading&&scrollState == OnScrollListener.SCROLL_STATE_IDLE
                    && lastVisibleIndex ==mTotalItemCount-1) {
                mIsLoading=true;
                addFooterView(mFootView);
                if (mLoadMoreListener!=null) {
                    mLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mTotalItemCount=totalItemCount;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        mLoadMoreListener=listener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    /**
     * 本次加载完成后移除rootview
     */
    public void setLoadCompleted(){
        mIsLoading=false;
        removeFooterView(mFootView);
    }

    /**
     * 加载完成了没有更多
     */
    public void setLoadAll(){
        mIsLoading=false;
        mCanLoadMore = false;
        ((TextView)mFootView.findViewById(R.id.loadmore_text)).setText("没有更多了哦");
        (mFootView.findViewById(R.id.loadmore_pb)).setVisibility(GONE);
    }

    /**
     * 设置是否可以加载更多
     * @param canLoadMore
     */
    public void setCanLoadMore(boolean canLoadMore){
        mCanLoadMore = canLoadMore;
    }
}