import java.util.Comparator;

public class SortTriangleByZ implements Comparator<Triangle>
{
    public int compare(Triangle a, Triangle b)
    {
		float first = (a.Points[0].Z + a.Points[1].Z + a.Points[2].Z) / 3.0f;
		float second = (b.Points[0].Z + b.Points[1].Z + a.Points[2].Z) / 3.0f;

		return sign(first - second);
    }

    private int sign(float f)
    {
        if (f < 0) return -1;
        else if (f > 0) return 1;
        else return 0;
    }
}