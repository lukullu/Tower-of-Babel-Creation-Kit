package com.lukullu.undersquare.utils;

import com.lukullu.undersquare.enums.SegmentRoles;
import com.lukullu.tbck.utils.Vec2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PSFF_Utils {

    public static ArrayList<String> getLines(String psff_url)
    {
        ArrayList<String> out = new ArrayList<>();

        try {
            File myObj = new File(psff_url);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                out.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        return out;
    }

    public static SegmentData getVerticesFromLine(String data)
    {
        try {
            SegmentData out = new SegmentData();

            ArrayList<Vec2> vertices = new ArrayList<>();

            String[] dataEntries = data.split(":");

            if (dataEntries.length != 3) {
                return null;
            }

            out.ArmorPoints = Integer.parseInt(dataEntries[2]);

            out.role = SegmentRoles.valueOf(dataEntries[1]);

            String[] verticesAsString = dataEntries[0].split(";");

            for (String s : verticesAsString) {
                String[] coords = s.split(",");
                vertices.add(new Vec2(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])));
            }
            out.vertices = vertices;

            return out;

        }catch (Exception e) { e.printStackTrace(); return null; }
    }


}
