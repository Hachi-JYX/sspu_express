package caoxltest.administrator.caoxltest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import java.util.List;
import caoxltest.administrator.caoxltest.adapter.SearchInfoAdapter;
import caoxltest.administrator.caoxltest.bean.DetailBean;
import caoxltest.administrator.caoxltest.bean.InfoBean;
import caoxltest.administrator.caoxltest.contanst.Api;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

public class SearchActivity extends AppCompatActivity implements DefineView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EditText et_info;
    private ImageView ivClean;
    private ImageView ivSearch;
    private ImageView ivBack;
    private FamiliarRecyclerView listView;
    private String id = "";
    private int page = 1;
    private int rows = 10;
    private DetailBean beens;
    private InfoBean infoBean;
    private boolean isLoadingMore = false;
    private SearchInfoAdapter adapter;
    private List<DetailBean.ElementsBean> list ;
    private LinearLayoutManager mLayoutManager;
    private View empty;
    private int flag;
    private boolean isEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        init();
        initData();
        initListener();
    }

    @Override
    public void initListener() {
        ivClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_info.setText("");
                if (adapter!=null){
                    list.clear();
                    isEmpty = false;
                    page =1;
                    listView.removeFooterView(empty);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtId = et_info.getText().toString();
                task(rtId);
                getXh();
                if (page==1){
                    requestNet();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_info.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String rtId = et_info.getText().toString();
                    task(rtId);
                    getXh();
                    if (page==1){
                        requestNet();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void requestNet() {;
        if (isEmpty){
            return;
        }
        final RequestParams params = new RequestParams();
        params.addQueryStringParameter("findParmeter",id);
        params.addQueryStringParameter("page",String.valueOf(page++));
        params.addQueryStringParameter("rows",String.valueOf(rows));
        HttpUtils http = new HttpUtils(10000);
        http.configCurrentHttpCacheExpiry(10);
        http.send(HttpRequest.HttpMethod.GET,
                Api.Net3,
                params,
                new RequestCallBack<String>(){
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String res = responseInfo.result;
                        if (res.contains("html")){
                            Toast.makeText(SearchActivity.this,"网站正在修复中",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("log",res);
                        Gson gson = new Gson();
                        beens = gson.fromJson(res,DetailBean.class);
                        if (beens!=null){
                            if (beens.getCount() == 0 && list.size() > 8){
                                page--;
                                isEmpty = true;
                                listView.addFooterView(empty);
                                return;
                            }
                            if (page==2){
                                list = beens.getElements();
                                adapter = new SearchInfoAdapter(list,SearchActivity.this);
                                listView.setAdapter(adapter);
                            }else {
                                if (list == null){
                                    Log.d("log","error");
                                    return;
                                }
                                list.addAll(beens.getElements());
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    @Override
    public void init() {
        et_info = (EditText) findViewById(R.id.et_info);
        ivClean = (ImageView) findViewById(R.id.iv_clean);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        listView = (FamiliarRecyclerView) findViewById(R.id.lv_data);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swp);
        empty = View.inflate(this, R.layout.listview_emptyr,null);

    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount -1 && dy > 0 && !isEmpty) {
                    if(isLoadingMore){
                        Log.d("log","ignore manually update!");
                    } else{
                        requestNet();//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = false;
                    }
                }
            }
        });
    }

    public void task(String text){
        if (text.length() == 0){
            return;
        }
        if (text.length() <= 4){
            String s = "00000000000" + text;
            s = s.substring(s.length()-11,s.length());
            flag = 1;
            id = s;
        }else  if(text.length() == 8){//66B407DE
            flag = 2;
            id = text.toUpperCase();
        }else {
            flag = 3;
            id = text;
        }
    }

    public void getXh(){
        if (id == "") {
            return;
        }
        //不是物理地址
        if (flag != 2){
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("CardLID",id);
            HttpUtils http = new HttpUtils(10000);
            http.configCurrentHttpCacheExpiry(10);
            http.send(HttpRequest.HttpMethod.GET,
                    Api.Net1,
                    params,
                    new RequestCallBack<String>(){
                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                        }
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String res = responseInfo.result;
                            if (res.contains("html")){
                                Toast.makeText(SearchActivity.this,"网站正在修复中",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Gson gson = new Gson();
                            infoBean = gson.fromJson(res,InfoBean.class);
                            if (infoBean!=null && infoBean.getRes()==200){
                                et_info.setText(infoBean.getXh());
                            }else {
                                Toast.makeText(SearchActivity.this,infoBean.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onFailure(HttpException error, String msg) {
                        }
                    });
        }
        else {//是物理地址
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("CardPID",id);
            HttpUtils http = new HttpUtils();
            http.send(HttpRequest.HttpMethod.GET,
                    Api.Net1,
                    params,
                    new RequestCallBack<String>(){
                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                        }

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String res = responseInfo.result;
                            if (res.contains("html")){
                                Toast.makeText(SearchActivity.this,"网站正在修复中",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Gson gson = new Gson();
                            infoBean = gson.fromJson(res,InfoBean.class);
                            if (infoBean!=null && infoBean.getRes()==200){
                                et_info.setText(infoBean.getXh());
                            }else {
                                Toast.makeText(SearchActivity.this,infoBean.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                        }
                    });
        }
    }
}
