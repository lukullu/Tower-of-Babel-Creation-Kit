package com.lukullu.oceanExperiment.simulation;

import com.kilix.processing.ProcessingClass;
import com.lukullu.oceanExperiment.OceanExperiment;
import com.lukullu.tbck.gameObjects.IAtom;
import com.lukullu.tbck.utils.DeltaTimer;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.Objects;

public class H2O implements IAtom, ProcessingClass
{

    private static final double DENSITY_RADIUS = 100;
    private static final double MOLECULE_MASS = 1;
    private static final Vec2 GRAVITY = new Vec2(0,100);
    private static final double DENSITY_PUSH_FORCE = 100;
    private static final double COLLISION_DAMPENING = 0.8;
    private Vec2 position;
    private Vec2 force = new Vec2(0,0);
    public double surroundingDensity = 1;

    public H2O(Vec2 position)
    {
        this.position = position;
    }

    @Override
    public void update()
    {
        surroundingDensity = calcSurroundingDensity(this.getPosition());
        //applyForce(GRAVITY);
        applyForce(calcDensityForce());
        collision();
        move();
    }

    private void move()
    {
        //position = position.add(force.divide(MOLECULE_MASS).multiply(DeltaTimer.getInstance().getDeltaTime() * DeltaTimer.getInstance().getDeltaTime()));
        position = position.add(force.divide(MOLECULE_MASS).multiply(0.00002));
    }

    private void applyForce(Vec2 deltaForce)
    {
        force = force.add(deltaForce);
    }

    private void collision()
    {
        if(position.y >= getHeight() || position.y <= 0)
        {
            force = new Vec2(force.x,-force.y).multiply(COLLISION_DAMPENING);
            position.y = position.y > 0 ? getHeight()-1 : 1;
        }

        if(position.x >= getWidth() || position.x <= 0)
        {
            force = new Vec2(-force.x,force.y).multiply(COLLISION_DAMPENING);
            position.x = position.x > 0 ? getWidth()-1 : 1;
        }

    }

    public static double calcSurroundingDensity(Vec2 position)
    {

        Vec2 cartesianIndex = OceanExperiment.getSim().partitionedMolecules.getCartesianIndex(position);

        ArrayList<IAtom> allMolecules = new ArrayList<>();

        //allMolecules = OceanExperiment.sim.molecules;
        for(int y = -1; y < 2; y++)
        {
            for(int x = -1; x < 2; x++)
            {
                int iX;
                int iY;
                if(cartesianIndex.x + x < 0)
                    iX = 0;
                else
                    iX = (int)cartesianIndex.x + x;

                if(cartesianIndex.y + y < 0)
                    iY = 0;
                else
                    iY = (int)cartesianIndex.y + y;


                if(OceanExperiment.getSim().partitionedMolecules.getPartitionedPoints()[iY][iX] != null)
                    allMolecules.addAll(OceanExperiment.getSim().partitionedMolecules.getPartitionedPoints()[iY][iX]);
            }
        }

        System.out.println(allMolecules.size());

        double newSurroundingDensity = 0;
        int colliderCounter = 0;

        for(IAtom molecule : allMolecules)
        {
            if(molecule.getPosition().equals(position))
                continue;

            double distance2 = Vec2.distance2(molecule.getPosition(),position);

            if(distance2 > DENSITY_RADIUS * DENSITY_RADIUS)
                continue;

            double normalisedDistance = distance2 / (DENSITY_RADIUS * DENSITY_RADIUS);

            newSurroundingDensity += normalisedDistance * MOLECULE_MASS;
            colliderCounter++;

        }
        double out = 0;
        if(colliderCounter != 0)
            out = newSurroundingDensity / (double) colliderCounter;

    return out;
    }

    private Vec2 calcDensityForce()
    {
        Vec2 cartesianIndex = OceanExperiment.getSim().partitionedMolecules.getCartesianIndex(position);

        ArrayList<IAtom> allMolecules = new ArrayList<>();

        //allMolecules = OceanExperiment.sim.molecules;
        for(int y = -1; y < 2; y++)
        {
            for(int x = -1; x < 2; x++)
            {
                int iX;
                int iY;
                if(cartesianIndex.x + x < 0)
                    iX = 0;
                else
                    iX = (int)cartesianIndex.x + x;

                if(cartesianIndex.y + y < 0)
                    iY = 0;
                else
                    iY = (int)cartesianIndex.y + y;


                if(OceanExperiment.getSim().partitionedMolecules.getPartitionedPoints()[iY][iX] != null)
                    allMolecules.addAll(OceanExperiment.getSim().partitionedMolecules.getPartitionedPoints()[iY][iX]);
            }
        }

        Vec2 output = new Vec2(0,0);
        for(IAtom molecule : allMolecules)
        {
            if(molecule.getPosition().equals(position))
                continue;

            double distance2 = Vec2.distance2(molecule.getPosition(),position);

            if(distance2 > DENSITY_RADIUS * DENSITY_RADIUS)
                continue;

            double normalisedDistance = distance2 / (DENSITY_RADIUS * DENSITY_RADIUS);

            double force = densityForceFunction(normalisedDistance);

            output = output.add(molecule.getPosition().subtract(this.position).normalise().multiply(force*DENSITY_PUSH_FORCE));
        }

        return output;
    }

    private static double densityForceFunction(double in)
    {
        return Math.pow(in-0.5,3)*8;
    }

    /*private Vec2 calcDensityForce()
    {
        double densityLeft  = -calcSurroundingDensity(getPosition().add(new Vec2(-DENSITY_RADIUS,0))) + Simulation.TARGET_DENSITY;
        double densityRight = -calcSurroundingDensity(getPosition().add(new Vec2( DENSITY_RADIUS,0))) + Simulation.TARGET_DENSITY;
        double densityUp    = -calcSurroundingDensity(getPosition().add(new Vec2(0,-DENSITY_RADIUS))) + Simulation.TARGET_DENSITY;
        double densityDown  = -calcSurroundingDensity(getPosition().add(new Vec2(0, DENSITY_RADIUS))) + Simulation.TARGET_DENSITY;

        //System.out.println(densityDown);

        Vec2 densityForce = new Vec2(
                densityLeft - densityRight,
                densityUp - densityDown
        );

        return densityForce.multiply(DENSITY_PUSH_FORCE);
    }*/



    @Override
    public void paint()
    {
        fill(60 + (float) surroundingDensity * 30);
        noStroke();
        ellipse((float)position.x,(float)position.y,5,5);

    }

    @Override
    public void reset() {

    }

    @Override
    public void die() {

    }

    public Vec2 getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        H2O h2O = (H2O) o;
        return Objects.equals(position, h2O.position) && Objects.equals(force, h2O.force);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, force);
    }

    @Override
    public String toString() {
        return "H2O{" +
                "position=" + position +
                ", force=" + force +
                ", surroundingDensity=" + surroundingDensity +
                '}';
    }
}