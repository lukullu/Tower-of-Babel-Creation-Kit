package com.lukullu;

import com.kilix.processing.ProcessingSketch;
import com.lukullu.oceanExperiment.OceanExperiment;
import com.lukullu.undersquare.UnderSquare3;

public class Main {
    public static void main(String[] args) {
        ProcessingSketch<OceanExperiment> gameSketch = ProcessingSketch
                .builder(OceanExperiment.class)
                .fullScreen()
                .build();
    }

}
