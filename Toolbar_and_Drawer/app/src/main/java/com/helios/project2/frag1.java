package com.helios.project2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import   androidx.core.widget.NestedScrollView;

class createCardView
{
    public static androidx.cardview.widget.CardView  cardView(Context context) {
        //  String tag = "cardView"+i;
        androidx.cardview.widget.CardView card;
        card = new androidx.cardview.widget.CardView(context);
        //card.setTag(tag);
        card.setRadius(4);
        return card;
    }
    public static LinearLayout.LayoutParams params(){
        LinearLayout.LayoutParams outerLinearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        return  outerLinearLayoutParams;
    }
}

class createLinearLayout{
    public static LinearLayout linearLayout(Context context,int i){
        String tag = "innerLinearLayout"+i;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setTag(tag);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return  linearLayout;
    }
    public static LinearLayout.LayoutParams params(){
        LinearLayout.LayoutParams innerLinearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        return  innerLinearLayoutParams;
    }
}


class createToolbar{
    public static Toolbar toolbar(final Context context, int i){
        String tag = "toolbar"+i;
        Toolbar toolbar;
        toolbar = new Toolbar(context);
        toolbar.setTag(tag);
        toolbar.inflateMenu(R.menu.playbar);
        toolbar.setTitle("Sunday Suspense");
        toolbar.setTitleTextAppearance(context,R.style.ToolbarTheme);
        Toolbar.OnMenuItemClickListener listenToToolbarItems = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.download)
                {
                    CharSequence text = "Download will be ready soon";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                return true;
            }
        };
        toolbar.setOnMenuItemClickListener(listenToToolbarItems);

        return toolbar;
    }
    public static Toolbar.LayoutParams params(){
        Toolbar.LayoutParams innerLinearLayoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        innerLinearLayoutParams.gravity = Gravity.BOTTOM;
        return  innerLinearLayoutParams;
    }
}

class createImageView{
    public static ImageView imageView(frag1 ob, final Context context, int i){
        final String tag = "img"+i;
        int val_drawable_image[]={R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6};
        ImageView imageView = new ImageView(context);
        imageView.setTag(tag);
        imageView.setImageResource(val_drawable_image[i]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(ob);
        return imageView;
    }

    public static LinearLayout.LayoutParams params(){
        LinearLayout.LayoutParams params_for_image_view = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 500);
        return params_for_image_view;
    }
}


public class frag1 extends Fragment implements View.OnClickListener{

     public static interface flag{
      boolean check(int innerLinearLayoutSize);
    }
    static  int countImageViews;
    flag ob_flag;
    View view;
    int n;
    public frag1() {
        countImageViews=0;
        n=4;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
    addElements();
    }


    public void addElements(){
        frag1 ob = this;
        getView().findViewById(R.id.nested_scroll);
        LinearLayout outerLineaLayout = (LinearLayout)(getView());
        LinearLayout innerLinearLayout;
        Context context = getContext();
        androidx.cardview.widget.CardView card;
        for(int i=0;i<n;i++) {
            countImageViews++;
            innerLinearLayout = createLinearLayout.linearLayout(context,i);
            innerLinearLayout.addView(createImageView.imageView(ob,context, i), createImageView.params());
            innerLinearLayout.addView(createToolbar.toolbar(context,i), createToolbar.params());
            card = createCardView.cardView(context);
            card.addView(innerLinearLayout, createLinearLayout.params());
            outerLineaLayout.addView(card, createCardView.params());
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.ob_flag = (flag)activity;
    }
    @Override
    public void onClick(View view) {
        String tag = (String)view.getTag();
        final Context context = getContext();
        CharSequence text = tag+" ";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onPause(){
        super.onPause();
        //String tag = (String)((ImageView) view).getTag();
        final Context context = getContext();
        CharSequence text = String.valueOf("paused "+n);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void onResume(){
        super.onResume();
        final Context context = getActivity();
        CharSequence text = String.valueOf("resumed "+n);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        keepChecking();
    }
    private void keepChecking() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean x = ob_flag.check(view.findViewWithTag("innerLinearLayout1").getHeight()-10);
              // Log.i("Add more-->",countImageViews+" "+view.findViewById(R.id.outerLinearLayout).getHeight());
               //Log.i("Add more-->",countImageViews+" "+view.findViewWithTag("innerLinearLayout1").getHeight());
                if(x){
                    n=1;
                    addElements();
                    addElements();
                    addElements();
                    addElements();
                    //Log.i("Add more-->","true ");
                }
                else {
                   // Log.i("Add more-->", "false");
                }
                keepChecking();
            }
        };
        Handler handler = new Handler();
        handler.post(runnable);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        countImageViews=0;
    }
}

