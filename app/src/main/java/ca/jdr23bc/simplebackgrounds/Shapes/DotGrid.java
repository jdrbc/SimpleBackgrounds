package ca.jdr23bc.simplebackgrounds.Shapes;

import android.graphics.Canvas;

public class DotGrid extends Pattern {
    public final int MAX_ROWS = 20;
    public final int MIN_ROWS = 10;
    public final int MAX_COLS = 15;
    public final int MIN_COLS = 5;
    static final int MAX_RADIUS = 75;
    static final int MIN_RADIUS = 30;

    static final float MAX_RANDOMNESS = 1;
    static final float MIN_RANDOMNESS = 0.25f;

    int rows;
    int cols;
    boolean sameRadius;
    boolean sequencedRadiusRandomness;
    double radiusRandomness;
    boolean sameColor;
    float radius;
    int color;
    Grid grid;
    RandomDots randomDots;

    public DotGrid(int width, int height) {
        super(width, height);
        this.rows =  nextIntInRange(MIN_ROWS, MAX_ROWS);
        this.cols = nextIntInRange(MIN_COLS, MAX_COLS);
        grid = new Grid(width, height + 125, rows, cols);
        sameRadius = random.nextBoolean();
        radius = nextIntInRange(MIN_RADIUS, MAX_RADIUS);
        sameColor = random.nextBoolean();
        color = colorScheme.getRandom();
        sequencedRadiusRandomness = random.nextBoolean();
        radiusRandomness = nextFloatInRange(MIN_RANDOMNESS, MAX_RANDOMNESS);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        randomDots = new RandomDots(width, height, this);
        while(grid.hasNext()) {
            Grid.Cell cell = grid.next();
            Circle c = randomDots.getDot();
            c.pos = cell.getTopLeft();

            if (!sameColor) {
                color = colorScheme.getRandom();
            }
            c.primaryColor = color;

            if (!sameRadius) {
                if (sequencedRadiusRandomness) {
                    radius = nextIntInRangeSequenced(
                            MIN_RADIUS,
                            MAX_RADIUS,
                            cell.getRow() * radiusRandomness,
                            cell.getCol() * radiusRandomness);
                } else {
                    radius = nextIntInRange(MIN_RADIUS, MAX_RADIUS);
                }
            }
            c.radius = radius;
            c.draw(canvas);
        }
    }
}
