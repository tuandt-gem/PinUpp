package australia.godoer.pinupp.Models;

/**
 * Created by naveen on 2/24/2016.
 */
public class Profile{

    private final int id;
    public final String title;
    private MyInfo myInfo;
    private Education education;
    private MySkills myskills;
    private Experiences experiences;

    public Jobs getMyJobs() {
        if (myJobs == null) {
            this.myJobs = new Jobs();
        }
        return myJobs;
    }

    public void setMyJobs(Jobs myJobs) {
        this.myJobs = myJobs;
    }

    private Jobs myJobs;

    public Experiences getExperiences() {
        if(this.experiences == null)
            this.experiences = new Experiences();
        return experiences;
    }

    public void setExperiences(Experiences experiences) {
        this.experiences = experiences;
    }

    public MySkills getMyskills() {
        if(this.myskills == null){
            this.myskills = new MySkills();
        }
        return myskills;
    }

    public void setMyskills(MySkills myskills) {
        this.myskills = myskills;
    }

    public Education getEducation() {
        if(this.education == null)
        {
          this.education = new Education();
        }
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Profile(int id, String title) {
        this.id = id;
        this.title = title;
    }


    public MyInfo getMyInfo() {
        if(this.myInfo == null)
            this.myInfo = new MyInfo();
        return myInfo;
    }

    public void setMyInfo(MyInfo myInfo) {
        this.myInfo = myInfo;
    }

    @Override
    public String toString() {
        return title;
    }


    public Integer getId() {
        return id;
    }

}
