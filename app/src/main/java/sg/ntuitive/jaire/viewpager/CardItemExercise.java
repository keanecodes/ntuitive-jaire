package sg.ntuitive.jaire.viewpager;

public class CardItemExercise {
    private int Image;
    private String trackType;
    private String excerciseName;
    private String equipment;

    public CardItemExercise(int image, String trackType, String excerciseName, String equipment) {
        Image = image;
        this.trackType = trackType;
        this.excerciseName = excerciseName;
        this.equipment = equipment;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getExcerciseName() {
        return excerciseName;
    }

    public void setExcerciseName(String excerciseName) {
        this.excerciseName = excerciseName;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
