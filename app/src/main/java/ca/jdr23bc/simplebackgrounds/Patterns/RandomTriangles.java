package ca.jdr23bc.simplebackgrounds.Patterns;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;

import ca.jdr23bc.simplebackgrounds.ColorScheme;
import ca.jdr23bc.simplebackgrounds.Colour;
import ca.jdr23bc.simplebackgrounds.RandomGrid;

public class RandomTriangles extends Pattern {

    RandomGrid randomGrid;

    public RandomTriangles(int width, int height) {
        super(width, height);
        this.colorScheme = new ColorScheme(this.rootColor, Colour.ColorScheme.ColorSchemeMonochromatic);
        BlurMaskFilter maskFilter = new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.SOLID);
        paint.setMaskFilter(maskFilter);
        randomGrid = new RandomGrid(width, height);
    }

    public void draw(Canvas canvas) {
        ArrayList<Triangle> triangles = new ArrayList<>();
        for (int row = 0; row < randomGrid.rows - 1; row++) {
            for (int col = 0; col < randomGrid.cols - 1; col++) {
                PointF topLeft = randomGrid.points[row][col];
                PointF topRight = randomGrid.points[row][col + 1];
                PointF botLeft = randomGrid.points[row + 1][col];
                PointF botRight = randomGrid.points[row + 1][col + 1];
                PointF center = new PointF((topLeft.x + topRight.x) / 2, (topLeft.y + botLeft.y) / 2);
                triangles.add(new Triangle(topLeft, topRight, center));
                triangles.add(new Triangle(botLeft, topLeft, center));
                triangles.add(new Triangle(botLeft, botRight, center));
                triangles.add(new Triangle(botRight, topRight, center));
            }
        }

        while(!triangles.isEmpty()) {
            paint.setColor(colorScheme.getRandom());
            triangles.remove(random.nextInt(triangles.size())).draw(canvas, paint);
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
