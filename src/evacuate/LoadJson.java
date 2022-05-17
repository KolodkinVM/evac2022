package evacuate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;


import structure.BuildingPIM;

import com.google.gson.Gson;

public class LoadJson {

    private BuildingPIM building = new BuildingPIM();
    public LoadJson(String jsonLocation) {
        FileInputStream f;
        try {
            f = new FileInputStream(jsonLocation);
            BufferedReader in = new BufferedReader(new InputStreamReader(f));
            setBuilding(new Gson().fromJson(in, BuildingPIM.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public BuildingPIM getBuilding() {
        return building;
    }
    public void setBuilding(BuildingPIM building) {
        this.building = building;
    }

}
