package ca.jdr23bc.backgrounds.backgrounds;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;

public abstract class ShapeBackground extends Background {
    private static final String TAG = ShapeBackground.class.getCanonicalName();

    public ShapeBackground(int width, int height) {
        super(width, height);
    }

    @Override
    public void init() {
        ShapeFactory factory = getFactory();
        factory.init(new PointF(0, 0), new PointF(getWidth(), getHeight()));

        ShapePainter painter = getPainter();
        painter.init(factory.next());
        painter.fillBackground(getCanvas());
    }

    @Override
    public Boolean hasNextDrawStep() {
        return getPainter().hasNextPaintStep() || getFactory().hasNext();
    }

    @Override
    public void drawStep() {
        ShapePainter painter = getPainter();
        if (!painter.hasNextPaintStep()) {
            painter.init(getFactory().next());
        }
        painter.paintStep(getCanvas());
    }

    public String toString() {
        return "{ name: " + this.getClass().getSimpleName() + ", " +
                "factory: " + getFactory().toString() + ", " +
                "painter: " + getPainter().toString() + " }";
    }

    protected abstract ShapeFactory getFactory();
    protected abstract ShapePainter getPainter();
}
