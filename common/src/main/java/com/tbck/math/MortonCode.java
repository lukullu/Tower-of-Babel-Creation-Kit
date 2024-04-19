package com.tbck.math;

public class MortonCode
{

    public static UInt32 encode2D(int x, int y)
    {
        x = Math.abs(x);
        y = Math.abs(y);

        long out = 0;
        for(int i = 0; i < 64; i++)
        {
            int in = (i%2==0) ? x : y;
            int bit = (in >> i/2) & 1;
            out += (long) bit << i;
        }

        return new UInt32(out);
    }

    public static Vec2 decode2D(UInt32 zOrderValue)
    {
        return new Vec2(
                decodeX2D(zOrderValue).longValue(),
                decodeY2D(zOrderValue).longValue()
        );
    }

    public static UInt32 decodeX2D(UInt32 zOrderValue)
    {
        long z = zOrderValue.longValue();
        long out = 0;
        for(int i = 0; i < 31; i++)
        {
            int bit = (int) (z >> i*2 & 1);
            out += bit << i;
        }
        return new UInt32(out);
    }

    public static UInt32 decodeY2D(UInt32 zOrderValue)
    {

        long z = zOrderValue.longValue();
        long out = 0;
        for(int i = 0; i < 31; i++)
        {
            int bit = (int) (z >> (i*2+1) & 1);
            out += bit << i;
        }
        return new UInt32(out);
    }

}
