package australia.godoer.pinupp.Models;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.ParseUser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import australia.godoer.pinupp.Views.LoginActivity;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

/**
 * Created by naveen on 2/21/2016.
 */
public class User {

    private ParseUser currentUser;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String Address;
    private String Location;
    private String website;
    private String aboutMe;
    private String availableFrom;
    private String availableIn;
    private Map<Integer, Profile> PROFILE_MAP = new HashMap<Integer, Profile>();
    // TODO naveen -- user profile image

    public static final String SAVE_SHARED_PREF_KEY = "pinuppuser";
    public static final String INTENT_KEY = "user_intent";
    public static final String PROFILE_MAP_KEY = "profilemap";
    // from signup
    public User(ParseUser p_user) {
        this.currentUser = p_user;
        if(p_user.get(LoginActivity.PARSE_LOGIN_FIRST_NAME_KEY).toString() != null &&
                p_user.get(LoginActivity.PARSE_LOGIN_LAST_NAME_KEY).toString() != null   ){
            this.firstName = p_user.get(LoginActivity.PARSE_LOGIN_FIRST_NAME_KEY).toString() ;
            this.lastName = p_user.get(LoginActivity.PARSE_LOGIN_LAST_NAME_KEY).toString();
        }
        this.email = p_user.getEmail();
        this.PROFILE_MAP = getItemMapFromParse();
    }

    public static void saveUserLocal(SharedPreferences sharedPref, User user){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson prof_json = new Gson();
        editor.putString(SAVE_SHARED_PREF_KEY, prof_json.toJson(user));
        editor.commit();
    }

    public static boolean loadUserLocal(SharedPreferences sharedPref){
        String load_json = sharedPref.getString(SAVE_SHARED_PREF_KEY, null);
        if(load_json != null) {
            HomeActivity.current_user = new Gson().fromJson(load_json, User.class);
            return true;
        }
        return false;
    }

    public static Map<Integer, Profile> getItemMapFromParse(){
        Map<Integer, Profile> tempMap = null;
        if(ParseUser.getCurrentUser().containsKey(PROFILE_MAP_KEY)){
            Type type = new TypeToken<Map<Integer, Profile>>() {}.getType();
            tempMap = new Gson().fromJson(ParseUser.getCurrentUser().get(PROFILE_MAP_KEY).toString(), type);
        }else{
            tempMap = new HashMap<>();
            ParseUser.getCurrentUser().put(PROFILE_MAP_KEY, new Gson().toJson(tempMap));
            //will save parseuser in home
        }
        return tempMap;
    }

   public static void updateProfile(Profile profToUpdate){
       Map<Integer, Profile> tempMap = getItemMapFromParse();
       if(tempMap!=null){
           if(tempMap.containsKey(profToUpdate.getId())){
               tempMap.remove(profToUpdate.getId());
           tempMap.put(profToUpdate.getId(), profToUpdate);
           }
       }
       ParseUser.getCurrentUser().put(PROFILE_MAP_KEY, new Gson().toJson(tempMap));
       ParseUser.getCurrentUser().saveInBackground();
   }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Map<Integer, Profile> getPROFILE_MAP() {

        return PROFILE_MAP;
    }

    public void setPROFILE_MAP(Map<Integer, Profile> PROFILE_MAP) {
        this.PROFILE_MAP = PROFILE_MAP;
    }

    public String getMobileno() {
        return mobileNo;
    }

    public void setMobileno(String mobileno) {
        this.mobileNo = mobileno;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableIn() {
        return availableIn;
    }

    public void setAvailableIn(String availableIn) {
        this.availableIn = availableIn;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public ParseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ParseUser currentUser) {
        this.currentUser = currentUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
