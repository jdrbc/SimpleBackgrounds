package ca.jdr23bc.backgrounds.painters;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import ca.jdr23bc.backgrounds.shapes.tree.Branch;
import ca.jdr23bc.backgrounds.shapes.tree.Leaf;
import ca.jdr23bc.backgrounds.shapes.tree.Tree;

public class TreePainter extends ShapePainter<Tree> {

    private Tree tree;
    private int branchColor;
    private int leafColor;
    private int shadowColor;

    @Override
    public void init(Tree tree) {
        this.tree = tree;
        this.branchColor = popRandomPaintColor();
        this.leafColor = popRandomPaintColor();
        this.shadowColor = popRandomPaintColor();
        tree.init();
    }

    @Override
    public Boolean hasNextPaintStep() {
        return tree.hasNextBranch() || tree.hasNextLeaf();
    }

    @Override
    public void paintStep(Canvas canvas) {
        if (tree.hasNextBranch()) {
            paintBranch(canvas);
        } else if (tree.hasNextLeaf()) {
            paintLeaf(canvas);
        }
    }

    private void paintBranch(Canvas canvas) {
        Branch branch = tree.nextBranch();
        Paint paint = newPaint();
        paint.setColor(branchColor);
        paint.setStrokeWidth(branch.getWidth());
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setShadowLayer(5.0f, 0.0f, 2.0f, shadowColor);
        PointF branchBase = branch.getBase();
        PointF branchTip = branch.getTip();
        canvas.drawLine(branchBase.x, branchBase.y,
                branchTip.x, branchTip.y, paint);
    }

    private void paintLeaf(Canvas canvas) {
        Leaf leaf = tree.nextLeaf();

        PointF base = leaf.getBase();
        PointF tip = leaf.getTip();
        PointF side1 = leaf.getSideRightOfBase();
        PointF side2 = leaf.getSideLeftOfBase();

        Path path = new Path();
        path.moveTo(base.x, base.y);
        path.quadTo(side1.x, side1.y, tip.x, tip.y);
        path.quadTo(side2.x, side2.y, base.x, base.y);
        path.close();

        Paint paint = newPaint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(leafColor);
        canvas.drawPath(path, getPaint());
    }
}
