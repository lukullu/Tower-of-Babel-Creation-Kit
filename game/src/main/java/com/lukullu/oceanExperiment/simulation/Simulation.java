package com.lukullu.oceanExperiment.simulation;

import com.kilix.processing.ProcessingClass;
import com.lukullu.oceanExperiment.spacialPartitioning.QuadList;
import com.lukullu.tbck.gameObjects.IAtom;
import com.tbck.math.MortonCode;
import com.tbck.math.UInt32;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class Simulation implements ProcessingClass
{

    private static final double INITIAL_MOLECULE_SPACING = 5;
    public static final double TARGET_DENSITY = 0.0;

    ArrayList<IAtom> molecules = new ArrayList<>();
    QuadList partitionedMolecules = new QuadList(getWidth(),6);

    public Simulation(int moleculeCount)
    {
        molecules = spawnMoleculeGrid(moleculeCount);
    }

    private ArrayList<IAtom> spawnMoleculeGrid(int count)
    {

        ArrayList<IAtom> molecules = new ArrayList<>();

        int dim = (int)Math.ceil(Math.sqrt(count));

        Vec2 startPos = new Vec2(
                getWidth()/2d-(dim/2d)*INITIAL_MOLECULE_SPACING,
                getHeight()/2d-(dim/2d)*INITIAL_MOLECULE_SPACING
        );


        for(int y = 0; y < dim; y++)
        {
            for(int x = 0; x < dim; x++)
            {
                molecules.add(new H2O(new Vec2(x*INITIAL_MOLECULE_SPACING+startPos.x,y*INITIAL_MOLECULE_SPACING+startPos.y)));
                count--;

                if(count <= 0)
                    break;
            }
        }

        return molecules;
    }

    public void update()
    {
        partitionedMolecules.partition(molecules);

        for (IAtom molecule : molecules)
        {
            molecule.update();
        }
    }

    public void paint()
    {

        for(IAtom molecule : molecules)
        {
            molecule.paint();
        }

        for (IAtom molecule : molecules)
        {
            molecule.paint();
        }
    }

    public ArrayList<IAtom> getMolecules()
    {
        return molecules;
    }
}