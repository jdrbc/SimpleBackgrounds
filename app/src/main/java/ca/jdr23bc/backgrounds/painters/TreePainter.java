package ca.jdr23bc.backgrounds.painters;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;

import ca.jdr23bc.backgrounds.shapes.tree.BranchSegment;
import ca.jdr23bc.backgrounds.shapes.tree.Leaf;
import ca.jdr23bc.backgrounds.shapes.tree.Tree;
import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class TreePainter extends ShapePainter<Tree> {
    // Set saturation in the middle so that leaves & branches don't blend into background color
    private static final float ROOT_COLOR_SATURATION = 0.5f;

    // Don't spend too long in the draw loop
    private static final Integer MAX_NUM_ITEMS_TO_PAINT_PER_STEP = 100;

    private Tree tree;
    private final int branchColor;
    private final int leafColor;
    private final int shadowColor;
    private PointF lightDir;
    private Bitmap branchLayer;
    private Canvas branchLayerCanvas;
    private Bitmap leafLayer;
    private Canvas leafLayerCanvas;
    private Bitmap behindLeafLayer;
    private Canvas behindLeafLayerCanvas;

    public TreePainter() {
        super();
        float[] rootColorHsv = new float[3];
        Color.colorToHSV(RandomUtils.getRandomColor(), rootColorHsv);
        rootColorHsv[1] = ROOT_COLOR_SATURATION;
        setColorPalette(Color.HSVToColor(rootColorHsv));
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
        this.behindLeafLayer = Bitmap.createBitmap((int) tree.getWidth(), (int) tree.getHeight(), Bitmap.Config.ARGB_8888);
        this.behindLeafLayerCanvas = new Canvas(behindLeafLayer);
        tree.init();
    }

    @Override
    public Boolean hasNextPaintStep() {
        return !tree.isComplete();
    }

    @Override
    public void paintStep(Canvas canvas) {
        if (!tree.hasNextBranch() && !tree.hasNextLeaf()) {
            branchLayerCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // don't redraw behind leaves
            behindLeafLayerCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            tree.growStep();
        } else {
            Paint paint = newPaint();
            paint.setColor(branchColor);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setShadowLayer(1,
                    lightDir.x,
                    lightDir.y,
                    shadowColor);
            Integer numItemsPainted = 0;
            while (tree.hasNextBranch()) {
                paintBranchSegment(paint, tree.nextBranch());
                numItemsPainted++;
                if (numItemsPainted > MAX_NUM_ITEMS_TO_PAINT_PER_STEP) {
                    return;
                }
            }

            paint = newPaint();
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(leafColor);
            paint.setShadowLayer(1,
                    lightDir.x,
                    lightDir.y,
                    shadowColor);
            while (tree.hasNextLeaf()) {
                paintLeaf(paint, tree.nextLeaf());
                numItemsPainted++;
                if (numItemsPainted > MAX_NUM_ITEMS_TO_PAINT_PER_STEP) {
                    return;
                }
            }
            canvas.drawBitmap(branchLayer, new Matrix(), null);
            canvas.drawBitmap(leafLayer, new Matrix(), null);
            canvas.drawBitmap(behindLeafLayer, new Matrix(), null);
        }
    }

    private void paintBranchSegment(Paint paint, BranchSegment branchSegment) {
        paint.setStrokeWidth(branchSegment.getWidth());
        PointF branchBase = branchSegment.getBase();
        PointF branchTip = branchSegment.getTip();
        branchLayerCanvas.drawLine(branchBase.x, branchBase.y,
                branchTip.x, branchTip.y, paint);
    }

    private void paintLeaf(Paint paint, Leaf leaf) {
        PointF base = leaf.getBase();
        PointF tip = leaf.getTip();
        PointF side1 = leaf.getSideRightOfBase();
        PointF side2 = leaf.getSideLeftOfBase();

        Path path = new Path();
        path.moveTo(base.x, base.y);
        path.quadTo(side1.x, side1.y, tip.x, tip.y);
        path.quadTo(side2.x, side2.y, base.x, base.y);
        path.close();

        if (RandomUtils.getRandomBoolean()) {
            leafLayerCanvas.drawPath(path, paint);
        } else {
            behindLeafLayerCanvas.drawPath(path, paint);
        }
    }
}
