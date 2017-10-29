package ca.jdr23bc.backgrounds.shapes.tree;

import android.graphics.PointF;

import java.util.ArrayList;

import ca.jdr23bc.backgrounds.utils.MathUtils;

public class Branch {
    private float width;
    private PointF tip;
    private PointF base;
    private PointF dir;
    private float lengthOfChildBranches;
    private Leaf leaf;

    // List of attracting points
    private ArrayList<PointF> curAttractors = new ArrayList<>();

    public Branch(PointF tip, PointF base, PointF dir, float lengthOfChildBranches, float width) {
        this.tip = tip;
        this.base = base;
        this.dir = dir;
        this.lengthOfChildBranches = lengthOfChildBranches;
        this.width = width;
    }

    // Root constructor - root has length of 0, but child branches will have length 'lengthOfChildBranches'
    public Branch(PointF tip, PointF dir, float lengthOfChildBranches, float width) {
        this(tip, tip, dir, lengthOfChildBranches, width);
    }

    public Branch grow() {
        PointF attractedDir = new PointF(dir.x, dir.y);
        // Two attractors leads to risk of branch trapping
        if (curAttractors.size() == 2) {
            curAttractors.remove(0);
        }
        for (PointF attractor : curAttractors) {
            PointF attractorToTip = MathUtils.sub(attractor, tip);
            PointF attractorToTipUnitVector = MathUtils.normalize(attractorToTip);
            attractedDir = MathUtils.add(attractedDir, attractorToTipUnitVector);
        }
        attractedDir = MathUtils.normalize(attractedDir);
        curAttractors.clear();
        PointF nextBranchTip = MathUtils.add(tip, MathUtils.mult(attractedDir, lengthOfChildBranches));
        PointF nextBranchBase = tip;
        return new Branch(nextBranchTip, nextBranchBase, attractedDir, lengthOfChildBranches, width);
    }

    public PointF getTip() {
        return tip;
    }

    public PointF getBase() {
        return base;
    }

    public float getWidth() {
        return width;
    }

    public float getLength() {
        return MathUtils.getDistF(base, tip);
    }

    public Boolean hasLeaf() {
        return leaf != null;
    }

    public Leaf getLeaf() {
        return leaf;
    }

    public void setLeaf(Leaf leaf) {
        this.leaf = leaf;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void addAttractor(PointF p) {
        curAttractors.add(p);
    }
}
