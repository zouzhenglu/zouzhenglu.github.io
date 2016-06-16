package com.zzl.demo.reverserv;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import junit.framework.Assert;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        Assert.assertNotNull(rv);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(true);
        layout.setReverseLayout(true);
        rv.setLayoutManager(layout);

        final SimpleRvAdapter adapter = new SimpleRvAdapter(this);
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");
        adapter.notifyDataSetChanged();
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.add(String.valueOf(adapter.getItemCount() + 1));
                    adapter.notifyDataSetChanged();

                }
            });
        }
    }
}
