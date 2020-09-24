package com.helios.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements frag1.flag{

    int holdscrolly=0;
    Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        toolbar= findViewById(R.id.toolbar);
        setupToolbar();
        //setting up toolbar with navigation
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar,navController,mAppBarConfiguration);
    }

    public void setupToolbar(){
        //inflating the toolbar
        toolbar.inflateMenu(R.menu.title_menu);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.title));
        //setting listener to menu items
        Toolbar.OnMenuItemClickListener listenToToolbarItems = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.share)
                {
                    String text = "I am Listening Stories On Radio Stories";
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                    sendIntent.setType("text/plain");
                    Intent chooser = Intent.createChooser(sendIntent,"Choose Your Platform");
                    startActivity(chooser);
                }
                return true;
            }
        };
        toolbar.setOnMenuItemClickListener(listenToToolbarItems);
    }


    public void NestedScrollHandle(){
        NestedScrollView nestedScrollView = findViewById(R.id.nested_scroll);

    }
    @Override
    public void onStart(){
        super.onStart();
        final Context context = this;
        final NestedScrollView nestedScrollView = findViewById(R.id.nested_scroll);
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator);
                String t  = coordinatorLayout.doViewsOverlap(findViewById(R.id.app_bar),findViewById(R.id.nav_host_fragment)) ? "true":"false";
                Log.i("do views overlap---->", t);
                Log.i("nested scroll---->", nestedScrollView.getScrollY()+" ");
                Log.i("coordinator height---->", findViewById(R.id.coordinator).getHeight()+" ");
                Log.i("nestedScrollHeight---->", findViewById(R.id.nested_scroll).getHeight()+" ");
                Log.i("appbar height---->", findViewById(R.id.app_bar).getHeight()+" ");
        }
    });
    }

    @Override
    public boolean check(int innerLinearLayoutSize) {

        if((findViewById(R.id.nested_scroll).getScrollY() - holdscrolly) > innerLinearLayoutSize )
        {
            holdscrolly = findViewById(R.id.nested_scroll).getScrollY();
            return true;
        }
        return false;
    }

}