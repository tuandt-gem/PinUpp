package australia.godoer.pinupp.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by naveen on 3/17/2016.
 */
public class Experiences {

    public Map<Integer, Experience> getExperienceMap() {
        return experienceMap;
    }
    //public int total_weight;

    public void setExperienceMap(Map<Integer, Experience> experienceMap) {
        this.experienceMap = experienceMap;
    }

    private Map<Integer, Experience> experienceMap = new HashMap<>();

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Experience {

        private String title;

        @Override
        public String toString() {
            return title + " - "  + totalValStr;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public float getChart_weight() {
            return chart_weight;
        }

        public void setChart_weight(float chart_weight) {
            this.chart_weight = chart_weight;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

//        public int getSpin2_pos() {
//            return spin2_pos;
//        }
//
//        public void setSpin2_pos(int spin2_pos) {
//            this.spin2_pos = spin2_pos;
//        }
//
//        public int getSpin1_pos() {
//            return spin1_pos;
//        }
//
//        public void setSpin1_pos(int spin1_pos) {
//            this.spin1_pos = spin1_pos;
//        }

        private float chart_weight;
        private String value1;
        private String value2;
        //private int spin1_pos;
        //private int spin2_pos;
        private String totalValStr;

        public String getTotalValStr() {
            return totalValStr;
        }

        public void setTotalValStr(String totalValStr) {
            this.totalValStr = totalValStr;
        }

        public Experience(String title, float chart_weight, String value1, String value2, String totalVal) {
            this.title = title;
            this.chart_weight = chart_weight;
            this.value1 = value1;
            this.value2 = value2;
            //this.spin1_pos = spin1;
            //this.spin2_pos = spin2;
            this.totalValStr = totalVal;
        }
    }
}
