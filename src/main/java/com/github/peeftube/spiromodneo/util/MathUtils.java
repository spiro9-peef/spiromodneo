package com.github.peeftube.spiromodneo.util;

public class MathUtils
{
    /// <summary>
    /// Gets 1 divided by a float input value
    /// </summary>
    public static float getFracInv(float in) { return 1.0f / in; }

    public static class MinMax
    {
        static int min = 1;
        static int max = 1;

        public MinMax(int min, int max)
        { this.min = min; this.max = max; }

        public static int getMin()
        { return min; }
        public static int getMax()
        { return max; }
    }
}
