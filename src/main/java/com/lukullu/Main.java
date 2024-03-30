package com.lukullu;

import com.kilix.processing.ProcessingSketch;
import com.lukullu.undersquare.UnderSquare3;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProcessingSketch<UnderSquare3> gameSketch = ProcessingSketch
                .builder(UnderSquare3.class)
                .fullScreen()
                .build();

    }

}
