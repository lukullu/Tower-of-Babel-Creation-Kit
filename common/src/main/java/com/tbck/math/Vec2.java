package com.tbck.math;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.aether.utils.utils.reflection.Exposed;
import net.aether.utils.utils.swing.FieldButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vec2 implements Serializable
{

    public static final TypeAdapter<Vec2> TYPE_ADAPTER = new TypeAdapter<>() {

        public void write(JsonWriter jsonWriter, Vec2 vec2) throws IOException {
			// to have zero indent, but to not break the rest of the json file
			// we create a seperate jsonWriter with the desired indent, and then use it's value
			StringWriter sWriter = new StringWriter();
			JsonWriter writer = new JsonWriter(sWriter);
			writer.setIndent("");
			writer.beginArray()
				.value(vec2.x)
				.value(vec2.y)
				.endArray();
			jsonWriter.jsonValue(sWriter.getBuffer().toString());
        }
        public Vec2 read(JsonReader jsonReader) throws IOException {
            jsonReader.beginArray();
            double x = jsonReader.nextDouble();
            double y = jsonReader.nextDouble();
            jsonReader.endArray();
            return new Vec2(x, y);
        }
    };

    // register Vec2 to dynamic typing system™
    static {
        FieldButton.GENERATOR_REGISTRY.put(Vec2.class, oldValue -> {
            String input = oldValue.toString();
            Vec2 value;
            do {
                input = JOptionPane.showInputDialog(null, "Input Vec2", input);
                // user closed or pressed cancel
                if (input == null) return oldValue;
                value = Vec2.fromString(input);
            } while (value == null);

            return value;
        });
    }

    @Serial private static final long serialVersionUID = 4559635089051247664L;
    public static final Pattern VEC2_PATTERN = Pattern.compile("\\[?(.+),([^\\]]+)\\]?");

    public static Vec2 ZERO_VECTOR2 = new Vec2(0,0);
    public double x, y;
    public Vec2(double x, double y) { this.x = x; this.y = y; }
    public Vec2(Vec2 in) { this.x = in.x; this.y = in.y; }

    // Vector Arithmetic
    public Vec2 add(Vec2 other_vector)
    {
        return new Vec2(x + other_vector.x, y + other_vector.y);
    }
    public static Vec2 add(Vec2 v1, Vec2 v2) { return v1.add(v2); }
    public Vec2 subtract(Vec2 other_vector)
    {
        return new Vec2(x - other_vector.x, y - other_vector.y);
    }
    public Vec2 multiply(Vec2 other_vector)
    {
        return new Vec2(x * other_vector.x, y * other_vector.y);
    }
    public Vec2 multiply(double value) { return new Vec2(x * value, y * value); }
    public Vec2 divide(Vec2 other_vector)
    {
        return new Vec2(x / other_vector.x, y / other_vector.y);
    }
    public Vec2 divide(double value) { return new Vec2(x / value, y / value); }
    public Vec2 mod(Vec2 other_vector) { return new Vec2(x % other_vector.x, y % other_vector.y); }
    public Vec2 mod(double value) { return new Vec2(x % value, y % value); }
    public double length(){return Math.sqrt(Math.max(0, x*x + y*y)); }
    public double length2(){return x*x + y*y; }
    public Vec2 normalise(){ return this.divide(this.length()); }
    public static double dotProduct(Vec2 v1, Vec2 v2){ return v1.x * v2.x + v1.y * v2.y; }
    public static double distance(Vec2 v1, Vec2 v2){ return v1.subtract(v2).length(); }
    public static double distance2(Vec2 v1, Vec2 v2){ return v1.subtract(v2).length2(); }
    public Vec2 align(Vec2 other_vector){ return new Vec2 (Math.signum(other_vector.x) * Math.abs(x), Math.signum(other_vector.y) * Math.abs(y)); }
    public Vec2 normalVector() { return new Vec2(y,-x); }
    public Vec2 abs() { return new Vec2(Math.abs(x),Math.abs(y)); }
    public Vec2 rotate(Vec2 pivot, double angle)
    {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        Vec2 relative = this.subtract(pivot);
        Vec2 newRelative = new Vec2(
                relative.x * c - relative.y * s,
                relative.x * s + relative.y * c
        );
        return newRelative.add(pivot);
    }
    public Vec2 scale(Vec2 origin, double value) { return this.add(this.subtract(origin).multiply(value)); }

    public Point getPoint() { return new Point((int) x, (int) y); }
    
    public String toString() {
        return "[" + (Math.round(x * 100) / 100.0) + ", " + (Math.round(y * 100) / 100.0) + "]";
    }

    public static Vec2 fromString(String string) {
        if (string == null) return null;
        Matcher matcher = VEC2_PATTERN.matcher(string.replace(" ", ""));

        // unparsable
        if (! matcher.find() || matcher.groupCount() != 2) return null;

        try {
            double x = Double.parseDouble(matcher.group(1));
            double y = Double.parseDouble(matcher.group(2));
            return new Vec2(x, y);
        } catch (NumberFormatException e) { return null; }
    }

    @Exposed(as = "position")
    public void setPosition(Vec2 newPos) {
        this.x = newPos.x;
        this.y = newPos.y;
    }
    @Exposed(as = "position")
    public Vec2 getPosition() { return this; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2 vec2)) return false;
        return Double.compare(x, vec2.x) == 0 && Double.compare(y, vec2.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
