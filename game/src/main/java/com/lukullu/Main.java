package com.lukullu;

import com.kilix.processing.ProcessingSketch;
import com.lukullu.rayMarchingExperiment.FlashLightExperiment;
import com.lukullu.undersquare.UnderSquare3;

public class Main {
    public static void main(String[] args) {
        ProcessingSketch<FlashLightExperiment> gameSketch = ProcessingSketch
                .builder(FlashLightExperiment.class)
                .fullScreen()
                .build();
    }

}
