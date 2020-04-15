import java.lang.Math;
import java.util.Comparator;

public class SortTriangleByZ implements Comparator<Triangle>
{
	// Compares the average z value of triangle a and triangle b
    public int compare(Triangle a, Triangle b)
    {
		float first = (a.Points[0].Z + a.Points[1].Z + a.Points[2].Z) / 3.0f;
		float second = (b.Points[0].Z + b.Points[1].Z + a.Points[2].Z) / 3.0f;

		return Math.round(first - second);
    }
}