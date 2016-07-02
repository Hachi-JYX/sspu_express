package caoxltest.administrator.caoxltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rvLayout1;
    RelativeLayout rvLayout2;
    RelativeLayout rvLayout3;
    RelativeLayout rvLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        initListener();
        initData();
    }

    private void initData() {

    }

    private void init() {
        rvLayout1 = (RelativeLayout) findViewById(R.id.rv_item1);
        rvLayout2 = (RelativeLayout) findViewById(R.id.rv_item2);
        rvLayout3 = (RelativeLayout) findViewById(R.id.rv_item3);
        rvLayout4 = (RelativeLayout) findViewById(R.id.rv_item4);
    }

    private void initListener() {
        rvLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OutStockActivity.class));
            }
        });

        rvLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

        rvLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
            }
        });

        rvLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
