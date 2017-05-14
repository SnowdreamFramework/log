package com.github.snowdream.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.snowdream.util.log.ILog;
import com.github.snowdream.util.log.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //testLogWithTrunk();
                //jsonLogTest();
                //xmlLogTest();

                tagfileTest();

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
    }

    private void testLogWithTrunk() {
        ILog logger = Log.getLogger(getApplicationContext(), true, true);

        StringBuffer buffer = new StringBuffer();
        int ret;
        for (int i = 0; i <= 4000; i++) {
            if (i == 4000) {
                ret = 2;
            } else {
                ret = 1;
            }

            buffer.append(ret);
        }

        logger.i("ILog", buffer.toString());
    }

    private void testLogs() {
        ILog logger = Log.getLogger(getApplicationContext(), true, true);

        for (int i = 0; i <= 100000; i++) {
            logger.i("ILog", String.valueOf(i));
        }
    }

    private void jsonLogTest(){
        String jsonString = "{\"name\": \"Elvis\", \"age\": 18}";

        ILog logger = Log.getLogger(getApplicationContext(), true, true);

        logger.json("ILog", jsonString);
    }


    private void xmlLogTest(){
        String xmlString = "<team><member name=\"Elvis\"/><member name=\"Leon\"/></team>";


        ILog logger = Log.getLogger(getApplicationContext(), true, true);

        logger.xml("ILog", xmlString);
    }


    private void tagfileTest() {
        String xmlString = "<team><member name=\"Elvis\"/><member name=\"Leon\"/></team>";

        ILog logger = Log.getLogger(getApplicationContext(), true, true);

        for (int i = 0; i <= 10; i++) {
            logger.i("ILog_"+i, xmlString);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
