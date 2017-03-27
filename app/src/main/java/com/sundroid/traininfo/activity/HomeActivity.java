package com.sundroid.traininfo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sundroid.traininfo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    ActionBarDrawerToggle drawerToggle;
    @BindView(R.id.nvView)
    NavigationView nvDrawer;
    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.gridview_menu)
    GridView gridview_menu;


    List<Integer> list_item_images=new ArrayList<>();
    List<String> list_item_text=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setUpLeftNavigationDrawer();

        setUpGridContent();
//        Log.d(TAG,"resp:-"+resp);
//        try{
//            Gson gson=new Gson();
//            TrainAutocompletePOJO pojo=gson.fromJson(resp,TrainAutocompletePOJO.class);
//            Log.d(TAG,"train_autocomplete:-"+pojo.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    private final String TAG=getClass().getSimpleName();
    public void setUpGridContent(){
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);
        list_item_images.add(R.drawable.ic_launcher);

        list_item_text.add("Live Train Status");
        list_item_text.add("PNR status");
        list_item_text.add("Train Route");
        list_item_text.add("Seat Availability");
        list_item_text.add("Train Between Station");
        list_item_text.add("Train Name/Number");
        list_item_text.add("Train Fair Enquiry");
        list_item_text.add("Train Arrival at station");
        list_item_text.add("Cancelled Train");
        list_item_text.add("Station Name To Code");
        list_item_text.add("Station Code To Name");
        list_item_text.add("Station AutoComplete");
        list_item_text.add("Train AutoComplete");

        GridAdapter adapter = new GridAdapter(this, list_item_images, list_item_text);

        gridview_menu.setAdapter(adapter);
        gridview_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(HomeActivity.this, "You Clicked at "+position , Toast.LENGTH_SHORT).show();
                GridItemClick(position);
            }
        });
    }
    public void GridItemClick(int position){
        switch (position){
            case 0:
                startActivity(new Intent(HomeActivity.this,LiveTrainStatusActivity.class));
                break;
            case 1:
                startActivity(new Intent(HomeActivity.this,PNRStatusActivity.class));
                break;
            case 2:
                startActivity(new Intent(HomeActivity.this,TrainRouteActivity.class));
                break;
            case 3:
                startActivity(new Intent(HomeActivity.this,SeatAvailability.class));

                break;
            case 4:
                startActivity(new Intent(HomeActivity.this,TrainBetweenStationActivity.class));

                break;
            case 5:
                startActivity(new Intent(HomeActivity.this,TrainNameNumberActivity.class));
                break;
            case 6:
                startActivity(new Intent(HomeActivity.this,TrainFareEnquiryActivity.class));

                break;
            case 7:
                startActivity(new Intent(HomeActivity.this,TrainArrivalAtStationActivity.class));
                break;
            case 8:
                startActivity(new Intent(HomeActivity.this,CancelledTrainActivity.class));
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;

        }
    }


    public void setUpLeftNavigationDrawer() {
        setupDrawerContent(nvDrawer);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer.setItemIconTintList(null);
        setFirstItemNavigationView();
//        drawerToggle.setDrawerIndicatorEnabled(false);
        setUpLeftHeaderLayout();
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }
    public void setUpLeftHeaderLayout(){
        View headerLayout = nvDrawer.inflateHeaderView(R.layout.inflate_home_left_header);

        CircleImageView ic_left_profile= (CircleImageView) headerLayout.findViewById(R.id.ic_left_profile);
        TextView tv_profile_name= (TextView) headerLayout.findViewById(R.id.tv_profile_name);

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    private void setFirstItemNavigationView() {
        nvDrawer.setCheckedItem(R.id.menu_home);
        nvDrawer.getMenu().performIdentifierAction(R.id.menu_home, 0);
    }
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                break;
            case R.id.menu_about:
                break;
            case R.id.menu_help:
                break;
            case R.id.menu_settings:
                break;
            default:
        }

        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(nvDrawer);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    class GridAdapter extends BaseAdapter{

        Activity activity;
        List<Integer> list_images;
        List<String> list_item_string;
        public GridAdapter(Activity context, List<Integer> list_images,List<String> list_item_text) {
            this.activity = context;
            this.list_images=list_images;
            this.list_item_string=list_item_text;
        }

        @Override
        public int getCount() {
            return list_images.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(activity);
                grid = inflater.inflate(R.layout.inflate_home_grid_items, null);
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
                textView.setText(list_item_string.get(i));
                imageView.setImageResource(list_images.get(i));
            } else {
                grid = (View) convertView;
            }

            return grid;
        }
    }

    String resp="{\n" +
            "  \"response_code\": 200,\n" +
            "  \"trains\": [\n" +
            "    {\n" +
            "      \"name\": \"KANCHANJANGA EX\",\n" +
            "      \"number\": \"25657\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANCHANJUNGA EX\",\n" +
            "      \"number\": \"25658\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAJ KIR PASSENGER\",\n" +
            "      \"number\": \"55728\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAP LKO PASSENGER\",\n" +
            "      \"number\": \"64214\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAP LKO PASSENGER\",\n" +
            "      \"number\": \"64216\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAWR PERN PASSENGER\",\n" +
            "      \"number\": \"70102\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAWR PERN PASSENGER\",\n" +
            "      \"number\": \"70104\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAMAYANI EXPRES\",\n" +
            "      \"number\": \"11071\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAMAYANI EXPRES\",\n" +
            "      \"number\": \"11072\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAIFIYAT EXP\",\n" +
            "      \"number\": \"12225\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAIFIYAT EXP\",\n" +
            "      \"number\": \"12226\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALKA MAIL\",\n" +
            "      \"number\": \"12312\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARNATAKA EXP\",\n" +
            "      \"number\": \"12627\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARNATAKA EXP\",\n" +
            "      \"number\": \"12628\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANYAKUMARI EXP\",\n" +
            "      \"number\": \"12633\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANYAKUMARI EXP\",\n" +
            "      \"number\": \"12634\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANNYAKUMARI EX\",\n" +
            "      \"number\": \"12665\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KACHEGUDA EXP\",\n" +
            "      \"number\": \"12786\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARNAVATI EXP\",\n" +
            "      \"number\": \"12933\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARNAVATI EXP\",\n" +
            "      \"number\": \"12934\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAVIGURU EXPRES\",\n" +
            "      \"number\": \"12949\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAVI GURU EXP\",\n" +
            "      \"number\": \"13027\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAVI GURU EXP\",\n" +
            "      \"number\": \"13028\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANCHANKANYA EX\",\n" +
            "      \"number\": \"13149\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANCHANKANYA EX\",\n" +
            "      \"number\": \"13150\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KASHI V EXPRESS\",\n" +
            "      \"number\": \"14258\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALINDI EXPRESS\",\n" +
            "      \"number\": \"14723\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALINDI EXPRESS\",\n" +
            "      \"number\": \"14724\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANCHANJANGA EX\",\n" +
            "      \"number\": \"15657\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANCHANJUNGA EX\",\n" +
            "      \"number\": \"15658\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAMAKHYA EXPRES\",\n" +
            "      \"number\": \"15667\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAMRUP EXPRESS\",\n" +
            "      \"number\": \"15959\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAMRUP EXPRESS\",\n" +
            "      \"number\": \"15960\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAVERI  EXPRESS\",\n" +
            "      \"number\": \"16021\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAVERI EXPRESS\",\n" +
            "      \"number\": \"16022\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARAIKAL EXP\",\n" +
            "      \"number\": \"16175\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KACHEGUDA EXP\",\n" +
            "      \"number\": \"16354\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANYAKUMARI EXP\",\n" +
            "      \"number\": \"16381\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAWR YPR EXPRES\",\n" +
            "      \"number\": \"16516\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANNUR  EXP\",\n" +
            "      \"number\": \"16517\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARWAR  EXP\",\n" +
            "      \"number\": \"16523\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANYAKUMARI EXP\",\n" +
            "      \"number\": \"16526\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAGHAZNAGAR EXP\",\n" +
            "      \"number\": \"17035\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAGHAZNAGAR EXP\",\n" +
            "      \"number\": \"17036\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KACHEGUDA EXP\",\n" +
            "      \"number\": \"17604\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KACHEGUDA EXP\",\n" +
            "      \"number\": \"17616\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KACHEGUDA  EXP\",\n" +
            "      \"number\": \"17651\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANDARI EXPRESS\",\n" +
            "      \"number\": \"18001\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KANDARI EXPRESS\",\n" +
            "      \"number\": \"18002\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALINGAUTKALEXP\",\n" +
            "      \"number\": \"18478\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KAVI GURU EXP\",\n" +
            "      \"number\": \"19709\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARMABHOOMI EXP\",\n" +
            "      \"number\": \"22511\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KARMABHOOMI EXP\",\n" +
            "      \"number\": \"22512\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALKA SIMLA EXP\",\n" +
            "      \"number\": \"52453\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALKA SHTBDI\",\n" +
            "      \"number\": \"12005\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALKA SHTBDI\",\n" +
            "      \"number\": \"12006\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALKA SHTBDI\",\n" +
            "      \"number\": \"12011\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"KALKA SHTBDI\",\n" +
            "      \"number\": \"12012\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total\": 58,\n" +
            "  \"train\": [\n" +
            "    \"KANCHANJANGA EX (25657)\",\n" +
            "    \"KANCHANJUNGA EX (25658)\",\n" +
            "    \"KAJ KIR PASSENGER (55728)\",\n" +
            "    \"KAP LKO PASSENGER (64214)\",\n" +
            "    \"KAP LKO PASSENGER (64216)\",\n" +
            "    \"KAWR PERN PASSENGER (70102)\",\n" +
            "    \"KAWR PERN PASSENGER (70104)\",\n" +
            "    \"KAMAYANI EXPRES (11071)\",\n" +
            "    \"KAMAYANI EXPRES (11072)\",\n" +
            "    \"KAIFIYAT EXP (12225)\",\n" +
            "    \"KAIFIYAT EXP (12226)\",\n" +
            "    \"KALKA MAIL (12312)\",\n" +
            "    \"KARNATAKA EXP (12627)\",\n" +
            "    \"KARNATAKA EXP (12628)\",\n" +
            "    \"KANYAKUMARI EXP (12633)\",\n" +
            "    \"KANYAKUMARI EXP (12634)\",\n" +
            "    \"KANNYAKUMARI EX (12665)\",\n" +
            "    \"KACHEGUDA EXP (12786)\",\n" +
            "    \"KARNAVATI EXP (12933)\",\n" +
            "    \"KARNAVATI EXP (12934)\",\n" +
            "    \"KAVIGURU EXPRES (12949)\",\n" +
            "    \"KAVI GURU EXP (13027)\",\n" +
            "    \"KAVI GURU EXP (13028)\",\n" +
            "    \"KANCHANKANYA EX (13149)\",\n" +
            "    \"KANCHANKANYA EX (13150)\",\n" +
            "    \"KASHI V EXPRESS (14258)\",\n" +
            "    \"KALINDI EXPRESS (14723)\",\n" +
            "    \"KALINDI EXPRESS (14724)\",\n" +
            "    \"KANCHANJANGA EX (15657)\",\n" +
            "    \"KANCHANJUNGA EX (15658)\",\n" +
            "    \"KAMAKHYA EXPRES (15667)\",\n" +
            "    \"KAMRUP EXPRESS (15959)\",\n" +
            "    \"KAMRUP EXPRESS (15960)\",\n" +
            "    \"KAVERI  EXPRESS (16021)\",\n" +
            "    \"KAVERI EXPRESS (16022)\",\n" +
            "    \"KARAIKAL EXP (16175)\",\n" +
            "    \"KACHEGUDA EXP (16354)\",\n" +
            "    \"KANYAKUMARI EXP (16381)\",\n" +
            "    \"KAWR YPR EXPRES (16516)\",\n" +
            "    \"KANNUR  EXP (16517)\",\n" +
            "    \"KARWAR  EXP (16523)\",\n" +
            "    \"KANYAKUMARI EXP (16526)\",\n" +
            "    \"KAGHAZNAGAR EXP (17035)\",\n" +
            "    \"KAGHAZNAGAR EXP (17036)\",\n" +
            "    \"KACHEGUDA EXP (17604)\",\n" +
            "    \"KACHEGUDA EXP (17616)\",\n" +
            "    \"KACHEGUDA  EXP (17651)\",\n" +
            "    \"KANDARI EXPRESS (18001)\",\n" +
            "    \"KANDARI EXPRESS (18002)\",\n" +
            "    \"KALINGAUTKALEXP (18478)\",\n" +
            "    \"KAVI GURU EXP (19709)\",\n" +
            "    \"KARMABHOOMI EXP (22511)\",\n" +
            "    \"KARMABHOOMI EXP (22512)\",\n" +
            "    \"KALKA SIMLA EXP (52453)\",\n" +
            "    \"KALKA SHTBDI (12005)\",\n" +
            "    \"KALKA SHTBDI (12006)\",\n" +
            "    \"KALKA SHTBDI (12011)\",\n" +
            "    \"KALKA SHTBDI (12012)\"\n" +
            "  ]\n" +
            "}";

}
