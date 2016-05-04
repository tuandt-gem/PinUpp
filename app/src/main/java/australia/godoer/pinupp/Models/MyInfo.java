package australia.godoer.pinupp.Models;

/**
 * Created by naveen on 3/3/2016.
 */
public class MyInfo {

    private String title;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String Address;
    private String Location;
    private String email;
    private String website;
    private String aboutMe;
    private String availableFrom;
    private String availableDate;

    public MyInfo(){
    }

    public MyInfo(String firstName, String lastName, String mobileNo, String address, String location,
                  String email, String website, String aboutMe, String availableFrom, String availableDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.Address = address;
        this.Location = location;
        this.email = email;
        this.website = website;
        this.aboutMe = aboutMe;
        this.availableFrom = availableFrom;
        this.availableDate = availableDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }
}
