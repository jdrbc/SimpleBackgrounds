package ca.jdr23bc.backgrounds.painters;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import ca.jdr23bc.backgrounds.shapes.tree.Branch;
import ca.jdr23bc.backgrounds.shapes.tree.Leaf;
import ca.jdr23bc.backgrounds.shapes.tree.Tree;
import ca.jdr23bc.backgrounds.utils.MathUtils;

public class TreePainter extends ShapePainter<Tree> {

    private Tree tree;
    private int branchColor;
    private int leafColor;
    private int shadowColor;
    private PointF lightDir;
    private Bitmap branchLayer;
    private Canvas branchLayerCanvas;
    private Bitmap leafLayer;
    private Canvas leafLayerCanvas;

    @Override
    public void init(Tree tree) {
        this.tree = tree;
        this.branchColor = popRandomPaintColor();
        this.leafColor = popRandomPaintColor();
        this.shadowColor = popRandomPaintColor();
        this.lightDir = MathUtils.normalize(new PointF(-1, -1));
        this.branchLayer = Bitmap.createBitmap((int) tree.getWidth(), (int) tree.getHeight(), Bitmap.Config.ARGB_8888);
        this.branchLayerCanvas = new Canvas(branchLayer);
        this.leafLayer = Bitmap.createBitmap((int) tree.getWidth(), (int) tree.getHeight(), Bitmap.Config.ARGB_8888);
        this.leafLayerCanvas = new Canvas(leafLayer);
        tree.init();
    }

    @Override
    public Boolean hasNextPaintStep() {
        return !tree.isComplete();
    }

    @Override
    public void paintStep(Canvas canvas) {
        tree.growStep();
        while (tree.hasNextBranch()) {
            paintBranch(tree.nextBranch());
        }
        while (tree.hasNextLeaf()) {
            paintLeaf(tree.nextLeaf());
        }
        fillBackground(canvas);
        canvas.drawBitmap(branchLayer, new Matrix(), null);
        canvas.drawBitmap(leafLayer, new Matrix(), null);
    }

    private void paintBranch(Branch branch) {
        Paint paint = newPaint();
        paint.setColor(branchColor);
        paint.setStrokeWidth(branch.getWidth());
        paint.setStrokeCap(Paint.Cap.ROUND);
        PointF branchBase = branch.getBase();
        PointF branchTip = branch.getTip();
        paint.setShadowLayer(1,
                lightDir.x,
                lightDir.y,
                shadowColor);
        branchLayerCanvas.drawLine(branchBase.x, branchBase.y,
                branchTip.x, branchTip.y, paint);
    }

    private void paintLeaf(Leaf leaf) {
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
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(leafColor);
        paint.setShadowLayer(1,
                lightDir.x,
                lightDir.y,
                shadowColor);
        leafLayerCanvas.drawPath(path, paint);
    }
}
