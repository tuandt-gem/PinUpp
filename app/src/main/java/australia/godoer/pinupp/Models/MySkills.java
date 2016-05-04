package australia.godoer.pinupp.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by naveen on 3/14/2016.
 */
public class MySkills {

    public Map<Integer, Skill> getSkillList() {
        return skillList;
    }
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSkillList(Map<Integer, Skill> skillList) {
        this.skillList = skillList;
    }

    private Map<Integer, Skill> skillList = new HashMap<>();

    public static class Skill {
        private String title;
        private String level;
        private int seek_val;

        public int getSeek_val() {
            return seek_val;
        }

        public void setSeek_val(int seek_val) {
            this.seek_val = seek_val;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public Skill(String title, String level, int seek_val) {
            this.title = title;
            this.level = level;
            this.seek_val = seek_val;
        }
    }
}
