public class Color
{
    
    public int R;
    public int G;
    public int B;

    // Creates a new color with values specified 
    public Color(byte r, byte g, byte b)
    {
        R = r;
        B = b;
        G = g;
    }

    // Creates a new color with values specified
    public Color(int r, int g, int b)
    {
        R = clipInteger(r);
        G = clipInteger(g);
        B = clipInteger(b);
    }

    // Clones existing color
    public Color(Color other)
    {
        R = Integer.valueOf(other.R);
        G = Integer.valueOf(other.G);
        B = Integer.valueOf(other.B);
    }

    // Returns an integer inside of the unsigned byte range 
    private int clipInteger(int x)
    {
        if (x < 0) x = 0;
        else if (x > 255) x = 255;

        return x;
    }
}