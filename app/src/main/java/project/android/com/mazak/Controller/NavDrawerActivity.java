package project.android.com.mazak.Controller;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import project.android.com.mazak.Database.Database;
import project.android.com.mazak.Database.Factory;
import project.android.com.mazak.Database.LoginDatabase;
import project.android.com.mazak.Model.Entities.Delegate;
import project.android.com.mazak.Model.Entities.GradesList;
import project.android.com.mazak.Model.IRefresh;
import project.android.com.mazak.Model.Services.LoginService;
import project.android.com.mazak.Model.getOptions;
import project.android.com.mazak.R;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int settingsId = 15;
    String username, password;
    String myEmailAdd = "yigalyairn@gmail.com";
    Menu menu;
    FrameLayout frame;
    Database db;
    IRefresh currentFragment;
    LoginDatabase loginDatabase;
    AdView adView;
    public static NavDrawerActivity current;
    private boolean fromSettings,
            fromWeb;
    private AsyncTask<Void, Void, Void> getGrades;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current = this;
        setContentView(R.layout.activity_nav_drawer);
        Intent toLogin = new Intent(NavDrawerActivity.this, LoginService.class);
        toLogin.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startService(toLogin);
        //starting service and alarm
        //startService(new Intent(this, startAlarmService.class));
        //grades = getGradesFromIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //AsyncGetGrades();
        //adding to the nav drawer in runtime
        menu = navigationView.getMenu();
        //menu.add();

        setupNavigationDrawerItems(menu);


    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            adView = (AdView) findViewById(R.id.adView);
            AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
            // Optionally populate the ad request builder.
            adRequestBuilder.addKeyword("Cars");
            adView.loadAd(adRequestBuilder.build());
            fromSettings = false;
            pb = (ProgressBar) findViewById(R.id.spinner);
            frame = (FrameLayout) findViewById(R.id.frameNav);
            fromWeb = getIntent().getBooleanExtra("refresh", false);
            loginDatabase = LoginDatabase.getInstance(this);
            HashMap<String, String> data = loginDatabase.getLoginDataFromMemory();
            username = data.get("username");
            password = data.get("password");
            db = Factory.getInstance(username, password, current);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }



        //this checkup if for opening the appeals fragment from the service.
        String whereToNav = getIntent().getStringExtra("fragment");
        if(whereToNav != null) {
            //came from service.
            if (whereToNav.equals("appeals"))
                onNavigationItemSelected(menu.findItem(R.id.irurs));
                //activity starts normally.
            else
                onNavigationItemSelected(menu.findItem(R.id.allitem));
        } else
            onNavigationItemSelected(menu.findItem(R.id.allitem));
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
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.action_refresh){
            if(currentFragment != null)
                currentFragment.Refresh();
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.allitem: // grades
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                bundle.putString("password",password);
                navigateTo(new FatherTab(),"Grades",bundle);
                break;
            case R.id.irurs: // appeals
                navigateTo(new IrurFragment(),"Appeals",null);
                break;
            case R.id.feedback_menu_item: // feedback
                sendFeedbackWithLog();
                break;
            case R.id.avgItem: // average
                navigateTo(new AverageFragment(),"Average",null);
                break;
            default: //settings
                fromSettings = true;
                startActivity(new Intent(NavDrawerActivity.this, SettingsActivity.class));
                break;
        }

        /*else if(id == R.id.menu_item_Schedule){
            navigateTo(new ScheduleFragment(),"Schedule",null);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendFeedbackWithLog() {
        File outputFile = new File(Environment.getExternalStorageDirectory(),
                "logcat.txt");
        try {
            Runtime.getRuntime().exec(
                    "logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setType("text/plain");
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {myEmailAdd });
        intent.putExtra(Intent.EXTRA_SUBJECT, "MazakGrades");
        intent.putExtra(Intent.EXTRA_TEXT, " ");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));
        startActivity(Intent.createChooser(intent , "Send Feedback :"));

/*        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.fromParts(
                "mailto", myEmailAdd, null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));*/
    }

    private void showException(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void toggleSpinner(boolean toggle, View toDismiss, ProgressBar toShow) {
        if (toggle) {
            toDismiss.setVisibility(View.GONE);
            //swipeRefreshLayout.setRefreshing(true);
        } else {
            toDismiss.setVisibility(View.VISIBLE);
            //swipeRefreshLayout.setRefreshing(false);
        }
    }

/*    private void initSearchView(SearchView search) {

        //set the search view to the right side.
        search.setLayoutParams(new Toolbar.LayoutParams(Gravity.RIGHT));
        search.setMaxWidth(Integer.MAX_VALUE);

        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                currentFragment.clearFilter();
                return false;
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (currentFragment != null)
                    currentFragment.Filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (currentFragment != null)
                    currentFragment.Filter(newText);
                return false;
            }
        });
    }*/

    void navigateTo(Fragment fgmt, String title,Bundle bundle) {
        fgmt.setArguments(bundle);
        currentFragment = (IRefresh) fgmt;
        getSupportFragmentManager().beginTransaction().replace(R.id.frameNav, fgmt).commit();
        this.setTitle(title);
    }

    /**
     * Resetes and setup all Navigation drawer items (Year1, Year2 Year3)...
     * @param menu
     */
    void setupNavigationDrawerItems(Menu menu) {
        //clear the nav drawer
        menu.clear();
        //add the All Tab
        menu.add(R.id.group1,R.id.allitem,Menu.NONE,"Grades").setIcon(R.mipmap.nav_year_icons);
/*        //Add all years tab
        for (int i = 0; i < grades.size(); i++)
            menu.add(R.id.group2, Menu.NONE, Menu.NONE, getYearTitle(i + 1)).setIcon(R.mipmap.all_tab_icon);*/
        menu.add(R.id.group1,R.id.irurs,Menu.NONE,"Appeals").setIcon(R.mipmap.all_tab_icon);

        menu.add(R.id.group1, R.id.avgItem, Menu.NONE, "Average").setIcon(R.mipmap.icon_student);

       // menu.add(R.id.group2,R.id.menu_item_Schedule,Menu.NONE,"Schedule").setIcon(R.mipmap.all_tab_icon);

        //add settings tab.
        menu.add(R.id.group4, settingsId, Menu.NONE, "Settings").setIcon(R.mipmap.icon_settings);



        //Feedback
        menu.add(R.id.group5, R.id.feedback_menu_item, Menu.NONE, "Feedback").setIcon(R.mipmap.icon_email);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //notify to the alarm to run after the app got killed.
        //sendBroadcast(new Intent("action.APP_KILLED"));
    }

    public void onEmailClick(View view) {
        sendFeedbackWithLog();
    }

    //region commented

    //region commented
    /*    private void setupLists(GradesList object) {
        GradesList l = object;
        list = new GradesList();
        list.clear();
        list.reverse();
        list.addAll(l);
        original.clear();
        original.addAll(l);
    }*/

    /*    private void navigateToAllFragment() {
       GradesList all = new GradesList();
        for (int i = 1; i <= grades.size(); i++)
            for (int j = 0; j < grades.get(i).size(); j++) {
                Object var1 = grades.get(i);
               GradesList var2 = ((HashMap<Integer,GradesList>) var1).get(j);
                all.addAll(var2);
            }
        Fragment whole = new gradesViewFragment();
        //all.reverse();
        Bundle list = new Bundle();
        //list.putSerializable("list", all);
        //putDelegate(whole,list);
        putYear(whole,list,0);
        whole.setArguments(list);
        try {
            currentFragment = (ISearch) whole;
        } catch (Exception ex) {
            showException(ex.getMessage());
        }
        navigateTo(whole, "All",null);
    }*/
// endregion
    public void AsyncGetGrades(final Delegate after) {
        //final SearchView search = (SearchView) findViewById(R.id.searchView);
        toggleSpinner(true, frame, pb);
        this.setTitle("Loading...");
        //search.setEnabled(false);
        try {
            GradesList lst;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //region async
        getGrades = new AsyncTask<Void, Void, Void>() {
            String errorMsg;
            GradesList lst;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    //db.clearDatabase();
                    if (fromWeb)
                        lst = db.getGrades(getOptions.fromWeb);
                    else
                        lst = db.getGrades(getOptions.fromMemory);
                    if(isCancelled())
                        return null;
                } catch (Exception e) {
                    errorMsg = e.getMessage();
                    if (!errorMsg.equals(String.valueOf(R.string.no_grades_error)))
                        errorMsg = "Wrong Username or Password";
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (errorMsg == null)
                    after.function(lst);
                else {
                    showException(errorMsg);
                    finish();
                }
            }
        };
        getGrades.execute();
        //endregion
    }

    private void onConnectionFinished(GradesList object) {
        try {
            toggleSpinner(false, frame, pb);
            //search.setEnabled(true);
            setupNavigationDrawerItems(menu);
            //select the all fragment first.
            onNavigationItemSelected(menu.findItem(R.id.allitem));
        } catch (Exception ex) {
            showException(ex.getMessage());
        }

    }

    /*    private void putYear(Fragment fgmt,Bundle b,int year){
        b.putInt("year",year);
    }*/

    /*    int getYearNum(String title) {
        for (int i = 1; i < 10; i++)
            if (title.equals(getYearTitle(i)))
                return i;
        return 1;
    }*/
    /*    String getYearTitle(int year) {
        String title = "";
        switch (year) {
            case 1:
                title = "First Year";
                break;
            case 2:
                title = "Second Year";
                break;
            case 3:
                title = "Third Year";
                break;
            case 4:
                title = "Fourth Year";
                break;
            case 5:
                title = "Fifth Year";
                break;
            default:
                title = "Other";
                break;
        }
        return title;
    }*/

    //endregion


}
