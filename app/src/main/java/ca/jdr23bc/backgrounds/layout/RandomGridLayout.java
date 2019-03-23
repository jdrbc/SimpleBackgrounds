package ca.jdr23bc.backgrounds.layout;

import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class RandomGridLayout extends Layout {
    private static final String TAG = RandomGridLayout.class.getCanonicalName();

    int numCells;
    List<Cell> cells = new ArrayList<>();

    @Override
    public void init(PointF topLeft, PointF bottomRight) {
        super.init(topLeft, bottomRight);
        GridLayout innerLayout = new GridLayout();
        innerLayout.withSquareCellsActive(true);
        innerLayout.init(topLeft, bottomRight);
        while (innerLayout.hasNext()) {
            Cell newCell = innerLayout.next();
            Log.v(TAG, "adding cell " + newCell);
            cells.add(newCell);
        }
        numCells = cells.size();
    }

    @Override
    public Integer getNumberOfCells() {
        return numCells;
    }

    @Override
    protected boolean finished() {
        return cells.isEmpty();
    }

    @Override
    public Cell next() {
        return cells.remove(RandomUtils.getRandomIntInRange(0, cells.size()));
    }
}