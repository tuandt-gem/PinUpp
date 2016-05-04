package australia.godoer.pinupp.Views.Profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;

import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Views.LoginActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileListFragment.OnListFragmentInteractionListener {

    public static final String PROFILE_OBJ_KEY = "current_profile";
    public static User current_user;
    public static final String PROFILE_KEY = "profile_id";
    public static final String PROFILE_CHANGE = "profile_change";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_user = new User(ParseUser.getCurrentUser());
        ParseUser.getCurrentUser().saveInBackground();

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        Menu nav_menu = navigationView.getMenu();
//        //MenuItem navItem = (MenuItem) findViewById(R.id.nav_profile_menu_item_head);
//        //Menu nav_menu = navItem.getSubMenu();
//        for(int i =0, count = current_user.getPROFILE_MAP().size(); i < count;i ++){
//            nav_menu.add("runtime profile "+ i);
//            nav_menu.getItem(i+3).setIcon(R.drawable.ic_menu_gallery);
//        }
//
//
//        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
//            final View child = navigationView.getChildAt(i);
//            if (child != null && child instanceof ListView) {
//                final ListView menuView = (ListView) child;
//                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
//                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
//                wrapped.notifyDataSetChanged();
//            }
//        }

        if(current_user.getPROFILE_MAP().size() == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NewProfileFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileListFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        if(ParseUser.getCurrentUser() !=null){
            ((TextView)findViewById(R.id.nav_header_user_name)).setText(current_user.getFullName());
            ((TextView)findViewById(R.id.nav_header_email)).setText(current_user.getEmail());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            if(ParseUser.getCurrentUser() != null)
                 ParseUser.logOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //if (id == R.id.nav_camera) {
            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {

        //} else
        if (id == R.id.nav_logoff) {
            //TODO @naveen clear profile items
            if(ParseUser.getCurrentUser() != null){
                ParseUser.logOut();
                current_user = null;
            }

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(int item_position) {
        Bundle args = new Bundle();
        args.putInt(PROFILE_KEY, item_position);
        ProfileHomeFragment frag = new ProfileHomeFragment();
        frag.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).addToBackStack(null).commit();
    }

    @Override
    public void onStop(){
        User.saveUserLocal(getPreferences(Context.MODE_PRIVATE),current_user);
        super.onStop();
    }
}
