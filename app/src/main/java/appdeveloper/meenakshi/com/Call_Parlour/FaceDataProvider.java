package appdeveloper.meenakshi.com.Call_Parlour;

/**
 * Created by Ashish on 04/28/2017.
 */

public class FaceDataProvider {

    private String heading;
    private String subHeading;

    public FaceDataProvider( String title, String desc) {

        this.heading= title;
        this.subHeading = desc;
    }
    public String getHeading() {
    return heading;
    }
    public void setHeading(String Heading) {
    this.heading = Heading;
    }
    public String getsubHeading() {
        return subHeading;
    }
    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }
    @Override
    public String toString() {
    return heading + "\n" + subHeading;
    }

}
