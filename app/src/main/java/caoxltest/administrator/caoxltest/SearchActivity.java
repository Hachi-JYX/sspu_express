package caoxltest.administrator.caoxltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import caoxltest.administrator.caoxltest.adapter.ItemInfoAdapter;
import caoxltest.administrator.caoxltest.bean.DetailBean;
import caoxltest.administrator.caoxltest.contanst.Api;

public class SearchActivity extends AppCompatActivity implements DefineView {

    private EditText et_info;
    private ImageView ivClean;
    private ImageView ivSearch;
    private ImageView ivBack;
    private ListView listView;
    private String id = "";
    private int page = 1;
    private int rows = 10;
    private DetailBean beens;
    private ItemInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        init();
        initListener();
    }

    @Override
    public void initListener() {
        ivClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_info.setText("");
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = et_info.getText().toString();
                requestNet();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void requestNet() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("findParmeter",id);
        params.addQueryStringParameter("page",String.valueOf(page));
        params.addQueryStringParameter("rows",String.valueOf(rows));
        HttpUtils http = new HttpUtils();
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
                        Gson gson = new Gson();
                        beens = gson.fromJson(res,DetailBean.class);
                        adapter = new ItemInfoAdapter(SearchActivity.this,beens.getElements());
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });

//        OkHttpUtils.get().url(Api.Net3)
//                .addParams("findParmeter",id)
//                .addParams("page",String.valueOf(page))
//                .addParams("rows ","10")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        if (response.contains("html")){
//                            Toast.makeText(SearchActivity.this,"网站正在修复中",Toast.LENGTH_SHORT).show();
//                        }
//                        Gson gson = new Gson();
//                        beens = gson.fromJson(response,DetailBean.class);
//                        adapter = new ItemInfoAdapter(SearchActivity.this,beens.getElements());
//                        listView.setAdapter(adapter);
//                    }
//                });
    }

    @Override
    public void init() {
        et_info = (EditText) findViewById(R.id.et_info);
        ivClean = (ImageView) findViewById(R.id.iv_clean);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        listView = (ListView) findViewById(R.id.lv_data);
    }

    @Override
    public void initData() {

    }
}
