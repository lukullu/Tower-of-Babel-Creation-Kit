package com.tbck.math;

import java.util.Objects;

public class LineSegment
{
    public Vec2 start;
    public Vec2 end;

    public LineSegment(Vec2 start, Vec2 end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineSegment line)) return false;
        return Objects.equals(start, line.start) && Objects.equals(end, line.end) || Objects.equals(end, line.start) && Objects.equals(start, line.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
