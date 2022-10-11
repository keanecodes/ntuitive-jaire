package sg.ntuitive.jaire.viewpager;

import com.github.mikephil.charting.data.RadarData;

public class CardItemModel {
    //private int Image;
    private String modelName;
    private String modelType;
    private String level;
    private String session;
    private RadarData radarData;

    public CardItemModel(String modelName, String modelType, String level, String session, RadarData radarData) {
        //Image = image;
        this.modelName = modelName;
        this.modelType = modelType;
        this.level = level;
        this.session = session;
        this.radarData = radarData;
    }

    public String getModelName() { return modelName; }

    public void setModelName(String modelName) { this.modelName = modelName; }

    public String getModelType() { return modelType; }

    public void setModelType(String modelType) { this.modelType = modelType; }

    public String getLevel() { return level; }

    public void setLevel(String level) { this.level = level; }

    public String getSession() { return session; }

    public void setSession(String session) { this.session = session; }

    public RadarData getRadarData() { return radarData; }

    public void setRadarData(RadarData radarData) { this.radarData = radarData; }
}
