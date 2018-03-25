package ca.jdr23bc.backgrounds.painters;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import ca.jdr23bc.backgrounds.colors.Colour;
import ca.jdr23bc.backgrounds.shapes.tree.BranchSegment;
import ca.jdr23bc.backgrounds.shapes.tree.Leaf;
import ca.jdr23bc.backgrounds.shapes.tree.Tree;
import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class TreePainter extends ShapePainter<Tree> {
    // Set saturation in the middle so that leaves & branches don't blend into background color
    private static final float ROOT_COLOR_SATURATION = 0.5f;

    private Tree tree;
    private int branchColor;
    private int leafColor;
    private int shadowColor;
    private PointF lightDir;
    private Bitmap branchLayer;
    private Canvas branchLayerCanvas;
    private Bitmap leafLayer;
    private Canvas leafLayerCanvas;

    public TreePainter() {
        super();
        float[] rootColorHsv = new float[3];
        Color.colorToHSV(RandomUtils.getRandomColor(), rootColorHsv);
        rootColorHsv[1] = ROOT_COLOR_SATURATION;
        setColorScheme(Color.HSVToColor(rootColorHsv), Colour.ColorScheme.ColorSchemeMonochromatic);
        if (RandomUtils.random.nextBoolean()) {
            this.leafColor = popLightestPaintColor();
            this.shadowColor = popDarkestPaintColor();
            this.branchColor = popDarkestPaintColor();
            setBackgroundColor(popDarkestPaintColor());
        } else {
            this.leafColor = popDarkestPaintColor();
            setBackgroundColor(popLightestPaintColor());
            this.branchColor = popLightestPaintColor();
            this.shadowColor = popLightestPaintColor();
        }
    }

    @Override
    public void init(Tree tree) {
        this.tree = tree;
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
            paintBranchSegment(tree.nextBranch());
        }
        while (tree.hasNextLeaf()) {
            paintLeaf(tree.nextLeaf());
        }
        canvas.drawBitmap(branchLayer, new Matrix(), null);
        canvas.drawBitmap(leafLayer, new Matrix(), null);
    }

    private void paintBranchSegment(BranchSegment branchSegment) {
        Paint paint = newPaint();
        paint.setColor(branchColor);
        paint.setStrokeWidth(branchSegment.getWidth());
        paint.setStrokeCap(Paint.Cap.ROUND);
        PointF branchBase = branchSegment.getBase();
        PointF branchTip = branchSegment.getTip();
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
