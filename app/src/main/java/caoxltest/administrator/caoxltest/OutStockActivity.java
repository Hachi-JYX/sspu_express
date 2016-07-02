package caoxltest.administrator.caoxltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import caoxltest.administrator.caoxltest.bean.InfoBean;
import caoxltest.administrator.caoxltest.bean.OutInfo;
import caoxltest.administrator.caoxltest.contanst.Api;



public class OutStockActivity extends AppCompatActivity implements DefineView {

    private EditText etXh;
    private EditText etXm;
    private EditText etDh;
    private int flag = 0;
    private String cardid = "";
    private InfoBean bean;
    private Button btnOk;
    private ImageView back;
    private boolean isConnect = false;
    private TextView tvCount;
    private int count = 0;
    private String pre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_stock);
        init();
        initData();
        initListener();
    }

    private void initdata() {
        if (isConnect){
            Toast.makeText(OutStockActivity.this,"请勿操作过快",Toast.LENGTH_SHORT).show();
            return;
        }
        if (cardid == "") {
            return;
        }
        isConnect = true;
        //不是物理地址
        count = 0;
        tvCount.setVisibility(View.INVISIBLE);
        if (flag != 2){
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("CardLID",cardid);
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
                                Toast.makeText(OutStockActivity.this,"网站正在修复中",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Gson gson = new Gson();
                            bean = gson.fromJson(res,InfoBean.class);
                            if (bean!=null && bean.getRes()==200){
                                etXm.setText(bean.getCustname());
                                etXh.setText(bean.getXh());
                            }else {
                                Toast.makeText(OutStockActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                            etDh.setFocusable(true);
                            etDh.setFocusableInTouchMode(true);
                            etDh.requestFocus();
                            etDh.findFocus();
                            isConnect = false;
                        }
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onFailure(HttpException error, String msg) {
                            isConnect = false;
                        }
                    });
        }
        else {//是物理地址
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("CardPID",cardid);
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
                                Toast.makeText(OutStockActivity.this,"网站正在修复中",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Gson gson = new Gson();
                            bean = gson.fromJson(res,InfoBean.class);
                            if (bean!=null && bean.getRes()==200){
                                etXm.setText(bean.getCustname());
                                etXh.setText(bean.getXh());
                            }else {
                                Toast.makeText(OutStockActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                            etDh.setFocusable(true);
                            etDh.setFocusableInTouchMode(true);
                            etDh.requestFocus();
                            etDh.findFocus();
                            isConnect = false;
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





    @Override
    public void init() {
        etXh = (EditText) findViewById(R.id.et_xh);
        etXm = (EditText) findViewById(R.id.et_xm);
        etDh = (EditText) findViewById(R.id.et_dh);
        btnOk = (Button) findViewById(R.id.btnok);
        back = (ImageView) findViewById(R.id.iv_back);
        tvCount = (TextView) findViewById(R.id.tv_count);
    }

    @Override
    public void initData() {
        tvCount.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etXh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    task(etXh.getText().toString());
                    initdata();
                }else {
                    etXh.setText("");
                    etXm.setText("");
                    cardid="";
                }
            }
        });
        //小于11
        etDh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etDh.getText().toString() == ""){
                    return;
                }
                if (!hasFocus){
                    String dh = etDh.getText().toString();
                    if (dh.length() <= 11){
                        etDh.setText("");
                        task(dh);
                        initdata();
                    }else {
                        postInfo();
                    }
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postInfo();
            }
        });
    }

    @Override
    public void requestNet() {

    }

    public void postInfo() {
        String paramXh = etXh.getText().toString();
        String parmaDh = etDh.getText().toString();
        String paemaXm = etXm.getText().toString();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("CardLID",paramXh);
        params.addQueryStringParameter("LayoutID",parmaDh);
        params.addQueryStringParameter("CustName",paemaXm);
        HttpUtils http = new HttpUtils(10000);
        http.configCurrentHttpCacheExpiry(10);
        http.send(HttpRequest.HttpMethod.GET,
                Api.Net2,
                params,
                new RequestCallBack<String>(){
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String res = responseInfo.result;
                        if (res.contains("html")){
                            Toast.makeText(OutStockActivity.this,"服务瘫痪",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("log",res);
                        Gson gson = new Gson();
                        OutInfo outInfo = gson.fromJson(res,OutInfo.class);
                        if (outInfo.getRes() == 200){
                            count++;
                            tvCount.setText("当前用户已经取件【"+ count +"】");
                            tvCount.setVisibility(View.VISIBLE);
                        }
                        else {
                            tvCount.setText(outInfo.getMsg());
                            tvCount.setVisibility(View.VISIBLE);
                        }

                        etDh.setText("");
                        etXm.setFocusable(false);
                        etDh.setFocusable(true);
                        etDh.setFocusableInTouchMode(true);
                        etDh.requestFocus();
                        etDh.findFocus();
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
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
            cardid = s;
            etXh.setText(s);
        }else  if(text.length() == 8){//66B407DE
            flag = 2;
            cardid = text.toUpperCase();
        }else {
            flag = 3;
            cardid = text;
        }
    }
}
