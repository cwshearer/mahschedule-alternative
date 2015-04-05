package se.k3.isak.mahschedule;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by isak on 2015-04-05.
 */
public class SearchableActivity extends Activity {


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(MainActivity.TAG, "onStart");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG, "onCreate");
        handleIntent(getIntent());
    }


    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Log.i(MainActivity.TAG, "onCreateView");
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(MainActivity.TAG, "onNewIntent");
    }

    void handleIntent(Intent intent) {
        Log.i(MainActivity.TAG, "handle intent");
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i(MainActivity.TAG, query);
        }
    }
}
