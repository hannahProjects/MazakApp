package project.android.com.mazak.Database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import project.android.com.mazak.Model.Entities.CourseStatistics;
import project.android.com.mazak.Model.Entities.Grade;
import project.android.com.mazak.Model.Entities.GradesList;
import project.android.com.mazak.Model.Entities.IrurList;
import project.android.com.mazak.Model.Entities.NameValuePair;
import project.android.com.mazak.Model.Entities.NotebookList;
import project.android.com.mazak.Model.Entities.ScheduleList;
import project.android.com.mazak.Model.Entities.TestList;
import project.android.com.mazak.Model.Entities.getOptions;
import project.android.com.mazak.Model.GradesModel;
import project.android.com.mazak.Model.HtmlParser;
import project.android.com.mazak.Model.Web.ConnectionData;
import project.android.com.mazak.Model.Web.MazakConnection;
import project.android.com.mazak.R;

/**
 * Created by Yair on 2017-02-17.
 * This class is the device database handler.
 */

public class InternalDatabase implements Database {
    private Context activity;
    private static final String filename = "Database.dat";
    private static final String updatesFileName = "updates.dat";
    public static final String gradesKey = "grades";
    public static final String IrursKey = "irurs";
    public static final String ScheduleKey = "schedules";
    public static final String TestKey = "tests";
    public static final String NotebookKey = "notebooks";
    private MazakConnection connection;
    private String currentUsername = "",currentPassword="";
    private GradesList grades;
    private IrurList irurs;
    private ScheduleList schedule;
    private TestList tests;
    private NotebookList notebooks;



    public InternalDatabase(Context ctx) {
        activity = ctx;
    }

    /**
     * checks if the connection is out and then refresh it.
     * @throws IOException
     */
    private void refreshConnection() throws IOException {
 /*       String username = LoginDatabase.getInstance(activity).getLoginDataFromMemory().get("username");
        String passw = LoginDatabase.getInstance(activity).getLoginDataFromMemory().get("password");
        if (connection == null || !username.equals(currentUsername) || !passw.equals(currentPassword)) {
            currentUsername = username;
            currentPassword = passw;
            connection = new MazakConnection(currentUsername, currentPassword);
        }*/
    }

    public  MazakConnection getConnection() throws IOException {
        String username = LoginDatabase.getInstance(activity).getLoginDataFromMemory().get("username");
        String passw = LoginDatabase.getInstance(activity).getLoginDataFromMemory().get("password");
        return new MazakConnection(username,passw);
    }


    /**
     * deletes all the data from the database.
     */
    @Override
    public void clearDatabase() {
        deleteGrades();
        deleteIrurs();
        clearScheudle();
        clearTests();
        clear(NotebookKey,notebooks);
    }

    @Override
    public GradesList FilterByName(String name) {
        GradesList toRet = new GradesList();
        for (Grade g : grades)
            if (g.name.contains(name))
                toRet.add(g);
        return toRet;
    }

    @Override
    public void changeUsernameAndPassword(String username, String password) throws Exception {
        if (username == null || password == null)
            throw new Exception("Argument is null");
        connection = new MazakConnection(username, password);
    }

    /**
     * updates the time in database that the current data (key) was updated.
     * @param key
     */
    private void updateTime(String key){
        Date cal1 = getCurrentTime();
        SharedPreferences sharedPref = activity.getSharedPreferences(updatesFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cal1);
        editor.putString(key, json);
        editor.commit();
    }

    /**
     * reads from the device data the last saved
     * @param key
     * @return the last saved update time.
     */
    @Override
    public String getUpdateTime(String key){
        SharedPreferences sharedPref = activity.getSharedPreferences(updatesFileName, Context.MODE_PRIVATE);
        String list = sharedPref.getString(key, null);
        //got nothing from database.
        if (list == null)
            return "";
        else {
            Object lst;
            try {
                lst = (new Gson()).fromJson(list, Date.class);
            }catch (Exception ex){
                return "";
            }
            return new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm").format(lst).toString();
        }
    }

    /**
     * saves the object at the current key in the data.
     * @param toSave
     * @param key
     */
    public void save(Object toSave,String key){
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(toSave);
        editor.putString(key, json);
        editor.commit();
    }

    /**
     * delets the current key and updates the listOfItmes to be null.
     * @param key
     * @param listOfItems
     */
    public void clear(String key,Object listOfItems){
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.commit();
        listOfItems = null;
    }

    /**
     * loads the key data from the URL to the list Object.
     * @param key
     * @param URL
     * @param list
     * @return
     * @throws Exception
     */
    public Object loadFromWeb(String key,String URL,Object list) throws Exception {
        String html;
        Object listParsed;
        clear(key,list);
        refreshConnection();
        html = getConnection().connect(URL);
        listParsed = HtmlParser.Parse(html,key);
        save(listParsed,key);
        updateTime(key);
        return listParsed;
    }

    /**
     * loads the key data from the memory and returns it.
     * @param key
     * @param typeOfList
     * @return
     */
    public Object loadFromMemory(String key, Type typeOfList){
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        String list = sharedPref.getString(key, null);
        //got nothing from database.
        if (list == null)
            return null;
        else {
            Object lst;
            lst = (new Gson()).fromJson(list,typeOfList);
            return lst;
        }
    }

    /**
     * gets the key data from the options specified (if its web so from the URL) to the listOfObjects.
     * @param option the option web , or memory.
     * @param key the key that IDs the data
     * @param listOfObjects the list of objects to update
     * @param typeOfList the type of that list
     * @param URL the url to check if its web.
     * @return
     * @throws Exception
     */
    public Object get(getOptions option,String key,Object listOfObjects,Type typeOfList,String URL) throws Exception {
        Object returnedList = null;
        switch (option) {
            case fromMemory:
                returnedList =  loadFromMemory(key,typeOfList);
                break;
            case fromWeb:
                returnedList =  loadFromWeb(key,URL,listOfObjects);
                break;
        }
        if (returnedList == null)
            throw new Exception(activity.getString(R.string.web_error));
        return returnedList;
    }

    //region grades
    private void loadGradesFromInternalMemory() {
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        String list = sharedPref.getString(gradesKey, null);
        //got nothing from database.
        if (list == null)
            grades = null;
        else {
            GradesList lst;
            lst = (new Gson()).fromJson(list, GradesList.class);
            grades = lst;
        }
    }

    @Override
    public void saveGrades(GradesList grades) {
        save(grades,gradesKey);
    }

    @Override
    public GradesList getGrades(getOptions option) throws Exception {
        switch (option) {
            case fromMemory:
                loadGradesFromInternalMemory();
                break;
            case fromWeb:
                getGradesFromWeb();
                break;
        }
        if (grades == null)
            throw new Exception(activity.getString(R.string.no_grades_error));
        return grades;
    }

    @Override
    public HashMap<Integer, GradesList> getGrades(getOptions options, int year) throws Exception {
        getGrades(options);
        return GradesModel.sortBySemesterAndYear(grades).get(year);
    }

    /**
     * Getting grades from the web, taking the username and password from the database.
     * @throws Exception
     */
    private void getGradesFromWeb() throws Exception {
        String jsonData;
        deleteGrades();
        refreshConnection();
        //html = getConnection().connect(ConnectionData.GradesURL);
        ArrayList<NameValuePair> data = new ArrayList<>();
        LoadDataPost();
        data.add(new NameValuePair("selectedAcademicYear",""));
        data.add(new NameValuePair("selectedSemester",""));
        //data.add(new NameValuePair("action","searchquery"));
        data.add(new NameValuePair("pageSize","200"));
        //data.add(new NameValuePair("current","4"));
        String stringdata = ConnectionData.JsonValues(data);
        jsonData = getConnection().connectPost(ConnectionData.GradesURL+"?action=searchquery",stringdata);
        //String res = WebViewConnection.RhinoTest(html);
        grades = HtmlParser.ParseToGrades(jsonData);
        saveGrades(grades);
        updateTime(gradesKey);
    }

    private void LoadDataPost() throws Exception {
        //String query = "action=LoadData";
        String jsonData = getConnection().connectPost("https://mazak.jct.ac.il/Student/Grades.aspx"+"?action=LoadData","");
        int x = 1;
    }

    private Date getCurrentTime() {
        return new Date();
    }

    @Override
    public void deleteGrades() {
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(gradesKey);
        editor.commit();
        grades = null;
    }
    //endregion

    //region irurs
    @Override
    public IrurList getIrurs(getOptions options) throws Exception {
        switch (options) {
            case fromMemory:
                getIrursFromMemory();
                break;
            case fromWeb:
                getIrursFromWeb();
                break;
        }
        if (irurs == null)
            throw new Exception(activity.getString(R.string.no_appeals_found));
        return (IrurList) irurs.clone();
    }

    @Override
    public void deleteIrurs() {
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(IrursKey);
        editor.commit();
        irurs = null;
    }

    @Override
    public void saveIrurs(IrurList irurs) {
        save(irurs,IrursKey);
    }

    @Override
    public boolean ifNewIrurArrived() throws Exception {
        IrurList currentList = null;
        try {
            //try get the list from the memory.
            currentList = getIrurs(getOptions.fromMemory);
        } catch (Exception ex){
            //if its the first time and there was no irurs saved in the memory
            //then for this time get them from the web and save them(in the getIrursFromWeb function)
            //and finish this call.
            getIrurs(getOptions.fromWeb);
            return false;
        }
        IrurList newList = getIrurs(getOptions.fromWeb);
        if(newList.equal(currentList))
            return false;
        saveIrurs(newList);
        return true;
    }

    private void getIrursFromWeb() throws Exception {
        String html;
        deleteIrurs();
        refreshConnection();
        html = getConnection().connect(ConnectionData.IrurURL);
        //String res = WebViewConnection.RhinoTest(html);
        irurs = HtmlParser.ParseToIrurs(html);
        saveIrurs(irurs);
        updateTime(IrursKey);
    }

    private void getIrursFromMemory() {
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        String list = sharedPref.getString(IrursKey, null);
        //got nothing from database.
        if (list == null)
            irurs = null;
        else {
            IrurList lst;
            lst = (new Gson()).fromJson(list, IrurList.class);
            irurs = lst;
        }
    }
    //endregion

    //region stats
    @Override
    public CourseStatistics getStatsFromWeb(String link) throws Exception {
        refreshConnection();
        String res = getConnection().getStatisticsPage(link);
        return HtmlParser.ParseToStats(res);
    }

    //endregion

    //region schedule
    @Override
    public ScheduleList getScheudle(getOptions options, String year, String sem) throws Exception {
        switch (options) {
            case fromMemory:
                loadScheduleFromMemeory();
                break;
            case fromWeb:
                getScheudleFromWeb(year,sem);
                break;
        }
        if (schedule == null)
            throw new Exception(activity.getString(R.string.no_schedule_error));
        return schedule;
    }

    private void getScheudleFromWeb(String year,String sem) throws Exception {
        String html;
        clearScheudle();
        refreshConnection();
        ArrayList<NameValuePair> data = new ArrayList<>();
        String stringdata;

        data.add(new NameValuePair("AcademicYearID",year));
        data.add(new NameValuePair("SemesterID",sem));
        data.add(new NameValuePair("action","searchquery"));
        data.add(new NameValuePair("selectedAcademicYear",year));
        data.add(new NameValuePair("selectedSemester",sem));
        stringdata = ConnectionData.JsonValues(data);

        html = getConnection().connectPost(ConnectionData.ScheduleURL,stringdata);
        schedule = HtmlParser.ParseToClassEvents(html);
        saveScheudle(schedule);
        updateTime(ScheduleKey);
    }

    private void loadScheduleFromMemeory() {
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        String list = sharedPref.getString(ScheduleKey, null);
        //got nothing from database.
        if (list == null)
            schedule = null;
        else {
            ScheduleList lst;
            lst = (new Gson()).fromJson(list, ScheduleList.class);
            schedule = lst;
        }
    }

    @Override
    public void saveScheudle(ScheduleList schedule) {
        save(schedule,ScheduleKey);
    }

    @Override
    public void clearScheudle() {
        SharedPreferences sharedPref = activity.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(ScheduleKey);
        editor.commit();
        schedule = null;
    }

    @Override
    public ArrayList<String> getYearAndSem() throws Exception {
        String html = getConnection().connect("https://mazak.jct.ac.il/Student/Schedule.aspx");
        Document doc = Jsoup.parse(html);
        Elements el  = doc.getElementsByClass("btn btn-primary");
        String link = el.get(0).attr("href");
        String[] dets = ((link.split(Pattern.quote("?")))[1]).split("&");
        String year = (dets[0].split("="))[1];
        String sem = (dets[1].split("="))[1];
        ArrayList<String> res = new ArrayList<>();
        res.add(year);
        res.add(sem);
        return res;
    }

    //endregion

    //region tests
    @Override
    public TestList getTests(getOptions option) throws Exception {
        switch (option) {
            case fromMemory:
                tests = (TestList) loadFromMemory(TestKey,TestList.class);
                break;
            case fromWeb:
                loadTestsFromWeb();
                break;
        }
        if (tests == null)
            throw new Exception(activity.getString(R.string.no_tests_error));
        return tests;
    }

    private void loadTestsFromWeb() throws Exception {
        tests = (TestList) loadFromWeb(TestKey,ConnectionData.TestsURL,tests);
    }

    private void saveTests(TestList tests) {
        save(tests,TestKey);
    }

    private void clearTests() {
        clear(TestKey,tests);
    }

    @Override
    public NotebookList getNotebooks(getOptions options) throws Exception {
        NotebookList list = (NotebookList) get(options,NotebookKey,notebooks,NotebookList.class,ConnectionData.NotebookURL);
        return list;
    }

    //endregion
}
