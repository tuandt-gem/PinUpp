package australia.godoer.pinupp.Models;


import android.net.Uri;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by naveen on 3/23/2016.
 */
public class Jobs {

    private HashMap<Integer,Job> job_list = new HashMap<>();
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<Integer, Job> getJob_list() {
        return job_list;
    }

    public void setJob_list(HashMap<Integer, Job> job_list) {
        this.job_list = job_list;
    }

    public static class Job {
        private String title;
        private String company_name;
        private String month;
        private String year;
        private String image_uri;
        //TODO store company logo

        public Job(String title, String company_name, String month, String year, String uri_img) {
            this.title = title;
            this.company_name = company_name;
            this.month = month;
            this.year = year;
            this.image_uri = uri_img;
        }

        public String getImage_uri() {
            return image_uri;
        }

        public void setImage_uri(String image_uri) {
            this.image_uri = image_uri;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
