package australia.godoer.pinupp.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by naveen on 3/7/2016.
 */
public class Education {

    public Map<Integer, Degrees> getDegreesList() {
        return degreesList;
    }
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDegreesList(Map<Integer, Degrees> degreesList) {
        this.degreesList = degreesList;
    }

    private Map<Integer, Degrees> degreesList = new HashMap<>();

    public static class Degrees {
        private String qualification;
        private String institute;
        private String year;
        private String image_uri;


        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getInstitute() {
            return institute;
        }

        public void setInstitute(String institute) {
            this.institute = institute;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getImage_uri() {
            return image_uri;
        }

        public void setImage_uri(String image_uri) {
            this.image_uri = image_uri;
        }

        @Override
        public String toString() {
            return qualification + ',' +
                    institute + ',' +
                    year ;
        }

        public Degrees(String qualification, String institute, String year, String uri_img) {
            this.qualification = qualification;
            this.institute = institute;
            this.year = year;
            this.image_uri = uri_img;
        }
    }
}
