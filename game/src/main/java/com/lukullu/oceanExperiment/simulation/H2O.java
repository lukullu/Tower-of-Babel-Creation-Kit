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

    private static final double DENSITY_RADIUS = 50;
    private static final double MOLECULE_MASS = 1;
    private static final Vec2 GRAVITY = new Vec2(0,10);
    private static final double DENSITY_PUSH_FORCE = 50;
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
        applyForce(GRAVITY);
        applyForce(calcDensityForce());
        collision();
        move();
    }

    private void move()
    {
        position = position.add(force.divide(MOLECULE_MASS).multiply(DeltaTimer.getInstance().getDeltaTime() * DeltaTimer.getInstance().getDeltaTime()));
    }

    private void applyForce(Vec2 deltaForce)
    {
        force = force.add(deltaForce);
    }

    private void collision()
    {
        if(position.y >= getHeight() || position.y <= 0)
        {
            force = new Vec2(force.x,-force.y);
        }

        if(position.x >= getWidth() || position.x <= 0)
        {
            force = new Vec2(-force.x,force.y);
        }

    }

    private double calcSurroundingDensity(Vec2 position)
    {
        ArrayList<IAtom> allMolecules = OceanExperiment.getSim().getMolecules();

        double newSurroundingDensity = 0;

        for(IAtom molecule : allMolecules)
        {
            if(molecule.equals(this))
                continue;

            double distance2 = Vec2.distance2(molecule.getPosition(),position);

            if(distance2 > DENSITY_RADIUS * DENSITY_RADIUS)
                continue;

            double normalisedDistance = distance2 / (DENSITY_RADIUS * DENSITY_RADIUS);

            newSurroundingDensity += normalisedDistance * MOLECULE_MASS;

        }
        return newSurroundingDensity;
    }

    private Vec2 calcDensityForce()
    {
        double densityLeft  = calcSurroundingDensity(getPosition().add(new Vec2(-DENSITY_RADIUS,0)));
        double densityRight = calcSurroundingDensity(getPosition().add(new Vec2( DENSITY_RADIUS,0)));
        double densityUp    = calcSurroundingDensity(getPosition().add(new Vec2(0,-DENSITY_RADIUS)));
        double densityDown  = calcSurroundingDensity(getPosition().add(new Vec2(0, DENSITY_RADIUS)));

        Vec2 densityForce = new Vec2(
                densityLeft  -densityRight,
                densityUp  -densityDown
        );

        return densityForce.multiply(DENSITY_PUSH_FORCE);
    }

    @Override
    public void paint()
    {

        fill(60 + (float) surroundingDensity * 10);
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
}
