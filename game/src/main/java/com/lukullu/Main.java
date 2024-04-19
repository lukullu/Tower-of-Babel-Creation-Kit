package com.lukullu;

import com.kilix.processing.ProcessingSketch;
import com.lukullu.oceanExperiment.OceanExperiment;
import com.tbck.math.MortonCode;
import com.tbck.math.UInt32;
import com.tbck.math.Vec2;

public class Main {

    public static void main(String[] args) {
        ProcessingSketch<OceanExperiment> gameSketch = ProcessingSketch
                .builder(OceanExperiment.class)
                .fullScreen()
                .build();

    }

}
